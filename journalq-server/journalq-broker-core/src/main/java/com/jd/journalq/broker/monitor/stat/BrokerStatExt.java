package com.jd.journalq.broker.monitor.stat;

import java.io.Serializable;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

/**
 * 对BrokerStat 进行扩展，包含 如topic app, partition ,partitionGroup 维度的 consume pending监控信息
 **/
public class BrokerStatExt implements Serializable {

    private BrokerStat brokerStat;
    private Integer brokerId;
    private Map<String/*topic*/, TopicPendingStat> topicPendingStatMap = new HashMap<>();
    private MemoryUsage heap;
    private MemoryUsage nonHeap;
    private long timeStamp;

    public BrokerStatExt(BrokerStat brokerStat) {
        setBrokerStat(brokerStat);
    }

    public BrokerStat getBrokerStat() {
        return brokerStat;
    }

    public void setBrokerStat(BrokerStat brokerStat) {
        this.brokerStat = brokerStat;
        if (brokerStat != null) {
            brokerId = brokerStat.getBrokerId();
        }
    }

    public Map<String, TopicPendingStat> getTopicPendingStatMap() {
        return topicPendingStatMap;
    }

    public void setTopicPendingStatMap(Map<String, TopicPendingStat> topicPendingStatMap) {
        this.topicPendingStatMap = topicPendingStatMap;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public MemoryUsage getHeap() {
        return heap;
    }

    public void setHeap(MemoryUsage heap) {
        this.heap = heap;
    }

    public MemoryUsage getNonHeap() {
        return nonHeap;
    }

    public void setNonHeap(MemoryUsage nonHeap) {
        this.nonHeap = nonHeap;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
