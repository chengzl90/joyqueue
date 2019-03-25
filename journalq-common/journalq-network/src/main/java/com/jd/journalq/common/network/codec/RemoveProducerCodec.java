package com.jd.journalq.common.network.codec;

import com.google.common.collect.Lists;
import com.jd.journalq.common.network.command.JMQCommandType;
import com.jd.journalq.common.network.command.RemoveProducer;
import com.jd.journalq.common.network.serializer.Serializer;
import com.jd.journalq.common.network.transport.codec.JMQHeader;
import com.jd.journalq.common.network.transport.codec.PayloadCodec;
import com.jd.journalq.common.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * RemoveProducerCodec
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/10
 */
public class RemoveProducerCodec implements PayloadCodec<JMQHeader, RemoveProducer>, Type {

    @Override
    public RemoveProducer decode(JMQHeader header, ByteBuf buffer) throws Exception {
        RemoveProducer removeProducer = new RemoveProducer();

        short topicSize = buffer.readShort();
        List<String> topics = Lists.newArrayListWithCapacity(topicSize);
        for (int i = 0; i < topicSize; i++) {
            topics.add(Serializer.readString(buffer, Serializer.SHORT_SIZE));
        }

        removeProducer.setTopics(topics);
        removeProducer.setApp(Serializer.readString(buffer, Serializer.SHORT_SIZE));
        return removeProducer;
    }

    @Override
    public void encode(RemoveProducer payload, ByteBuf buffer) throws Exception {
        buffer.writeShort(payload.getTopics().size());
        for (String topic : payload.getTopics()) {
            Serializer.write(topic, buffer, Serializer.SHORT_SIZE);
        }
        Serializer.write(payload.getApp(), buffer, Serializer.SHORT_SIZE);
    }

    @Override
    public int type() {
        return JMQCommandType.REMOVE_PRODUCER.getCode();
    }
}