package com.xc.strategy;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author chujian
 * @ClassName WriteStrategy
 * @Description 功能描述
 * @date 2019/5/16 17:44
 */
public interface WriteStrategy {

    void writeAndFlush(ChannelHandlerContext ctx, String data);

}
    