package com.jd.journalq.common.network.command;

import com.jd.journalq.common.exception.JMQCode;
import com.jd.journalq.common.network.transport.command.JMQPayload;

/**
 * ProduceMessageRollbackAck
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/18
 */
public class ProduceMessageRollbackAck extends JMQPayload {

    private JMQCode code;

    public ProduceMessageRollbackAck() {

    }

    public ProduceMessageRollbackAck(JMQCode code) {
        this.code = code;
    }

    @Override
    public int type() {
        return JMQCommandType.PRODUCE_MESSAGE_ROLLBACK_ACK.getCode();
    }

    public void setCode(JMQCode code) {
        this.code = code;
    }

    public JMQCode getCode() {
        return code;
    }
}