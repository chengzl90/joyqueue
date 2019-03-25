package com.jd.journalq.common.network.command;

import com.jd.journalq.common.exception.JMQCode;
import com.jd.journalq.common.network.domain.BrokerNode;

/**
 * FindCoordinatorAckData
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/18
 */
public class FindCoordinatorAckData {

    private BrokerNode node;
    private JMQCode code;

    public FindCoordinatorAckData() {

    }

    public FindCoordinatorAckData(JMQCode code) {
        this.code = code;
    }

    public FindCoordinatorAckData(BrokerNode node, JMQCode code) {
        this.node = node;
        this.code = code;
    }

    public BrokerNode getNode() {
        return node;
    }

    public void setNode(BrokerNode node) {
        this.node = node;
    }

    public JMQCode getCode() {
        return code;
    }

    public void setCode(JMQCode code) {
        this.code = code;
    }
}