package com.xc.service;

import com.xc.controller.ChatServerController;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chujian
 * @ClassName WebSocketRequestHandler
 * @Description 功能描述
 * @date 2019/5/16 12:22
 */
public class WebSocketRequestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Autowired
    private ChatServerController controller;

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        String content = textWebSocketFrame.text();
        System.out.println("收到lalal.." + content);

        System.out.println(controller);

    }
}
    