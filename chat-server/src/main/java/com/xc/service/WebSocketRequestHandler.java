package com.xc.service;

import com.xc.constant.StrategyValue;
import com.xc.controller.ChatServerController;
import com.xc.pojo.entity.ChannelHandlerContextEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author chujian
 * @ClassName WebSocketRequestHandler
 * @Description 功能描述
 * @date 2019/5/16 12:22
 */

public class WebSocketRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private ChatServerController controller = ChatServerController.getController();

    private ChannelHandlerContextEntity entity;


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        String content = textWebSocketFrame.text();
        System.out.println("收到lalal.." + content);

        if (this.entity == null) {
            this.entity = new ChannelHandlerContextEntity(channelHandlerContext, StrategyValue.WEB_SOCKET_WRITE_STRATEGY);
        }
        controller.operate(this.entity, content);

    }
}
    