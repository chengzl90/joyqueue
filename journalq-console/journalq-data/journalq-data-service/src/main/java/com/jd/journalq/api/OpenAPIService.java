package com.jd.journalq.api;

import com.jd.journalq.common.model.PageResult;
import com.jd.journalq.common.model.Pagination;
import com.jd.journalq.common.monitor.PartitionAckMonitorInfo;
import com.jd.journalq.common.monitor.PendingMonitorInfo;
import com.jd.journalq.model.domain.*;
import com.jd.journalq.model.query.QBrokerGroup;

import java.util.List;

/**
 *
 * Open API service for third party
 *
 **/
public interface OpenAPIService {


    /**
     *
     * To iterate topic pub/sub info,include all consumers and producers
     *
     * @param pagination  page and pageSize
     *
     **/
    PageResult<TopicPubSub> findTopicPubSubInfo(Pagination pagination) throws Exception;

    /**
     *
     * @param  topic  topic code
     * @param  namespace  topic namespace  code
     *
     **/
    TopicPubSub        findTopicPubSubInfo(String topic,String namespace) throws Exception;

    /**
     * get topiclist
     * @param app
     * @return
     * @throws Exception
     */

    List<Consumer>  queryConsumerTopicByApp(String app) throws Exception;

    /**
     *
     * @param  topic  topic code
     * @param  namespace  topic namespace  code
     * @return  all consumers of the topic
     *
     **/
    List<Consumer>     findConsumers(String topic,String namespace) throws Exception;

    /**
     *
     * @param  topic  topic code
     * @param  namespace  topic namespace  code
     * @return  all producers of the topic
     *
     **/
    List<Producer>     findProducers(String topic,String namespace) throws Exception;


    /**
     *
     * Add a producer(app) to the topic, producer should contain topic and app code, client type
     *
     **/
    Producer   publish(Producer producer) throws Exception;

    /**
     *
     * Add a consumer(app) to the topic,Consumer should contain topic and app code ,consumer group(subscribeGroup)
     *
     **/
    Consumer subscribe(Consumer consumer) throws Exception;


    /**
     *
     * Undo publish
     *
     **/
    boolean unPublish(Producer producer) throws Exception;

    Consumer uniqueSubscribe(Consumer consumer) throws Exception;

    /**
     *
     * Undo subscribe
     *
     **/
    boolean unSubscribe(Consumer consumer) throws Exception;

    /**
     *  Synchronize app from JDOS,JONE or others
     *
     **/
    Application syncApplication(Application application) throws Exception;


    /**
     *  Delete app after undo all pub/sub of the app
     *
     **/
    boolean delApplication(Application application) throws Exception;
    /**
     *  Create Topic
     *
     **/
    Topic  createTopic(Topic topic, QBrokerGroup brokerGroup,Identity operator) throws  Exception;

    /**
     * All partition Offset of the subscribe
     **/
    List<PartitionAckMonitorInfo> findOffsets(Subscribe subscribe) throws  Exception;

    /**
     *
     * Reset partition offset of the @Subscribe
     *
     **/
    boolean resetOffset(Subscribe subscribe,short partition,long offset) throws Exception;

    /**
     * @return  partition message  offset  of @code timeMs
     **/
    List<PartitionAckMonitorInfo> timeOffset(Subscribe subscribe, long timeMs);

    /**
     *
     * Reset all partition  of the @Subscribe by partition index
     *
     **/
    boolean resetOffset(Subscribe subscribe, List<PartitionOffset> offsets);

    /**
     *
     * Reset all partition  of the @Subscribe by time
     *
     **/
    boolean resetOffset(Subscribe subscribe,long timeMs) throws Exception;

    /**
     *
     * How many pending message of the @Subscribe
     *
     **/
    PendingMonitorInfo pending(Subscribe subscribe) throws Exception;

    /**
     *
     * How many partition
     *
     **/
    int queryPartitionByTopic(String namespaceCode,String topicCode) throws Exception;


    /**
     *
     *  Add a token for app
     *
     **/
    List<ApplicationToken>  add(ApplicationToken token);


    /**
     *
     * Look up app tokens
     *
     **/
    List<ApplicationToken>  tokens(String  app);



}
