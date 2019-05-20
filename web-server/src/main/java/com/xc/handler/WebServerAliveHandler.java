package com.xc.handler;

import com.xc.constant.ConstantValue;
import com.xc.server.WebServerAlive;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @author chujian
 * @ClassName WebServerAliveHandler
 * @Description 功能描述
 * @date 2019/5/19 11:49
 */
public class WebServerAliveHandler extends ChannelHandlerAdapter{


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // todo
        System.out.println(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("断线了......");

        EventLoop eventLoop = ctx.channel().eventLoop();

        // 重新连接（使用过程中断线）
        eventLoop.schedule(new Runnable() {
            public void run() {

                System.out.println("reconnect......");
                WebServerAlive.connect(ConstantValue.HEARTBEAT_DETECT_IP, ConstantValue.HEARTBEAT_DETECT_PORT);

            }
        }, 1L, TimeUnit.SECONDS);

        super.channelInactive(ctx);
    }
}
    