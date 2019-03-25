package com.jd.journalq.broker.mqtt.transport;

import com.jd.journalq.broker.mqtt.cluster.MqttConnectionManager;
import com.jd.journalq.broker.mqtt.cluster.MqttConsumerManager;
import com.jd.journalq.broker.mqtt.cluster.MqttSessionManager;
import com.jd.journalq.broker.mqtt.connection.MqttConnection;
import com.jd.journalq.broker.mqtt.handler.ExecutorsProvider;
import com.jd.journalq.broker.mqtt.handler.Handler;
import com.jd.journalq.broker.mqtt.handler.HandlerExecutor;
import com.jd.journalq.broker.mqtt.handler.MqttHandlerDispatcher;
import com.jd.journalq.broker.mqtt.util.NettyAttrManager;
import com.jd.journalq.toolkit.lang.Strings;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.channel.ChannelFutureListener.CLOSE;
import static io.netty.channel.ChannelFutureListener.CLOSE_ON_FAILURE;

/**
 * @author majun8
 */
@ChannelHandler.Sharable
public class MqttCommandInvocation extends SimpleChannelInboundHandler<Object> {
    private final Logger LOG = LoggerFactory.getLogger(MqttCommandInvocation.class);

    private MqttHandlerDispatcher mqttHandlerDispatcher;
    private MqttConnectionManager connectionManager;
    private MqttSessionManager sessionManager;
    private MqttConsumerManager consumerManager;

    public MqttCommandInvocation(MqttHandlerDispatcher mqttHandlerDispatcher) {
        this.mqttHandlerDispatcher = mqttHandlerDispatcher;
        this.connectionManager = mqttHandlerDispatcher.getMqttProtocolHandler().getConnectionManager();
        this.sessionManager = mqttHandlerDispatcher.getMqttProtocolHandler().getSessionManager();
        this.consumerManager = mqttHandlerDispatcher.getMqttProtocolHandler().getConsumerManager();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (ctx.channel().isActive()) {
                if (msg instanceof MqttMessage) {
                    MqttMessage message = (MqttMessage) msg;
                    if (message.decoderResult().isSuccess()) {
                        Handler handler = mqttHandlerDispatcher.getHandler(message.fixedHeader().messageType());
                        HandlerExecutor executor = new HandlerExecutor(handler, ctx, message);
                        if (handler instanceof ExecutorsProvider) {
                            ((ExecutorsProvider) handler).getExecutorService().submit(executor);
                        } else {
                            executor.execute();
                        }
                    }
                }
            } else {
                LOG.error("The channel is not active!" + ctx.channel());
            }
        } catch (Throwable th) {
            LOG.error("MqttCommandInvocation got exception: ", th);
            ctx.fireExceptionCaught(th);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {}

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String clientId = NettyAttrManager.getAttrClientId(ctx.channel());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                // keepalive的1.5倍时间内没有收到client端写操作 触发inactive并关闭连接
                LOG.info("READER_IDLE: {}, start close channel...", clientId);
                ctx.fireChannelInactive();
                ctx.close().addListener(CLOSE_ON_FAILURE);
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                //未进行写操作
                LOG.info("WRITER_IDLE: {}", clientId);
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                //未进行读写
                LOG.info("ALL_IDLE: {}", clientId);
                ctx.fireChannelInactive();
                ctx.close().addListener(CLOSE_ON_FAILURE);
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = NettyAttrManager.getAttrClientId(ctx.channel());
        if (!Strings.isNullOrEmpty(clientId)) {
            MqttConnection connection = connectionManager.getConnection(clientId);
            if (connection == null) {
                ctx.channel().close().addListener(CLOSE_ON_FAILURE);
                return;
            }
            if (!(ctx.channel().equals(connection.getChannel()))) {
                ctx.channel().close().addListener(CLOSE_ON_FAILURE);
                return;
            }
            // 掉线情况下清理client的订阅消费 连接也清理
            consumerManager.stopConsume(clientId);
            sessionManager.removeSession(clientId);
            connectionManager.removeConnection(connection);
        }

        ctx.close().addListener(CLOSE);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        String clientId = NettyAttrManager.getAttrClientId(ctx.channel());
        LOG.info("Channel Writable Changed, clientID: {}", clientId);
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String clientId = NettyAttrManager.getAttrClientId(ctx.channel());
        if (!Strings.isNullOrEmpty(clientId)) {
            LOG.info("Exception got clientID: {}, cause: {}, {}", clientId, cause.getCause(), cause.getMessage());
            consumerManager.stopConsume(clientId);
            sessionManager.removeSession(clientId);

            MqttConnection connection = connectionManager.getConnection(clientId);
            if (connection != null) {
                connection.getChannel().close().addListener(CLOSE_ON_FAILURE);
                connectionManager.removeConnection(connection);
                return;
            }

            ctx.close().addListener(CLOSE_ON_FAILURE);
        }
    }
}
