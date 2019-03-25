package com.jd.journalq.server.archive.store.model;

/**
 * 消息消费日志
 * <p>
 * Created by chengzhiliang on 2018/12/4.
 */
public class ConsumeLog {
    private byte[] bytesMessageId; // MD5(topic+partition+index)
    private int appId;
    private int brokerId;
    private byte[] clientIp;
    private String clientIpStr;
    private long consumeTime;

    // 扩展字段
    private String messageId;
    private String topic;
    private String app;

    //16个字节messageId + 4个字节appId + 4个字节brokerId + 16个字节clientIP + 8个字节消费时间
    public final static int len = 16 + 4 + 4 + 16 + 8;
    public final static int keyLen = 16 + 4;
    public final static int valLen = 4 + 16 + 8;

    public ConsumeLog() {
    }


    public byte[] getBytesMessageId() {
        return bytesMessageId;
    }

    public void setBytesMessageId(byte[] bytesMessageId) {
        this.bytesMessageId = bytesMessageId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
    }

    public byte[] getClientIp() {
        return clientIp;
    }

    public void setClientIp(byte[] clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientIpStr() {
        return clientIpStr;
    }

    public void setClientIpStr(String clientIpStr) {
        this.clientIpStr = clientIpStr;
    }

    public long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
}
