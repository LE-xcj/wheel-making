package com.xc.strategy.imp;

import com.xc.strategy.WriteStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author chujian
 * @ClassName WebSocketWriteStrategy
 * @Description 功能描述
 * @date 2019/5/16 17:50
 */
public class WebSocketWriteStrategy implements WriteStrategy {

    public void writeAndFlush(ChannelHandlerContext ctx, String data) {

        // 直接通过Netty封装好的websocket协议进行发送
        ctx.writeAndFlush(new TextWebSocketFrame(data));

    }

}
    