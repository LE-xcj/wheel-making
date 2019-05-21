package com.xc.initializer;

import com.xc.constant.ConstantValue;
import com.xc.handler.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author chujian
 * @ClassName ServerInitializer
 * @Description 功能描述
 * @date 2019/5/17 23:14
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        // 分隔符
        ByteBuf seperator = Unpooled.copiedBuffer(ConstantValue.SEPERATOR.getBytes());


        // 读写超时,注意添加顺序，一定要在业务操作前添加
        pipeline.addLast(new IdleStateHandler(10, 15, 100, TimeUnit.SECONDS));

        // 基于责任链添加一些处理业务
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, seperator))
                .addLast(new ServerHandler());



    }
}
    