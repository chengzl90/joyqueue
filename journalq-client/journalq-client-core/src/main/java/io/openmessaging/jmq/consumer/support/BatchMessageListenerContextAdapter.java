package io.openmessaging.jmq.consumer.support;

import io.openmessaging.consumer.BatchMessageListener;
import io.openmessaging.consumer.MessageReceipt;

/**
 * BatchMessageListenerContextAdapter
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/2/20
 */
public class BatchMessageListenerContextAdapter implements BatchMessageListener.Context {

    private boolean ack = false;

    @Override
    public void success(MessageReceipt... messages) {
    }

    @Override
    public void ack() {
        ack = true;
    }

    public boolean isAck() {
        return ack;
    }
}