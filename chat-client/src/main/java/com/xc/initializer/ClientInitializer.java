package com.xc.initializer;

import com.xc.service.ClientHandler;
import com.xc.utils.ChatChannelInitializer;
import com.xc.utils.ChatDecoder;
import com.xc.utils.ChatEncoder;
import io.netty.channel.socket.SocketChannel;

/**
 * @author chujian
 * @ClassName ClientInitializer
 * @Description 功能描述
 * @date 2019/5/14 20:55
 */
public class ClientInitializer extends ChatChannelInitializer {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //添加自定义的协议编码和解码工具
        socketChannel.pipeline().addLast(new ChatEncoder());
        socketChannel.pipeline().addLast(new ChatDecoder());
        //socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));

        //字符串解析器
        //socketChannel.pipeline().addLast(new StringDecoder());

        //IO读写业务
        socketChannel.pipeline().addLast(new ClientHandler());

    }
}
    