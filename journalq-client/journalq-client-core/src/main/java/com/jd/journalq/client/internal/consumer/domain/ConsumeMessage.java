package com.jd.journalq.client.internal.consumer.domain;

import com.jd.journalq.common.domain.TopicName;
import com.jd.journalq.toolkit.lang.Objects;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * ConsumeMessage
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/7
 */
public class ConsumeMessage implements Serializable {

    private TopicName topic;
    private String app;
    private short partition;
    private long index;
    private String txId;
    private String businessId;
    private String body;
    private byte[] bodyBytes;
    private short flag;
    private byte priority;
    private long startTime;
    private byte source;
    private Map<String, String> attributes;

    public ConsumeMessage() {

    }

    public ConsumeMessage(TopicName topic, String app, short partition, long index, String txId, String businessId, String body, byte[] bodyBytes, short flag, byte priority, long startTime, byte source, Map<String, String> attributes) {
        this.topic = topic;
        this.app = app;
        this.partition = partition;
        this.index = index;
        this.txId = txId;
        this.businessId = businessId;
        this.body = body;
        this.bodyBytes = bodyBytes;
        this.flag = flag;
        this.priority = priority;
        this.startTime = startTime;
        this.source = source;
        this.attributes = attributes;
    }

    public TopicName getTopic() {
        return topic;
    }

    public String getApp() {
        return app;
    }

    public short getPartition() {
        return partition;
    }

    public long getIndex() {
        return index;
    }

    public String getTxId() {
        return txId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getBody() {
        return body;
    }

    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    public short getFlag() {
        return flag;
    }

    public byte getPriority() {
        return priority;
    }

    public long getStartTime() {
        return startTime;
    }

    public byte getSource() {
        return source;
    }

    public Map<String, String> getAttributes() {
        if (attributes == null) {
            return Collections.emptyMap();
        }
        return attributes;
    }

    public String getAttribute(String key) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }

    public boolean containsAttribute(String key) {
        if (attributes == null) {
            return false;
        }
        return attributes.containsKey(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumeMessage that = (ConsumeMessage) o;
        return partition == that.partition &&
                index == that.index &&
                Objects.equal(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(topic, partition, index);
    }

    @Override
    public String toString() {
        return "ConsumeMessage{" +
                "topic=" + topic +
                ", app='" + app + '\'' +
                ", partition=" + partition +
                ", index=" + index +
                ", txId='" + txId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}