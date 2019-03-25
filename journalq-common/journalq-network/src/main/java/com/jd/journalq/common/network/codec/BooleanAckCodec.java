package com.jd.journalq.common.network.codec;

import com.jd.journalq.common.network.command.CommandType;
import com.jd.journalq.common.network.transport.codec.JMQHeader;
import com.jd.journalq.common.network.transport.codec.PayloadCodec;
import com.jd.journalq.common.network.command.BooleanAck;
import com.jd.journalq.common.network.transport.command.Type;
import io.netty.buffer.ByteBuf;

/**
 * BooleanAckCodec
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/11/30
 */
public class BooleanAckCodec implements PayloadCodec<JMQHeader, BooleanAck>, Type {

    @Override
    public Object decode(JMQHeader header, ByteBuf buffer) throws Exception {
        return null;
    }

    @Override
    public void encode(BooleanAck payload, ByteBuf buffer) throws Exception {
    }

    @Override
    public int type() {
        return CommandType.BOOLEAN_ACK;
    }
}