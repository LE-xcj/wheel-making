package com.xc.service;

import com.xc.pojo.ChatProtocol;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author chujian
 * @ClassName ClientHandler
 * @Description 功能描述
 * @date 2019/5/14 19:11
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        try {
            ChatProtocol protocol = (ChatProtocol) msg;

            String data = new String(protocol.getContent());
            System.out.println("server : " + data);

        } finally {

            ReferenceCountUtil.release(msg);

        }



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("断开连接.....");
        ctx.close();
    }
}
    