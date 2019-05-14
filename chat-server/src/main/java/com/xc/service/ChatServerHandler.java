package com.xc.service;

import com.xc.constant.ConstantValue;
import com.xc.pojo.ChatProtocol;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * @author chujian
 * @ClassName ChatServerHandler
 * @Description 功能描述
 *              具体的读写业务
 * @date 2019/5/14 18:17
 */
public class ChatServerHandler extends ChannelHandlerAdapter{

    /**
     * channel通道激活，也就是新建状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(socketAddress + "的通道激活了.....");
    }

    /**
     * IO操作
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //将封装好的协议强转
        ChatProtocol protocol = (ChatProtocol) msg;

        byte[] content = protocol.getContent();

        //获取ip位置
        SocketAddress source = protocol.getSource();
        String ip = source.toString();

        //内容解码
        String data = new String(content, ConstantValue.CHARSET);
        System.out.println(ip + " : " + data);

        //响应内容
        String str = "i recive your ( " + ip + " ) content";
        ChatProtocol response = new ChatProtocol(str.getBytes().length, str.getBytes());

        //写入并发送
        ctx.channel().writeAndFlush(response);


    }

    /**
     * 读取完毕的事后处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(socketAddress + "读完了.....");


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("与另一台主机断开连接......");
        ctx.close();
    }
}
    