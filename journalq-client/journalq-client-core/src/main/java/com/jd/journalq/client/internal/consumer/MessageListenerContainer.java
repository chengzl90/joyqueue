package com.jd.journalq.client.internal.consumer;

import com.jd.journalq.toolkit.lang.LifeCycle;

/**
 * MessageListenerContainer
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/25
 */
public interface MessageListenerContainer extends LifeCycle {

    void addListener(String topic, MessageListener messageListener);

    void addBatchListener(String topic, BatchMessageListener messageListener);
}