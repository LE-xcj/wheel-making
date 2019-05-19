package com.xc.handler;

import com.xc.constant.EncryptionAndDecryption;
import com.xc.service.HeartBeatService;
import com.xc.utils.GzipUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
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

        try {

            ByteBuf buf = (ByteBuf) msg;

            byte[] source = new byte[buf.readableBytes()];
            buf.readBytes(source);

            // 解压, 解密规则
            byte[] infoBytes = GzipUtil.unzip(source, EncryptionAndDecryption.negateDecrytion);


            // 转为字符串
            String info = new String(infoBytes);

            // 业务显示
            service.show(info);


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
}
    