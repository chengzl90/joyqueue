/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cubao.joyqueue.store.journalkeeper;

import io.chubao.joyqueue.domain.QosLevel;
import io.chubao.joyqueue.exception.JoyQueueCode;
import io.chubao.joyqueue.store.PositionOverflowException;
import io.chubao.joyqueue.store.ReadResult;
import io.chubao.joyqueue.store.WriteRequest;
import io.chubao.joyqueue.store.WriteResult;
import io.chubao.joyqueue.store.message.MessageParser;
import io.chubao.joyqueue.toolkit.concurrent.EventFuture;
import io.chubao.joyqueue.toolkit.format.Format;
import io.chubao.joyqueue.toolkit.time.SystemClock;
import io.chubao.joyqueue.toolkit.util.BaseDirUtils;
import io.journalkeeper.core.api.RaftServer;
import io.journalkeeper.rpc.URIParser;
import io.journalkeeper.utils.spi.ServiceSupport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


/**
 * @author liyue25
 * Date: 2018/8/30
 */
public class JournalKeeperPartitionGroupStoreTest {
    private static final Logger logger = LoggerFactory.getLogger(JournalKeeperPartitionGroupStoreTest.class);
    private static final String topic = "test_topic";
    private static final int partitionGroup = 3;
    private static final short[] partitions = new short[]{4, 5, 6};
    private File base = null;
    private File groupBase = null;
    private static final int brokerId = 88888;
    private JournalKeeperPartitionGroupStore store;
    private final JoyQueueTestUriParser joyQueueUriParser =
            (JoyQueueTestUriParser ) ServiceSupport.load(URIParser.class, JoyQueueTestUriParser.class.getCanonicalName());
    @Test
    public void writeReadTest() throws Exception {

        writeReadTest(QosLevel.RECEIVE);
        after();
        before();
        writeReadTest(QosLevel.PERSISTENCE);
        after();
        before();
        writeReadTest(QosLevel.REPLICATION);
        after();
        before();
        writeReadTest(QosLevel.ALL);

    }


    @Test
    public void indexLengthTest() throws Exception {
        int count = 1024 * 1024;
        long length = 0L;

        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int bodySize = ThreadLocalRandom.current().nextInt(127) + 1;
            
            ByteBuffer msg = MessageUtils.build(1, bodySize).get(0);
            length += msg.remaining();
            store.asyncWrite(e -> latch.countDown(), QosLevel.REPLICATION, new WriteRequest(partitions[ThreadLocalRandom.current().nextInt(partitions.length)], msg));
        }
        // 等待写入成功
        latch.wait();

        for (short partition : partitions) {

            for (long j = 0; j < store.getRightIndex(partition); j++) {
                ReadResult readResult = store.read(partition, j, 1, 0);
                Assert.assertEquals(JoyQueueCode.SUCCESS, readResult.getCode());
                Assert.assertEquals(1, readResult.getMessages().length);
                ByteBuffer readBuffer = readResult.getMessages()[0];
                Assert.assertEquals(readBuffer.getInt(0), readBuffer.remaining());
                length -= readBuffer.remaining();
            }
        }

