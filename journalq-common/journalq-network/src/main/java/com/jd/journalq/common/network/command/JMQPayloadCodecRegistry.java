package com.jd.journalq.common.network.command;

import com.jd.journalq.common.network.codec.AddConnectionAckCodec;
import com.jd.journalq.common.network.codec.AddConnectionCodec;
import com.jd.journalq.common.network.codec.AddConsumerAckCodec;
import com.jd.journalq.common.network.codec.AddConsumerCodec;
import com.jd.journalq.common.network.codec.AddProducerAckCodec;
import com.jd.journalq.common.network.codec.AddProducerCodec;
import com.jd.journalq.common.network.codec.AuthorizationCodec;
import com.jd.journalq.common.network.codec.BooleanAckCodec;
import com.jd.journalq.common.network.codec.CommitAckAckCodec;
import com.jd.journalq.common.network.codec.CommitAckCodec;
import com.jd.journalq.common.network.codec.FetchAssignedPartitionAckCodec;
import com.jd.journalq.common.network.codec.FetchAssignedPartitionCodec;
import com.jd.journalq.common.network.codec.FetchClusterAckCodec;
import com.jd.journalq.common.network.codec.FetchClusterCodec;
import com.jd.journalq.common.network.codec.FetchHealthAckCodec;
import com.jd.journalq.common.network.codec.FetchHealthCodec;
import com.jd.journalq.common.network.codec.FetchIndexAckCodec;
import com.jd.journalq.common.network.codec.FetchIndexCodec;
import com.jd.journalq.common.network.codec.FetchPartitionMessageAckCodec;
import com.jd.journalq.common.network.codec.FetchPartitionMessageCodec;
import com.jd.journalq.common.network.codec.FetchProduceFeedbackAckCodec;
import com.jd.journalq.common.network.codec.FetchProduceFeedbackCodec;
import com.jd.journalq.common.network.codec.FetchTopicMessageAckCodec;
import com.jd.journalq.common.network.codec.FetchTopicMessageCodec;
import com.jd.journalq.common.network.codec.FindCoordinatorAckCodec;
import com.jd.journalq.common.network.codec.FindCoordinatorCodec;
import com.jd.journalq.common.network.codec.GetTopicsAckCodec;
import com.jd.journalq.common.network.codec.GetTopicsCodec;
import com.jd.journalq.common.network.codec.HeartbeatCodec;
import com.jd.journalq.common.network.codec.ProduceMessageAckCodec;
import com.jd.journalq.common.network.codec.ProduceMessageCodec;
import com.jd.journalq.common.network.codec.ProduceMessageCommitAckCodec;
import com.jd.journalq.common.network.codec.ProduceMessageCommitCodec;
import com.jd.journalq.common.network.codec.ProduceMessagePrepareAckCodec;
import com.jd.journalq.common.network.codec.ProduceMessagePrepareCodec;
import com.jd.journalq.common.network.codec.ProduceMessageRollbackAckCodec;
import com.jd.journalq.common.network.codec.ProduceMessageRollbackCodec;
import com.jd.journalq.common.network.codec.RemoveConnectionCodec;
import com.jd.journalq.common.network.codec.RemoveConsumerCodec;
import com.jd.journalq.common.network.codec.RemoveProducerCodec;
import com.jd.journalq.common.network.codec.SubscribeAckCodec;
import com.jd.journalq.common.network.codec.SubscribeCodec;
import com.jd.journalq.common.network.codec.UnSubscribeCodec;
import com.jd.journalq.common.network.transport.codec.PayloadCodecFactory;

/**
 * JMQPayloadCodecRegistry
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/11/28
 */
public class JMQPayloadCodecRegistry {

    public static void register(PayloadCodecFactory payloadCodecFactory) {
        payloadCodecFactory.register(new BooleanAckCodec());

        // 连接相关
        payloadCodecFactory.register(new AddConnectionCodec());
        payloadCodecFactory.register(new AddConnectionAckCodec());
        payloadCodecFactory.register(new RemoveConnectionCodec());
        payloadCodecFactory.register(new AddConsumerCodec());
        payloadCodecFactory.register(new AddConsumerAckCodec());
        payloadCodecFactory.register(new RemoveConsumerCodec());
        payloadCodecFactory.register(new AddProducerCodec());
        payloadCodecFactory.register(new AddProducerAckCodec());
        payloadCodecFactory.register(new RemoveProducerCodec());
        payloadCodecFactory.register(new HeartbeatCodec());
        payloadCodecFactory.register(new FetchHealthCodec());
        payloadCodecFactory.register(new FetchHealthAckCodec());

        // 集群相关
        payloadCodecFactory.register(new FetchClusterCodec());
        payloadCodecFactory.register(new FetchClusterAckCodec());

        // 协调者相关
        payloadCodecFactory.register(new FindCoordinatorCodec());
        payloadCodecFactory.register(new FindCoordinatorAckCodec());
        payloadCodecFactory.register(new FetchAssignedPartitionCodec());
        payloadCodecFactory.register(new FetchAssignedPartitionAckCodec());

        // 消费相关
        payloadCodecFactory.register(new FetchTopicMessageCodec());
        payloadCodecFactory.register(new FetchTopicMessageAckCodec());
        payloadCodecFactory.register(new FetchPartitionMessageCodec());
        payloadCodecFactory.register(new FetchPartitionMessageAckCodec());
        payloadCodecFactory.register(new CommitAckCodec());
        payloadCodecFactory.register(new CommitAckAckCodec());
        payloadCodecFactory.register(new FetchIndexCodec());
        payloadCodecFactory.register(new FetchIndexAckCodec());

        // 生产相关
        payloadCodecFactory.register(new ProduceMessageCodec());
        payloadCodecFactory.register(new ProduceMessageAckCodec());
        payloadCodecFactory.register(new ProduceMessagePrepareCodec());
        payloadCodecFactory.register(new ProduceMessagePrepareAckCodec());
        payloadCodecFactory.register(new ProduceMessageCommitCodec());
        payloadCodecFactory.register(new ProduceMessageCommitAckCodec());
        payloadCodecFactory.register(new ProduceMessageRollbackCodec());
        payloadCodecFactory.register(new ProduceMessageRollbackAckCodec());
        payloadCodecFactory.register(new FetchProduceFeedbackCodec());
        payloadCodecFactory.register(new FetchProduceFeedbackAckCodec());

        // mqtt
        payloadCodecFactory.register(JMQCommandType.MQTT_SUBSCRIBE.getCode(), new SubscribeCodec());
        payloadCodecFactory.register(JMQCommandType.MQTT_SUBSCRIBE_ACK.getCode(), new SubscribeAckCodec());
        payloadCodecFactory.register(JMQCommandType.MQTT_UNSUBSCRIBE.getCode(), new UnSubscribeCodec());
        payloadCodecFactory.register(JMQCommandType.MQTT_GET_TOPICS.getCode(), new GetTopicsCodec());
        payloadCodecFactory.register(JMQCommandType.MQTT_GET_TOPICS_ACK.getCode(), new GetTopicsAckCodec());
        payloadCodecFactory.register(JMQCommandType.MQTT_AUTHORIZATION.getCode(), new AuthorizationCodec());
    }
}