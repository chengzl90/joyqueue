package com.jd.journalq.common.monitor;

/**
 * 入队信息
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/10/11
 */
public class EnQueueMonitorInfo extends BaseMonitorInfo {

    private long count;
    private long oneMinuteRate;
    private double tp99;
    private double tp90;
    private double max;
    private double min;
    private double avg;
    private long size;
    private long totalSize;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getOneMinuteRate() {
        return oneMinuteRate;
    }

    public void setOneMinuteRate(long oneMinuteRate) {
        this.oneMinuteRate = oneMinuteRate;
    }

    public double getTp99() {
        return tp99;
    }

    public void setTp99(double tp99) {
        this.tp99 = tp99;
    }

    public double getTp90() {
        return tp90;
    }

    public void setTp90(double tp90) {
        this.tp90 = tp90;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMin() {
        return min;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getAvg() {
        return avg;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}