package com.jd.journalq.broker.election.command;

import com.jd.journalq.broker.election.TopicPartitionGroup;
import com.jd.journalq.common.network.command.CommandType;
import com.jd.journalq.common.network.transport.command.JMQPayload;

import java.nio.ByteBuffer;

/**
 * author: zhuduohui
 * email: zhuduohui@jd.com
 * date: 2018/8/15
 */
public class AppendEntriesRequest extends JMQPayload {
    private TopicPartitionGroup topicPartitionGroup;

    private int term;
    private int leaderId;

    // position of previous message
    private long prevPosition;
    // term of previous message
    private int prevTerm;

    // the start position of the entries
    private long startPosition;
    // commit position of the raft cluster
    private long commitPosition;
    // left position of the leader
    private long leftPosition;

    private boolean match;

    private ByteBuffer entries;

    public TopicPartitionGroup getTopicPartitionGroup() {
        return topicPartitionGroup;
    }

    public void setTopicPartitionGroup(TopicPartitionGroup topicPartitionGroup) {
        this.topicPartitionGroup = topicPartitionGroup;
    }

    public String getTopic() {
        return topicPartitionGroup.getTopic();
    }

    public int getPartitionGroup() {
        return topicPartitionGroup.getPartitionGroupId();
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public long getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(long prevPosition) {
        this.prevPosition = prevPosition;
    }

    public int getPrevTerm() {
        return prevTerm;
    }

    public void setPrevTerm(int prevTerm) {
        this.prevTerm = prevTerm;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getCommitPosition() {
        return commitPosition;
    }

    public void setCommitPosition(long commitPosition) {
        this.commitPosition = commitPosition;
    }

    public long getLeftPosition() {
        return leftPosition;
    }

    public void setLeftPosition(long leftPosition) {
        this.leftPosition = leftPosition;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public ByteBuffer getEntries() {
        return entries;
    }

    public void setEntries(ByteBuffer entries) {
        this.entries = entries;
    }

    public int getEntriesLength() {
        return entries.remaining();
    }

    @Override
    public int type() {
        return CommandType.RAFT_APPEND_ENTRIES_REQUEST;
    }


    @Override
    public String toString() {
        return new StringBuilder("appendEntiresRequest:{")
                .append("topic:").append(getTopic())
                .append(", partitionGroup:").append(getPartitionGroup())
                .append(", term:").append(term)
                .append(", leaderId:").append(leaderId)
                .append(", prevTerm:").append(prevTerm)
                .append(", prevPosition:").append(prevPosition)
                .append(", startPosition:").append(startPosition)
                .append(", commitPosition:").append(commitPosition)
                .append(", leftPosition:").append(leftPosition)
                .append(", match:").append(match)
                .append(", entryLength:").append(entries == null ? 0 : entries.remaining())
                .append("}").toString();
    }

    public static class Build {
        private AppendEntriesRequest appendEntriesRequest = new AppendEntriesRequest();

        public static Build create() {
            return new Build();
        }

        public AppendEntriesRequest build() {
            return appendEntriesRequest;
        }

        public Build partitionGroup(TopicPartitionGroup partitionGroup) {
            appendEntriesRequest.setTopicPartitionGroup(partitionGroup);
            return this;
        }

        public Build term(int term) {
            appendEntriesRequest.setTerm(term);
            return this;
        }

        public Build leader(int leader) {
            appendEntriesRequest.setLeaderId(leader);
            return this;
        }

        public Build commitPosition(long commitPosition) {
            appendEntriesRequest.setCommitPosition(commitPosition);
            return this;
        }

        public Build startPosition(long startPosition) {
            appendEntriesRequest.setStartPosition(startPosition);
            return this;
        }

        public Build leftPosition(long leftPosition) {
            appendEntriesRequest.setLeftPosition(leftPosition);
            return this;
        }

        public Build match(boolean match) {
            appendEntriesRequest.setMatch(match);
            return this;
        }

        public Build prevTerm(int prevTerm) {
            appendEntriesRequest.setPrevTerm(prevTerm);
            return this;
        }

        public Build prevPosition(long prevPosition) {
            appendEntriesRequest.setPrevPosition(prevPosition);
            return this;
        }

        public Build entries(ByteBuffer entries) {
            appendEntriesRequest.setEntries(entries);
            return this;
        }
    }
}