        Assert.assertEquals(0L, length);

    }

    @Ignore
    @Test
    public void writePerformanceTest() throws InterruptedException {
        writePerformanceTest(5L * 1024 * 1024 * 1024, 1024, 10, false, QosLevel.REPLICATION);
    }

    private void writePerformanceTest(long totalBytes, int msgSize, int batchCount, boolean sync, QosLevel qosLevel) throws InterruptedException {
        List<ByteBuffer> messages = MessageUtils.build(batchCount, msgSize);
        int size = messages.get(0).remaining();
        int [] intPartitions = new int [partitions.length];
        for (int i = 0; i < intPartitions.length; i++) {
            intPartitions[i] = partitions[i];
        }
        List<WriteRequest []> partitionsAndWriteRequests =
                Arrays.stream(intPartitions).mapToObj(
                        intPartition -> (messages.stream().map(b -> new WriteRequest((short ) intPartition, b)).toArray(WriteRequest[]::new))
                ).collect(Collectors.toList());

        long t0 = SystemClock.now();
        int partitionIndex = 0;
        long currentBytes = 0L;
        long writeCount = 0L;

        while (currentBytes < totalBytes) {
            WriteRequest [] writeRequests = partitionsAndWriteRequests.get(partitionIndex);
            partitionIndex ++;
            if(partitionIndex >= partitionsAndWriteRequests.size()) {
                partitionIndex = 0;
            }
            EventFuture<WriteResult> eventFuture = new EventFuture<>();
            store.asyncWrite(eventFuture, qosLevel, writeRequests);
            if(sync) {
                eventFuture.get();
            }
            currentBytes += batchCount * size;
            writeCount += batchCount;
        }
        long t1 = SystemClock.now();

        logger.info("QOS level: {}, {}, total writes {}, takes {}ms, qps: {}, traffic: {}, msg size: {}, batch count: {}",
                qosLevel,
                sync ? "SYNC": "ASYNC",
                Format.formatTraffic(currentBytes),
                t1 - t0,
                Format.formatWithComma(1000L * writeCount / (t1 - t0)),
                Format.formatTraffic(1000L * currentBytes / (t1 - t0)),
                msgSize, batchCount);
    }

    @Ignore
    @Test
    public void loopTest() throws InterruptedException, ExecutionException {
        short partition = 4;
        List<ByteBuffer> messages = MessageUtils.build(1024, 1024);
        WriteRequest [] writeRequests = messages.stream().map(b -> new WriteRequest(partition, b)).toArray(WriteRequest[]::new);
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    for(;;) {
                        store.asyncWrite(QosLevel.RECEIVE, writeRequests);
                    }
                }),
                CompletableFuture.runAsync(() -> {
                    long index = 0 ;
                    for (;;) {
                        try {

                            index += store.read(partition, index, 1024, -1).getMessages().length;
                        } catch (PositionOverflowException ignored) {}
                        catch (Throwable e) {
                            logger.warn("ReadException: ", e);
                        }
                    }
                })
        ).get();


    }

    private void writeReadTest(QosLevel qosLevel) throws InterruptedException {
        int count = 1024;
        short partition = 0;
        long timeout = 500000L;
        List<ByteBuffer> messages = MessageUtils.build(count, 1024);


        final EventFuture<WriteResult> future = new EventFuture<>();
        store.asyncWrite(future, qosLevel,  messages.stream().map(b -> new WriteRequest(partition, b)).toArray(WriteRequest[]::new));

        WriteResult writeResult = future.get();
        Assert.assertEquals(JoyQueueCode.SUCCESS, writeResult.getCode());

        // 等待建索引都完成
        long t0 = SystemClock.now();
        while (SystemClock.now() - t0 < timeout && store.getRightIndex(partition) < count) {
            Thread.sleep(10L);
        }

        for (int i = 0; i < messages.size(); i++) {
            ByteBuffer writeBuffer = messages.get(i).slice();
            writeBuffer.clear();
            writeBuffer.position(MessageParser.getFixedAttributesLength());

            ReadResult readResult = store.read(partition, i, 1, 0);
            Assert.assertEquals(JoyQueueCode.SUCCESS, readResult.getCode());
            Assert.assertEquals(1, readResult.getMessages().length);
            ByteBuffer readBuffer = readResult.getMessages()[0];
            readBuffer.position(MessageParser.getFixedAttributesLength());
            Assert.assertEquals(writeBuffer, readBuffer);
        }

    }


    @Test
    public void writeReadBatchMessageTest() throws IOException, InterruptedException {
        int count = 1024;
        short partition = 4;
        short batchSize = 100;
        List<ByteBuffer> messages = MessageUtils.build(count, 1024)
                .stream().map(m -> MessageUtils.toBatchMessage(m, batchSize))
                .collect(Collectors.toList());



        final EventFuture<WriteResult> future = new EventFuture<>();
        store.asyncWrite(future, QosLevel.REPLICATION, messages.stream().map(b -> new WriteRequest(partition, b)).toArray(WriteRequest[]::new));

        WriteResult writeResult = future.get();
        Assert.assertEquals(JoyQueueCode.SUCCESS, writeResult.getCode());

        for (int i = 0; i < messages.size(); i++) {
            ByteBuffer writeBuffer = messages.get(i);

            for (int j = 0; j < batchSize; j++) {
                long index = i * batchSize + j;
                ReadResult readResult = store.read(partition, index, 1, 0);
                Assert.assertEquals(JoyQueueCode.SUCCESS, readResult.getCode());
                Assert.assertEquals(1, readResult.getMessages().length);
                ByteBuffer readBuffer = readResult.getMessages()[0];
                writeBuffer.clear();
                Assert.assertEquals(writeBuffer, readBuffer);
            }

        }
    }

    @Before
    public void before() throws Exception {
        prepareBaseDir();
        prepareStore();
    }

    @After
    public void after() throws Exception {
        destroyStore();
        destroyBaseDir();
    }

    private void destroyBaseDir() {
        BaseDirUtils.destroyBaseDir(base);

        groupBase = null;
        base = null;
    }

    private void prepareBaseDir() throws IOException {

        base = BaseDirUtils.prepareBaseDir();
        logger.info("Base directory: {}.", base.getCanonicalPath());

        groupBase = new File(base, String.format("%s/%d", topic, partitionGroup));
        logger.info("Partition Group base directory: {}.", groupBase.getCanonicalPath());

    }
    private String getPartitionGroupRelPath(String topic, int partitionGroup) {
        return "topics" + File.separator + topic.replace('/', '@') + File.separator + partitionGroup;
    }

    private List<URI> toURIs(List<Integer> brokerIds, String topic, int group) {
        return brokerIds.stream()
                .map(brokerId -> toURI(brokerId, topic, group))
                .collect(Collectors.toList());
    }

    private URI toURI(int brokerId, String topic, int group) {
        return joyQueueUriParser.create(topic, group, brokerId);
    }


    private void prepareStore() throws Exception {

        File groupBase = new File(base, getPartitionGroupRelPath(topic, partitionGroup));
        Properties properties = new Properties();
        properties.setProperty("working_dir", groupBase.getAbsolutePath());
        //TODO: StoreConfig -> properties
        store =
                new JournalKeeperPartitionGroupStore(
                        topic,
                        partitionGroup,
                        RaftServer.Roll.VOTER,
                        null,
                        properties);
        store.init(toURIs(Collections.singletonList(brokerId), topic, partitionGroup), toURI(brokerId, topic, partitionGroup));
        store.restore();
        store.start();
    }

    private void destroyStore() {
        if (this.store != null) {
            store.stop();
            store = null;
        }
    }

}
