package com.jd.journalq.broker.kafka.message.compressor.lz4;


import com.jd.journalq.broker.kafka.util.KafkaBufferUtils;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;
import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A partial implementation of the v1.4.1 LZ4 Frame format.
 *
 * @see <a href="https://docs.google.com/document/d/1Tdxmn5_2e5p1y4PtXkatLndWVb0R8QARJFe6JI4Keuo/edit">LZ4 Framing Format Spec</a>
 */
public final class KafkaLZ4BlockInputStream extends FilterInputStream {

    public static final String PREMATURE_EOS = "Stream ended prematurely";
    public static final String NOT_SUPPORTED = "Stream unsupported";
    public static final String BLOCK_HASH_MISMATCH = "Block checksum mismatch";
    public static final String DESCRIPTOR_HASH_MISMATCH = "Stream frame descriptor corrupted";

    private final LZ4SafeDecompressor decompressor;
    private final XXHash32 checksum;
    private final byte[] buffer;
    private final byte[] compressedBuffer;
    private final int maxBlockSize;
    private KafkaLZ4BlockOutputStream.FLG flg;
    private KafkaLZ4BlockOutputStream.BD bd;
    private int bufferOffset;
    private int bufferSize;
    private boolean finished;
    private final boolean ignoreFlagDescriptorChecksum;

    /**
     * Create a new {@link java.io.InputStream} that will decompress data using the LZ4 algorithm.
     *
     * @param in The stream to decompress
     * @throws java.io.IOException
     */
    public KafkaLZ4BlockInputStream(InputStream in, boolean ignoreFlagDescriptorChecksum) throws IOException {
        super(in);
        this.ignoreFlagDescriptorChecksum = ignoreFlagDescriptorChecksum;
        decompressor = LZ4Factory.fastestInstance().safeDecompressor();
        checksum = XXHashFactory.fastestInstance().hash32();
        readHeader();
        maxBlockSize = bd.getBlockMaximumSize();
        buffer = new byte[maxBlockSize];
        compressedBuffer = new byte[maxBlockSize];
        bufferOffset = 0;
        bufferSize = 0;
        finished = false;

    }

    /**
     * Reads the magic number and frame descriptor from the underlying {@link InputStream}.
     *
     * @throws IOException
     */
    private void readHeader() throws IOException {
        byte[] header = new byte[KafkaLZ4BlockOutputStream.LZ4_MAX_HEADER_LENGTH];

        // read first 6 bytes into buffer to check magic and FLG/BD descriptor flags
        bufferOffset = 6;
        if (in.read(header, 0, bufferOffset) != bufferOffset) {
            throw new IOException(PREMATURE_EOS);
        }

        if (KafkaLZ4BlockOutputStream.MAGIC != KafkaBufferUtils.readUnsignedIntLE(header, bufferOffset - 6)) {
            throw new IOException(NOT_SUPPORTED);
        }

        int checksumOffset = 4;
        int checksumLen = bufferOffset - checksumOffset;

        flg = KafkaLZ4BlockOutputStream.FLG.fromByte(header[bufferOffset - 2]);
        bd = KafkaLZ4BlockOutputStream.BD.fromByte(header[bufferOffset - 1]);

        if (flg.isContentSizeSet()) {
            if (in.read(header, bufferOffset, 8) != 8) {
                throw new IOException(PREMATURE_EOS);
            }
            checksumLen += 8;
        }

        // Old implementations produced incorrect HC checksums
        if (ignoreFlagDescriptorChecksum) {
            in.read(header, bufferOffset, 1);
            return;
        }

        // TODO read uncompressed content size, update flg.validate()
        // TODO read dictionary id, update flg.validate()

        // check stream descriptor hash
        byte hash = (byte) ((checksum.hash(header, checksumOffset, checksumLen, 0) >> 8) & 0xFF);
        header[bufferOffset++] = (byte) in.read();
        if (hash != header[bufferOffset-1]) {
            throw new IOException(DESCRIPTOR_HASH_MISMATCH);
        }
    }

    /**
     * Decompresses (if necessary) buffered data, optionally computes and validates a XXHash32 checksum,
     * and writes the result to a buffer.
     *
     * @throws IOException
     */
    private void readBlock() throws IOException {
        int blockSize = KafkaBufferUtils.readUnsignedIntLE(in);

        // Check for EndMark
        if (blockSize == 0) {
            finished = true;
            // TODO implement content checksum, update flg.validate()
            return;
        } else if (blockSize > maxBlockSize) {
            throw new IOException(String.format("Block size %s exceeded max: %s", blockSize, maxBlockSize));
        }

        boolean compressed = (blockSize & KafkaLZ4BlockOutputStream.LZ4_FRAME_INCOMPRESSIBLE_MASK) == 0;
        byte[] bufferToRead;
        if (compressed) {
            bufferToRead = compressedBuffer;
        } else {
            blockSize &= ~KafkaLZ4BlockOutputStream.LZ4_FRAME_INCOMPRESSIBLE_MASK;
            bufferToRead = buffer;
            bufferSize = blockSize;
        }

        if (in.read(bufferToRead, 0, blockSize) != blockSize) {
            throw new IOException(PREMATURE_EOS);
        }

        // verify checksum
        if (flg.isBlockChecksumSet() && KafkaBufferUtils.readUnsignedIntLE(in) != checksum.hash(bufferToRead, 0, blockSize, 0)) {
            throw new IOException(BLOCK_HASH_MISMATCH);
        }

        if (compressed) {
            try {
                bufferSize = decompressor.decompress(compressedBuffer, 0, blockSize, buffer, 0, maxBlockSize);
            } catch (LZ4Exception e) {
                throw new IOException(e);
            }
        }

        bufferOffset = 0;
    }

    @Override
    public int read() throws IOException {
        if (finished) {
            return -1;
        }
        if (available() == 0) {
            readBlock();
        }
        if (finished) {
            return -1;
        }
        int value = buffer[bufferOffset++] & 0xFF;

        return value;
    }

    @Override
    public int read(byte b[], int off, int len) throws IOException {
        //net.jpountz.util.Utils.checkRange(b, off, len);
        if (finished) {
            return -1;
        }
        if (available() == 0) {
            readBlock();
        }
        if (finished) {
            return -1;
        }
        len = Math.min(len, available());
        System.arraycopy(buffer, bufferOffset, b, off, len);
        bufferOffset += len;
        return len;
    }

    @Override
    public long skip(long n) throws IOException {
        if (finished) {
            return 0;
        }
        if (available() == 0) {
            readBlock();
        }
        if (finished) {
            return 0;
        }
        n = Math.min(n, available());
        bufferOffset += n;
        return n;
    }

    @Override
    public int available() throws IOException {
        return bufferSize - bufferOffset;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new RuntimeException("mark not supported");
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new RuntimeException("reset not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

}

