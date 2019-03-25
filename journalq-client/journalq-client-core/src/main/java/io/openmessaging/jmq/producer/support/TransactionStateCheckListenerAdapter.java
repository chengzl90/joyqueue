package io.openmessaging.jmq.producer.support;

import com.jd.journalq.client.internal.producer.callback.TxFeedbackCallback;
import com.jd.journalq.client.internal.producer.domain.ProduceMessage;
import com.jd.journalq.client.internal.producer.domain.TransactionStatus;
import com.jd.journalq.common.domain.TopicName;
import io.openmessaging.jmq.producer.message.MessageAdapter;
import io.openmessaging.message.Message;
import io.openmessaging.producer.TransactionStateCheckListener;

/**
 * TransactionStateCheckListenerAdapter
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2019/2/19
 */
public class TransactionStateCheckListenerAdapter implements TxFeedbackCallback {

    private TransactionStateCheckListener transactionStateCheckListener;

    public TransactionStateCheckListenerAdapter(TransactionStateCheckListener transactionStateCheckListener) {
        this.transactionStateCheckListener = transactionStateCheckListener;
    }

    @Override
    public TransactionStatus confirm(TopicName topic, String txId, String transactionId) {
        Message message = new MessageAdapter(new ProduceMessage());
        message.header()
                .setDestination(topic.getFullName());
        message.extensionHeader().get()
                .setTransactionId(transactionId);

        TransactionalContextAdapter transactionalContext = new TransactionalContextAdapter();
        transactionStateCheckListener.check(message, transactionalContext);

        if (transactionalContext.getStatus() == null) {
            return TransactionStatus.UNKNOWN;
        }
        return transactionalContext.getStatus();
    }
}