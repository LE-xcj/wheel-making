package com.xc.controller;

import com.alibaba.fastjson.JSON;
import com.xc.constant.ConstantValue;
import com.xc.container.MultiChatContainer;
import com.xc.container.SingleChatContainer;
import com.xc.pojo.ChatProtocol;
import com.xc.pojo.dto.MessageDTO;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chujian
 * @ClassName ChatServerController
 * @Description 功能描述
 * @date 2019/5/15 11:35
 */
@Controller
public class ChatServerController {


    public void operate(ChannelHandlerContext sourceCtx, String dtoStr) {

        MessageDTO dto = JSON.parseObject(dtoStr, MessageDTO.class);
        int action = dto.getAction();

        // 连接注册
        if (action == ConstantValue.REGISTE) {

            registe(sourceCtx, dto);

        } else {

            //转发
            foward(sourceCtx, dto);
        }

    }

    /**
     * 连接注册
     * @param sourceCtx
     * @param dto
     */
    private void registe(ChannelHandlerContext sourceCtx, MessageDTO dto) {

        int action = dto.getAction();

        if (action == ConstantValue.SINGLECHAT) {

            String source = dto.getSource();
            SingleChatContainer.add(source, sourceCtx);

        } else {

            String target = dto.getTarget();
            MultiChatContainer.add(target, sourceCtx);

            int size = MultiChatContainer.getSize(target);
            autoRepsone(sourceCtx, "当前群里人数： " + size);
        }

    }


    /**
     * 消息转发
     * @param sourceCtx
     * @param dto
     */
    public void foward(ChannelHandlerContext sourceCtx, MessageDTO dto) {

        ChannelHandlerContext ctx;

        // 聊天类型，传送目的地， 传送内容
        int type = dto.getType();
        String target = dto.getTarget();
        String context = dto.getContent();

        // 单聊
        if (type == ConstantValue.SINGLECHAT) {

            ctx = SingleChatContainer.getChannel(target);
            response(ctx, context, sourceCtx);

        } else {

            List<ChannelHandlerContext> channels = MultiChatContainer.getChannels(target);
            broadcast (channels, context);

        }

    }


    /**
     * 群聊广播
     * @param channels
     * @param context
     */
    private void broadcast(List<ChannelHandlerContext> channels, String context) {

        byte[] bytes = context.getBytes();
        int length = bytes.length;

        for (ChannelHandlerContext ctx : channels) {

            // 响应内容
            ChatProtocol response = new ChatProtocol(length, bytes);

            // 写入并发送
            ctx.channel().writeAndFlush(response);
        }

    }


    /**
     * 单聊
     * @param ctx
     * @param context
     * @param sourceCtx
     */
    private void response(ChannelHandlerContext ctx, String context, ChannelHandlerContext sourceCtx) {


        if (ctx == null) {

            autoRepsone(sourceCtx, "对方已经下线了......");

            return;
        }

        //封装
        List<ChannelHandlerContext> channels = new ArrayList<>();
        channels.add(ctx);

        broadcast(channels, context);
    }

    /**
     * 服务器自动消息
     * @param sourceCtx
     * @param content
     */
    private void autoRepsone (ChannelHandlerContext sourceCtx, String content) {

        MessageDTO dto = new MessageDTO();
        dto.setSource("服务器");
        dto.setContent(content);

        String str = JSON.toJSONString(dto);

        // 响应内容
        ChatProtocol response = new ChatProtocol(str.getBytes().length, str.getBytes());
        sourceCtx.channel().writeAndFlush(response);
    }

}
    