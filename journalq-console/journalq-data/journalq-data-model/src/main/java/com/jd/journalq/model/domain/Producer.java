package com.jd.journalq.model.domain;

import com.jd.journalq.model.domain.nsr.BaseNsrModel;

public class Producer extends BaseNsrModel {

    public static final int PRODUCER_TYPE = 1;

    private Identity app;

    private Topic topic;

    private Namespace namespace;

    private Identity owner;

    private byte clientType;

    private boolean archive;//todo 已经挪到config中

    private ProducerConfig config;

    public ProducerConfig getConfig() {
        return config;
    }

    public void setConfig(ProducerConfig config) {
        this.config = config;
    }

    public Identity getApp() {
        return app;
    }

    public void setApp(Identity app) {
        this.app = app;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public byte getClientType() {
        return clientType;
    }

    public void setClientType(byte clientType) {
        this.clientType = clientType;
    }

    public Identity getOwner() {
        return owner;
    }

    public void setOwner(Identity owner) {
        this.owner = owner;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producer producer = (Producer) o;
        if (id != null) {
            if (id.equals(producer.getId())) {
                return true;
            }
        }
        if (topic != null) {
            if (!topic.equals(producer.topic)) return false;
        }
        if (app != null) {
            return app.equals(producer.app);
        }
        return false;
    }
}
