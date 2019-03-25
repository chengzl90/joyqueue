package com.jd.journalq.broker.manage.service;

import com.jd.journalq.common.monitor.PartitionAckMonitorInfo;

import java.util.List;

/**
 * ConsumerManageService
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/10/15
 */
public interface ConsumerManageService {

    /**
     * 设置ackindex
     *
     * @param topic
     * @param app
     * @param partition
     * @param index
     * @return
     */
    boolean setAckIndex(String topic, String app, short partition, long index);

    /**
     * 设置最大ack索引
     *
     * @param topic
     * @param app
     * @return
     */
    boolean setMaxAckIndex(String topic, String app, short partition);

    /**
     * 返回最大ack索引
     *
     * @param topic
     * @param app
     * @return
     */
    long getAckIndex(String topic, String app, short partition);

    /**
     * 返回最大ack索引
     *
     * @param topic
     * @param app
     * @return
     */
    List<PartitionAckMonitorInfo> getAckIndexes(String topic, String app);

    /**
     * 设置最大ack索引
     *
     * @param topic
     * @param app
     * @return
     */
    boolean setMaxAckIndexes(String topic, String app);

    /**
     * 根据时间设置ack
     *
     * @param topic
     * @param app
     * @param partition
     * @param timestamp
     * @return
     */
    boolean setAckIndexByTime(String topic, String app, short partition, long timestamp);

    /**
     * 根据时间返回ack
     *
     * @param topic
     * @param app
     * @param partition
     * @param timestamp
     * @return
     */
    long getAckIndexByTime(String topic, String app, short partition, long timestamp);

    /**
     * 根据时间返回ack
     *
     * @param topic
     * @param app
     * @param timestamp
     * @return
     */
    List<PartitionAckMonitorInfo> getTopicAckIndexByTime(String topic, String app , long timestamp);

    /**
     * 根据时间设置ack
     *
     * @param topic
     * @param app
     * @param timestamp
     * @return
     */
    boolean setAckIndexesByTime(String topic, String app, long timestamp);
}
