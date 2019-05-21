package com.xc.handler;

import com.xc.constant.ConstantValue;
import com.xc.server.WebServerAlive;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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

        System.out.println("断开连接.....");

        Thread.sleep(1000);
        WebServerAlive.connect(ConstantValue.HEARTBEAT_DETECT_IP, ConstantValue.HEARTBEAT_DETECT_PORT);

        super.channelInactive(ctx);
    }
}
    