package com.xc.strategy;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author chujian
 * @ClassName WriteStrategy
 * @Description 功能描述
 * 不同协议之间的传输方式会有所差异，因此定义统一的写入接口来解决不兼容问题
 * 采用策略模型来解决，不同协议就采用对应的写入策略
 * @date 2019/5/16 17:44
 */
public interface WriteStrategy {

    /**
     * 统一网络写入接口
     * @param ctx  通道上下文
     * @param data  写入的字符串数据
     */
    void writeAndFlush(ChannelHandlerContext ctx, String data);

}
    