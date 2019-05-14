package com.xc.utils;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


/**
 * @author chujian
 * @ClassName ChatChannelInitializer
 * @Description 功能描述
 * @date 2019/5/14 18:00
 */
public class ChatChannelInitializer extends ChannelInitializer<SocketChannel> {

    public ChatChannelInitializer() {
    }


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //添加自定义的协议编码和解码工具
        socketChannel.pipeline().addLast(new ChatEncoder());
        socketChannel.pipeline().addLast(new ChatDecoder());

        //字符串解析器
        //socketChannel.pipeline().addLast(new StringDecoder());

        //IO读写业务
        socketChannel.pipeline().addLast(null);

    }


}
    