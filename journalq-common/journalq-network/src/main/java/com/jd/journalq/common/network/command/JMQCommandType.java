package com.jd.journalq.common.network.command;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * JMQCommandType
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/11/28
 */
public enum JMQCommandType {

    // 连接相关
    ADD_CONNECTION(1),
    ADD_CONNECTION_ACK(-1),
    REMOVE_CONNECTION(2),
    ADD_CONSUMER(3),
    ADD_CONSUMER_ACK(-3),
    REMOVE_CONSUMER(4),
    ADD_PRODUCER(5),
    ADD_PRODUCER_ACK(-5),
    REMOVE_PRODUCER(6),
    HEARTBEAT(7),
    FETCH_HEALTH(8),
    FETCH_HEALTH_ACK(-8),

    // 集群相关
    FETCH_CLUSTER(10),
    FETCH_CLUSTER_ACK(-10),

    // 协调者相关
    FIND_COORDINATOR(20),
    FIND_COORDINATOR_ACK(-20),
    FETCH_ASSIGNED_PARTITION(21),
    FETCH_ASSIGNED_PARTITION_ACK(-21),

    // 消费相关
    FETCH_TOPIC_MESSAGE(30),
    FETCH_TOPIC_MESSAGE_ACK(-30),
    FETCH_PARTITION_MESSAGE(31),
    FETCH_PARTITION_MESSAGE_ACK(-31),
    COMMIT_ACK(32),
    COMMIT_ACK_ACK(-32),
    COMMIT_ACK_INDEX(33),
    COMMIT_ACK_INDEX_ACK(-33),
    FETCH_ACK_INDEX(34),
    FETCH_ACK_INDEX_ACK(-34),
    FETCH_INDEX(35),
    FETCH_INDEX_ACK(-35),

    // 生产相关
    PRODUCE_MESSAGE(50),
    PRODUCE_MESSAGE_ACK(-50),
    PRODUCE_MESSAGE_PREPARE(51),
    PRODUCE_MESSAGE_PREPARE_ACK(-51),
    PRODUCE_MESSAGE_COMMIT(52),
    PRODUCE_MESSAGE_COMMIT_ACK(-52),
    PRODUCE_MESSAGE_ROLLBACK(53),
    PRODUCE_MESSAGE_ROLLBACK_ACK(-53),
    FETCH_PRODUCE_FEEDBACK(54),
    FETCH_PRODUCE_FEEDBACK_ACK(-54),

    // mqtt使用
    MQTT_SUBSCRIBE(100),
    MQTT_SUBSCRIBE_ACK(-100),
    MQTT_UNSUBSCRIBE(101),
    MQTT_GET_TOPICS(102),
    MQTT_GET_TOPICS_ACK(-102),
    MQTT_AUTHORIZATION(103),

    ;

    private static final Map<Byte, JMQCommandType> TYPES;

    static {
        Map<Byte, JMQCommandType> types = Maps.newHashMap();
        for (JMQCommandType commandType : JMQCommandType.values()) {
            types.put(commandType.getCode(), commandType);
        }
        TYPES = types;
    }

    private byte code;

    private JMQCommandType(int code) {
        this.code = (byte) code;
    }

    public byte getCode() {
        return code;
    }

    public static boolean contains(byte code) {
        return TYPES.containsKey(code);
    }

    public static JMQCommandType valueOf(byte code) {
        return TYPES.get(code);
    }
}