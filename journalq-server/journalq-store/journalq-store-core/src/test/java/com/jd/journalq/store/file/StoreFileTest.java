package com.jd.journalq.store.file;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author majun8
 */
public class StoreFileTest {
    private static final Logger logger = LoggerFactory.getLogger(StoreFileTest.class);
    private File base = null;
    private File file = null;
    private long timestamp = -1L;

    @Before
    public void before() throws Exception {
        prepareBaseDir();
    }

    @Test
    public void readTimestamp() {
        ByteBuffer timeBuffer = ByteBuffer.allocate(8);
        try (RandomAccessFile raf = new RandomAccessFile(file, "r"); FileChannel fileChannel = raf.getChannel()) {
            fileChannel.position(0);
            fileChannel.read(timeBuffer);
        } catch (Exception e) {

        } finally {
            timestamp = timeBuffer.getLong(0);
        }
        logger.info("read timestamp: {}", timestamp);
    }

    @Test
    public void writeTimestamp() {
        ByteBuffer timeBuffer = ByteBuffer.allocate(8);
        long creationTime = System.currentTimeMillis();
        timeBuffer.putLong(0, creationTime);
        //timeBuffer.flip();
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw"); FileChannel fileChannel = raf.getChannel()) {
            fileChannel.position(0);
            fileChannel.write(timeBuffer);
            fileChannel.force(true);
        } catch (Exception e) {

        } finally {
            timestamp = creationTime;
        }
        logger.info("write timestamp: {}", timestamp);
    }

    private void prepareBaseDir() throws IOException {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        base = new File(tempDir + File.separator + "jmq-data");
        if (!base.exists()) {
            base.mkdirs();
        }
        logger.info("Base directory: {}.", base.getCanonicalPath());
        file = new File(base, "329369803896");
    }
}
