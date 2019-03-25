package com.jd.journalq.server.archive.store;

import com.jd.journalq.common.exception.JMQCode;
import com.jd.journalq.common.exception.JMQException;
import com.jd.journalq.common.hbase.HBaseClient;
import com.jd.journalq.toolkit.lang.Pair;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chengzhiliang on 2018/12/10.
 */
public class HBaseTopicAppMapping {

    private final String mappingTable = "topic_app_mapping";
    private final String autoTopicIncreaseRowKey = "topic_increaseId";
    private final String autoAppIncreaseRowKey = "app_increaseId";

    private final byte[] cf = Bytes.toBytes("cf");
    private final byte[] col = Bytes.toBytes("col");

    private final String separator = ":";
    private final String topicPrefix = "topic:";
    private final String appPrefix = "app:";

    // 主题名称和编号缓存
    private Map<String, Integer> topicMappingCache = new ConcurrentHashMap<>();
    // 编号缓存和主题名称
    private Map<Integer, String> topicIdMappingCache = new ConcurrentHashMap<>();
    // 应用名称和编号缓存
    private Map<String, Integer> appMappingCache = new ConcurrentHashMap<>();
    // 编号缓存和应用名称
    private Map<Integer, String> appIdMappingCache = new ConcurrentHashMap<>();
    // hbase客户端
    private HBaseClient hbaseClient;

    public HBaseTopicAppMapping(HBaseClient hbaseClient) {
        this.hbaseClient = hbaseClient;
    }

    /**
     * 获取topic的Id
     *
     * @param name
     * @return
     * @throws InterruptedException
     */
    public int getTopicId(String name) throws JMQException {
        Integer id = topicMappingCache.get(name);
        if (id == null) {
            try {
                id = getIdFromHBase(autoTopicIncreaseRowKey, topicPrefix + name, 1);
            } catch (Exception e) {
                throw new JMQException(JMQCode.CN_DB_ERROR, e);
            }
            topicMappingCache.putIfAbsent(name, id);
        }
        return id;
    }

    public String getTopicName(int id) throws JMQException {
        String topic = topicIdMappingCache.get(id);
        if (topic == null) {
            try {
                topic = getNameFromHBase(cf, col, topicPrefix + id);
            } catch (Exception e) {
                throw new JMQException(JMQCode.CN_DB_ERROR, e);
            }
            topicIdMappingCache.putIfAbsent(id, topic);
        }
        return topic;
    }


    /**
     * 获取app的Id
     *
     * @param name
     * @return
     * @throws InterruptedException
     */
    public int getAppId(String name) throws JMQException {
        Integer id = appMappingCache.get(name);
        if (id == null) {
            try {
                id = getIdFromHBase(autoAppIncreaseRowKey, appPrefix + name, 1);
            } catch (Exception e) {
                throw new JMQException(JMQCode.CN_DB_ERROR, e);
            }
            appMappingCache.putIfAbsent(name, id);
        }
        return id;
    }


    public String getAppName(int id) throws JMQException {
        String app = appIdMappingCache.get(id);
        if (app == null) {
            try {
                app = getNameFromHBase(cf, col, appPrefix + id);
            } catch (Exception e) {
                throw new JMQException(JMQCode.CN_DB_ERROR, e);
            }
            appIdMappingCache.putIfAbsent(id, app);
        }
        return app;
    }

    /**
     *
     * @param autoIncreaseKey
     * @param name
     * @return
     * @throws InterruptedException
     */
    private int getIdFromHBase(String autoIncreaseKey, String name, int invokeTimes) throws InterruptedException, IOException {
        byte[] bytes = hbaseClient.get(mappingTable, cf, col, Bytes.toBytes(name));
        // 如果未初始化、尝试未占位
        if ((bytes == null || bytes.length == 0)
                && compareAndSwap(name, null, 0 + "")) {
            // 占位成功，获取自增主值，
            Integer autoIncreaseId = getAutoIncreaseId(autoIncreaseKey);
            // 更新到占位符
            update(cf, col, name, autoIncreaseId + "");
            return autoIncreaseId;
        }
        String idStr = Bytes.toString(bytes);
        int id = Integer.parseInt(idStr);
        if (id == 0) {
            if (invokeTimes > 3) {
                // 连续等3次，其它线程都没处理，则当前线程从尝试从HBASE获取ID
                compareAndSwap(name, 0 + "", null);
                return getIdFromHBase(autoIncreaseKey, name, 1);
            }
            // 被其它线程占位，待初始化；当前线程等待其它线程处理完成，再去读取
            Thread.sleep(500); // 模拟等待其它线程
            return getIdFromHBase(autoIncreaseKey, name, ++invokeTimes);
        } else {
            // 可直接使用
            return id;
        }
    }

    private boolean compareAndSwap(String rowKey, String expect, String value) throws IOException {
        return hbaseClient.checkAndPut(mappingTable, cf, col, Bytes.toBytes(rowKey),
                expect == null ? null : Bytes.toBytes(expect), value == null ? null : Bytes.toBytes(value));
    }

    /**
     * 获取自增主键值
     *
     * @param autoIncreaseKey
     * @return
     */
    public Integer getAutoIncreaseId(String autoIncreaseKey) throws IOException {
        String idStr = null;
        int newAutoIncreaseId = 1;
        do {
            byte[] bytes = hbaseClient.get(mappingTable, cf, col, Bytes.toBytes(autoIncreaseKey));
            if (bytes != null) {
                idStr = Bytes.toString(bytes);
                newAutoIncreaseId = Integer.parseInt(idStr) + 1;
            }
        } while (!compareAndSwap(autoIncreaseKey, idStr, newAutoIncreaseId + ""));

        return newAutoIncreaseId;
    }


    /**
     * 更新一条记录并插入一条记录
     *
     * @param rowKey
     * @param cf
     * @param col
     * @param val
     */
    private void update(byte[] cf, byte[] col, String rowKey, String val) throws IOException {
        List<Pair<byte[], byte[]>> list = new LinkedList<>();
        // 待更新的记录
        list.add(new Pair<>(Bytes.toBytes(rowKey), Bytes.toBytes(val)));
        // 待插入的记录
        String key = rowKey.split(separator)[0] + separator + val;
        String value = rowKey.split(separator)[1];
        list.add(new Pair<>(Bytes.toBytes(key), Bytes.toBytes(value)));
        // 批量插入
        hbaseClient.put(mappingTable, cf, col, list);
    }

    /**
     *
     * @param cf
     * @param col
     * @param key
     * @return
     * @throws IOException
     */
    private String getNameFromHBase(byte[] cf, byte[] col, String key) throws IOException {
        byte[] bytes = hbaseClient.get(mappingTable, cf, col, Bytes.toBytes(key));
        return Bytes.toString(bytes);
    }

}
