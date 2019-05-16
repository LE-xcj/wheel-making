package com.xc.initializer;

import com.xc.service.ChatServerHandler;
import com.xc.utils.ChatChannelInitializer;
import com.xc.utils.ChatDecoder;
import com.xc.utils.ChatEncoder;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author chujian
 * @ClassName ServerInitializer
 * @Description 功能描述
 * @date 2019/5/14 20:53
 */
public class ServerInitializer extends ChatChannelInitializer {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        //添加自定义的协议编码和解码工具
        //IO读写业务
        pipeline.addLast(new ChatEncoder())
                .addLast(new ChatDecoder())
                .addLast(new ChatServerHandler());


        //字符串解析器
        //socketChannel.pipeline().addLast(new StringDecoder());


        // 当连接在60秒内没有接收到消息时，触发事件s
//        socketChannel.pipeline().addLast(new IdleStateHandler(
//                ConstantValue.READ_IDLE_TIME_OUT,
//                ConstantValue.WRITE_IDLE_TIME_OUT,
//                ConstantValue.ALL_IDLE_TIME_OUT
//        ));


        // 读超时
        //pipeline.addLast(new ReadTimeoutHandler(5));


    }
}
    