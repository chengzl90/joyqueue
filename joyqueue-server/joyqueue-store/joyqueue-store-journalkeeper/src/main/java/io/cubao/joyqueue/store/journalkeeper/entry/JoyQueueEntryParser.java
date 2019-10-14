package io.cubao.joyqueue.store.journalkeeper.entry;

import io.chubao.joyqueue.store.message.MessageParser;
import io.journalkeeper.core.api.JournalEntry;
import io.journalkeeper.core.api.JournalEntryParser;
import io.journalkeeper.core.entry.DefaultJournalEntry;
import io.journalkeeper.core.entry.JournalEntryParseSupport;

import java.nio.ByteBuffer;

/**
 * @author LiYue
 * Date: 2019/10/14
 */
public class JoyQueueEntryParser implements JournalEntryParser {
    @Override
    public int headerLength() {
        return MessageParser.getFixedAttributesLength();
    }

    @Override
    public JournalEntry parse(byte[] bytes) {
        return new JoyQueueEntry(bytes, true, true);
    }

    @Override
    public JournalEntry parseHeader(byte[] headerBytes) {
        return new JoyQueueEntry(headerBytes, false, false);
    }

    @Override
    public JournalEntry createJournalEntry(byte[] payload) {
        int headerLength = headerLength();

        byte [] rawEntry = new byte[headerLength + payload.length];
        System.arraycopy(payload, 0, rawEntry, headerLength, payload.length);
        ByteBuffer buffer = ByteBuffer.wrap(rawEntry);
        ByteBuffer body = MessageParser.getByteBuffer(buffer, MessageParser.BODY);
        MessageParser.setInt(buffer, MessageParser.LENGTH, rawEntry.length);
        MessageParser.setLong(buffer, MessageParser.CRC, CRC.crc(body));
        MessageParser.setShort(buffer, JournalEntryParseSupport.MAGIC, DefaultJournalEntry.MAGIC_CODE);
        return parse(rawEntry);
    }


}
