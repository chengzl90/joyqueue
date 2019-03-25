package com.jd.journalq.client.internal.producer.support;

import com.jd.journalq.client.internal.cluster.ClusterManager;
import com.jd.journalq.client.internal.metadata.domain.TopicMetadata;
import com.jd.journalq.client.internal.producer.MessageProducer;
import com.jd.journalq.client.internal.producer.TransactionMessageProducer;
import com.jd.journalq.client.internal.producer.callback.AsyncBatchProduceCallback;
import com.jd.journalq.client.internal.producer.callback.AsyncProduceCallback;
import com.jd.journalq.client.internal.producer.domain.ProduceMessage;
import com.jd.journalq.client.internal.producer.domain.SendResult;
import com.jd.journalq.client.internal.producer.interceptor.ProducerInterceptor;
import com.jd.journalq.client.internal.producer.transport.ProducerClientManager;
import com.jd.journalq.toolkit.service.Service;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * MessageProducerWrapper
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/27
 */
public class MessageProducerWrapper extends Service implements MessageProducer {

    private ClusterManager clusterManager;
    private ProducerClientManager producerClientManager;
    private MessageProducer delegate;

    public MessageProducerWrapper(ClusterManager clusterManager, ProducerClientManager producerClientManager, MessageProducer delegate) {
        this.clusterManager = clusterManager;
        this.producerClientManager = producerClientManager;
        this.delegate = delegate;
    }

    @Override
    protected void doStart() throws Exception {
        if (clusterManager != null) {
            clusterManager.start();
        }
        if (producerClientManager != null) {
            producerClientManager.start();
        }
        delegate.start();
    }

    @Override
    protected void doStop() {
        delegate.stop();
        if (producerClientManager != null) {
            producerClientManager.stop();
        }
        if (clusterManager != null) {
            clusterManager.stop();
        }
    }

    @Override
    public SendResult send(ProduceMessage message) {
        return delegate.send(message);
    }

    @Override
    public SendResult send(ProduceMessage message, long timeout, TimeUnit timeoutUnit) {
        return delegate.send(message, timeout, timeoutUnit);
    }

    @Override
    public List<SendResult> batchSend(List<ProduceMessage> messages) {
        return delegate.batchSend(messages);
    }

    @Override
    public List<SendResult> batchSend(List<ProduceMessage> messages, long timeout, TimeUnit timeoutUnit) {
        return delegate.batchSend(messages, timeout, timeoutUnit);
    }

    @Override
    public void sendOneway(ProduceMessage message) {
        delegate.sendOneway(message);
    }

    @Override
    public void sendOneway(ProduceMessage message, long timeout, TimeUnit timeoutUnit) {
        delegate.sendOneway(message, timeout, timeoutUnit);
    }

    @Override
    public void batchSendOneway(List<ProduceMessage> messages) {
        delegate.batchSendOneway(messages);
    }

    @Override
    public void batchSendOneway(List<ProduceMessage> messages, long timeout, TimeUnit timeoutUnit) {
        delegate.batchSendOneway(messages, timeout, timeoutUnit);
    }

    @Override
    public void sendAsync(ProduceMessage message, AsyncProduceCallback callback) {
        delegate.sendAsync(message, callback);
    }

    @Override
    public void sendAsync(ProduceMessage message, long timeout, TimeUnit timeoutUnit, AsyncProduceCallback callback) {
        delegate.sendAsync(message, timeout, timeoutUnit, callback);
    }

    @Override
    public void batchSendAsync(List<ProduceMessage> messages, AsyncBatchProduceCallback callback) {
        delegate.batchSendAsync(messages, callback);
    }

    @Override
    public void batchSendAsync(List<ProduceMessage> messages, long timeout, TimeUnit timeoutUnit, AsyncBatchProduceCallback callback) {
        delegate.batchSendAsync(messages, timeout, timeoutUnit, callback);
    }

    @Override
    public Future<SendResult> sendAsync(ProduceMessage message) {
        return delegate.sendAsync(message);
    }

    @Override
    public Future<SendResult> sendAsync(ProduceMessage message, long timeout, TimeUnit timeoutUnit) {
        return delegate.sendAsync(message, timeout, timeoutUnit);
    }

    @Override
    public Future<List<SendResult>> batchSendAsync(List<ProduceMessage> messages) {
        return delegate.batchSendAsync(messages);
    }

    @Override
    public Future<List<SendResult>> batchSendAsync(List<ProduceMessage> messages, long timeout, TimeUnit timeoutUnit) {
        return delegate.batchSendAsync(messages, timeout, timeoutUnit);
    }

    @Override
    public TransactionMessageProducer beginTransaction() {
        return delegate.beginTransaction();
    }

    @Override
    public TransactionMessageProducer beginTransaction(long timeout, TimeUnit timeoutUnit) {
        return delegate.beginTransaction(timeout, timeoutUnit);
    }

    @Override
    public TransactionMessageProducer beginTransaction(String transactionId) {
        return delegate.beginTransaction(transactionId);
    }

    @Override
    public TransactionMessageProducer beginTransaction(String transactionId, long timeout, TimeUnit timeoutUnit) {
        return delegate.beginTransaction(transactionId, timeout, timeoutUnit);
    }

    @Override
    public TopicMetadata getTopicMetadata(String topic) {
        return delegate.getTopicMetadata(topic);
    }

    @Override
    public void addInterceptor(ProducerInterceptor interceptor) {
        delegate.addInterceptor(interceptor);
    }

    @Override
    public void removeInterceptor(ProducerInterceptor interceptor) {
        delegate.removeInterceptor(interceptor);
    }
}