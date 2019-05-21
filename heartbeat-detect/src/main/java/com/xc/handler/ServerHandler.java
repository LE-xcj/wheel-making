package com.xc.handler;

import com.xc.alarm.impl.NormalAlarm;
import com.xc.constant.EncryptionAndDecryption;
import com.xc.service.HeartBeatService;
import com.xc.utils.GzipUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * @author chujian
 * @ClassName ServerHandler
 * @Description 功能描述
 * @date 2019/5/19 9:36
 */

// 共享一个handler
@ChannelHandler.Sharable
public class ServerHandler extends ChannelHandlerAdapter {

    private HeartBeatService service = new HeartBeatService();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("来自：" + ctx.channel().id() + "的消息");

        // channel的id
        ChannelId id = ctx.channel().id();

        try {

            ByteBuf buf = (ByteBuf) msg;

            byte[] source = new byte[buf.readableBytes()];
            buf.readBytes(source);

            // 解压, 解密规则
            byte[] infoBytes = GzipUtil.unzip(source, EncryptionAndDecryption.negateDecrytion);


            // 转为字符串
            String info = new String(infoBytes);

            // 业务显示
            service.add(info, new NormalAlarm(), id);


        } finally {

            ReferenceCountUtil.release(msg);
        }



    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        IdleStateEvent event = (IdleStateEvent) evt;

        String eventStr = null;

        switch (event.state()) {
            case READER_IDLE:
                // 断开连接
                ctx.disconnect();

                // 将该channel移除
                ChannelId id = ctx.channel().id();
                service.dealOffLine(id);
                System.out.println("断开：" + id);

                // 指的是心跳服务器读web服务器的信息超时
                eventStr = "读超时";

                break;
            case WRITER_IDLE:

                // 指的是心跳服务器给web服务器的写超时
                eventStr = "写超时";
                break;
            case ALL_IDLE:
                eventStr = "读写超时";
                break;
            default:
                break;
        }

        System.out.println("超时事件： " + eventStr);

    }
}
