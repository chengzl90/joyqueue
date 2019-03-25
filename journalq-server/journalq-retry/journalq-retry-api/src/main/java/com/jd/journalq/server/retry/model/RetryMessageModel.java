package com.jd.journalq.server.retry.model;

/**
 * 重试消息
 *
 * Created by chengzhiliang on 2019/1/30.
 */
//TODO 改一下名字
public class RetryMessageModel {
    // 业务编号
    private String businessId;
    // 主题
    private String topic;
    // 应用
    private String app;
    // 分区
    private short partition;
    // 索引序号
    private long index;
    // 消息体
    private byte[] brokerMessage;
    // 异常信息
    private byte[] exception;
    // 发送时间
    private long sendTime;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public short getPartition() {
        return partition;
    }

    public void setPartition(short partition) {
        this.partition = partition;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public byte[] getBrokerMessage() {
        return brokerMessage;
    }

    public void setBrokerMessage(byte[] brokerMessage) {
        this.brokerMessage = brokerMessage;
    }

    public byte[] getException() {
        return exception;
    }

    public void setException(byte[] exception) {
        this.exception = exception;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
