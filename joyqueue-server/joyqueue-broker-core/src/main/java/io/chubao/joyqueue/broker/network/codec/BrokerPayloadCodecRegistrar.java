/**
 * Copyright 2019 The JoyQueue Authors.
 *
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
package io.chubao.joyqueue.broker.network.codec;

import io.chubao.joyqueue.broker.index.network.codec.IndexQueryRequestDecoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexQueryRequestEncoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexQueryResponseDecoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexQueryResponseEncoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexStoreRequestDecoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexStoreRequestEncoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexStoreResponseDecoder;
import io.chubao.joyqueue.broker.index.network.codec.IndexStoreResponseEncoder;
import io.chubao.joyqueue.broker.producer.transaction.codec.TransactionCommitRequestCodec;
import io.chubao.joyqueue.broker.producer.transaction.codec.TransactionRollbackRequestCodec;
import io.chubao.joyqueue.network.codec.BooleanAckCodec;
import io.chubao.joyqueue.network.transport.codec.PayloadCodecFactory;
import io.chubao.joyqueue.nsr.network.codec.OperatePartitionGroupCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.GetRetryAckCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.GetRetryCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.GetRetryCountAckCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.GetRetryCountCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.PutRetryCodec;
import io.chubao.joyqueue.server.retry.remote.command.codec.UpdateRetryCodec;

/**
 * BrokerPayloadCodecRegistrar
 *
 * author: gaohaoxiang
 * date: 2018/8/21
 */
// 使用BrokerPayloadCodec接口通过spi方式注册
@Deprecated
public class BrokerPayloadCodecRegistrar {

    public static PayloadCodecFactory register(PayloadCodecFactory payloadCodecFactory) {
        // boolean ack command codec
        payloadCodecFactory.register(new BooleanAckCodec());

        // retry message command codec
        payloadCodecFactory.register(new GetRetryCodec());
        payloadCodecFactory.register(new GetRetryAckCodec());
        payloadCodecFactory.register(new GetRetryCountCodec());
        payloadCodecFactory.register(new GetRetryCountAckCodec());
        payloadCodecFactory.register(new PutRetryCodec());
        payloadCodecFactory.register(new UpdateRetryCodec());

        // index manage command codec
        payloadCodecFactory.register(new IndexQueryRequestDecoder());
        payloadCodecFactory.register(new IndexQueryRequestEncoder());
        payloadCodecFactory.register(new IndexQueryResponseDecoder());
        payloadCodecFactory.register(new IndexQueryResponseEncoder());
        payloadCodecFactory.register(new IndexStoreRequestDecoder());
        payloadCodecFactory.register(new IndexStoreRequestEncoder());
        payloadCodecFactory.register(new IndexStoreResponseDecoder());
        payloadCodecFactory.register(new IndexStoreResponseEncoder());

        //nsr
        payloadCodecFactory.register(new OperatePartitionGroupCodec());

        // transaction
        payloadCodecFactory.register(new TransactionCommitRequestCodec());
        payloadCodecFactory.register(new TransactionRollbackRequestCodec());

        return payloadCodecFactory;
    }
}