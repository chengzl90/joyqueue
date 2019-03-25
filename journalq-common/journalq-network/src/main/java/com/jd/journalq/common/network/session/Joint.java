package com.jd.journalq.common.network.session;

/**
 * 接入
 */
public class Joint {
    // 主题
    protected String topic;
    // 消费者
    protected String app;

    public Joint() {
    }

    public Joint(String topic, String app) {
        this.topic = topic;
        this.app = app;
    }

    public static Joint create(String topic, String app) {
        return new Joint(topic, app);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Joint joint = (Joint) o;

        if (app != null ? !app.equals(joint.app) : joint.app != null) {
            return false;
        }
        if (topic != null ? !topic.equals(joint.topic) : joint.topic != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = topic != null ? topic.hashCode() : 0;
        result = 31 * result + (app != null ? app.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Joint{" +
                "topic='" + topic + '\'' +
                ", app='" + app + '\'' +
                '}';
    }
}
