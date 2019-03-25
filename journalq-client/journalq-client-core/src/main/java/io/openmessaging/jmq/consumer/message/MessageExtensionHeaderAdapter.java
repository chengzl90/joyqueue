package io.openmessaging.jmq.consumer.message;

import com.jd.journalq.client.internal.consumer.domain.ConsumeMessage;
import io.openmessaging.extension.ExtensionHeader;

/**
 * MessageExtensionAdapter
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/3/1
 */
public class MessageExtensionHeaderAdapter implements ExtensionHeader {

    private ConsumeMessage message;

    public MessageExtensionHeaderAdapter(ConsumeMessage message) {
        this.message = message;
    }

    @Override
    public ExtensionHeader setPartition(int partition) {
        return this;
    }

    @Override
    public ExtensionHeader setOffset(long offset) {
        return this;
    }

    @Override
    public ExtensionHeader setCorrelationId(String correlationId) {
        return this;
    }

    @Override
    public ExtensionHeader setTransactionId(String transactionId) {
        return this;
    }

    @Override
    public ExtensionHeader setStoreTimestamp(long storeTimestamp) {
        return this;
    }

    @Override
    public ExtensionHeader setStoreHost(String storeHost) {
        return this;
    }

    @Override
    public ExtensionHeader setMessageKey(String messageKey) {
        return this;
    }

    @Override
    public ExtensionHeader setTraceId(String traceId) {
        return this;
    }

    @Override
    public ExtensionHeader setDelayTime(long delayTime) {
        return this;
    }

    @Override
    public ExtensionHeader setExpireTime(long expireTime) {
        return this;
    }

    @Override
    public int getPartiton() {
        return message.getPartition();
    }

    @Override
    public long getOffset() {
        return message.getIndex();
    }

    @Override
    public String getCorrelationId() {
        return null;
    }

    @Override
    public String getTransactionId() {
        return null;
    }

    @Override
    public long getStoreTimestamp() {
        return 0;
    }

    @Override
    public String getStoreHost() {
        return null;
    }

    @Override
    public long getDelayTime() {
        return 0;
    }

    @Override
    public long getExpireTime() {
        return 0;
    }

    @Override
    public String getMessageKey() {
        return message.getBusinessId();
    }

    @Override
    public String getTraceId() {
        return null;
    }

    @Override
    public String toString() {
        return "MessageExtensionHeaderAdapter{" +
                "message=" + message +
                '}';
    }
}