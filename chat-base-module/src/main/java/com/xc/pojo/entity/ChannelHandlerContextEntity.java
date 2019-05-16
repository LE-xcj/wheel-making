package com.xc.pojo.entity;

import com.xc.strategy.WriteStrategy;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chujian
 * @ClassName ChannelHandlerContextEntity
 * @Description 功能描述
 *              封装channel和发送策略
 * @date 2019/5/16 19:32
 */
public class ChannelHandlerContextEntity {

    private ChannelHandlerContext channelHandlerContext;

    private WriteStrategy strategy;

    public ChannelHandlerContextEntity() {
    }

    public ChannelHandlerContextEntity(ChannelHandlerContext channelHandlerContext, WriteStrategy strategy) {
        this.channelHandlerContext = channelHandlerContext;
        this.strategy = strategy;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public WriteStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(WriteStrategy strategy) {
        this.strategy = strategy;
    }
}
    