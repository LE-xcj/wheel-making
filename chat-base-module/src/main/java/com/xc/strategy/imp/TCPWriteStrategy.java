package com.xc.strategy.imp;

import com.xc.pojo.ChatProtocol;
import com.xc.strategy.WriteStrategy;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chujian
 * @ClassName TCPWriteStrategy
 * @Description 功能描述
 * @date 2019/5/16 17:43
 */
public class TCPWriteStrategy implements WriteStrategy {

    /**
     *
     * @param ctx
     * @param strDto  传送的数据
     */
    public void writeAndFlush(ChannelHandlerContext ctx, String strDto) {

        byte[] bytes = strDto.getBytes();
        int length = bytes.length;


        // 封装成自定义的协议
        ChatProtocol response = new ChatProtocol(length, bytes);

        // 写入并发送
        ctx.channel().writeAndFlush(response);
    }

}
    