package com.jd.journalq.client.internal.producer.converter;

import com.google.common.collect.Lists;
import com.jd.journalq.client.internal.common.compress.CompressUtils;
import com.jd.journalq.client.internal.common.compress.Compressor;
import com.jd.journalq.client.internal.common.compress.CompressorManager;
import com.jd.journalq.client.internal.exception.ClientException;
import com.jd.journalq.client.internal.producer.domain.ProduceMessage;
import com.jd.journalq.common.message.BrokerMessage;
import com.jd.journalq.common.message.Message;
import com.jd.journalq.common.message.SourceType;
import com.jd.journalq.toolkit.network.IpUtil;
import com.jd.journalq.toolkit.time.SystemClock;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.CRC32;

/**
 * ProduceMessageConverter
 * author: gaohaoxiang
 * email: gaohaoxiang@jd.com
 * date: 2018/12/20
 */
public class ProduceMessageConverter {

    private static final byte[] CLIENT_IP = IpUtil.toByte(IpUtil.getLocalIp() + ":0");

    protected static final Logger logger = LoggerFactory.getLogger(ProduceMessageConverter.class);

    public static List<BrokerMessage> convertToBrokerMessages(String topic, String app, List<ProduceMessage> produceMessages, boolean compress, int compressThreshold, String compressType) {
        List<BrokerMessage> result = Lists.newArrayListWithCapacity(produceMessages.size());
        for (ProduceMessage produceMessage : produceMessages) {
            BrokerMessage brokerMessage = convertToBrokerMessage(topic, app, produceMessage, compress, compressThreshold, compressType);
            result.add(brokerMessage);
        }
        return result;
    }

    public static BrokerMessage convertToBrokerMessage(String topic, String app, ProduceMessage produceMessage, boolean compress, int compressThreshold, String compressType) {
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setTopic(topic);
        brokerMessage.setApp(app);
        brokerMessage.setPartition(produceMessage.getPartition());
        brokerMessage.setBody(serializeBody(produceMessage));
        brokerMessage.setBusinessId(produceMessage.getBusinessId());
        brokerMessage.setPriority(produceMessage.getPriority());
        brokerMessage.setAttributes(produceMessage.getAttributes());
        brokerMessage.setStartTime(SystemClock.now());
        brokerMessage.setFlag(produceMessage.getFlag());
        brokerMessage.setSource(SourceType.JMQ.getValue());
        brokerMessage.setClientIp(CLIENT_IP);
        brokerMessage.setCompressed(false);

        compress(brokerMessage, compress, compressThreshold, compressType);
        crc(brokerMessage);
        return brokerMessage;
    }

    protected static void crc(BrokerMessage brokerMessage) {
        CRC32 crc32 = new CRC32();
        crc32.update(brokerMessage.getByteBody());
        brokerMessage.setBodyCRC(crc32.getValue());
    }

    protected static void compress(BrokerMessage brokerMessage, boolean compress, int compressThreshold, String compressType) {
        if (!compress) {
            return;
        }
        byte[] byteBody = brokerMessage.getByteBody();
        if (compressThreshold > byteBody.length) {
            return;
        }

        Compressor compressor = CompressorManager.getCompressor(compressType);
        try {
            brokerMessage.setBody(CompressUtils.compress(byteBody, compressor));
        } catch (IOException e) {
            throw new ClientException(e);
        }
        brokerMessage.setCompressionType(Message.CompressionType.convert(compressor.type()));
        brokerMessage.setCompressed(true);
    }

    protected static byte[] serializeBody(ProduceMessage produceMessage) {
        if (ArrayUtils.isNotEmpty(produceMessage.getBodyBytes())) {
            return produceMessage.getBodyBytes();
        } else {
            try {
                return produceMessage.getBody().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("serializeBody exception, body: {}", produceMessage.getBody(), e);
                return produceMessage.getBody().getBytes();
            }
        }
    }
}