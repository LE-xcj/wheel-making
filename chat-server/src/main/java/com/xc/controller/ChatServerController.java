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

        if (!checkIdentity (dto.getToken())) {
            autoRepsone(sourceCtx, "非法访问.....");
            sourceCtx.close();
            return;
        }

        int type = dto.getType();

        String source = dto.getSource();
        SingleChatContainer.add(source, sourceCtx);

        if (!(type == ConstantValue.SINGLECHAT)) {
            String target = dto.getTarget();
            MultiChatContainer.add(target, dto.getSource());

            int size = MultiChatContainer.getSize(target);
            autoRepsone(sourceCtx, "当前群里人数： " + size);

        }

    }


    /**
     * 身份验证
     * @param token
     * @return
     */
    private boolean checkIdentity(String token) {

        // 这里模拟从缓存或者是token验证系统中检验该token是否有效
        if (null == token || token.length() > 4) {
            return false;
        }

        return true;
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

        // 单聊
        if (type == ConstantValue.SINGLECHAT) {

            ctx = SingleChatContainer.getChannel(target);
            response(ctx, dto, sourceCtx);

        } else {

            // 获取群聊中的ids
            List<String> ids = MultiChatContainer.getChannels(target);
            List<ChannelHandlerContext> channels = new ArrayList<>(ids.size());

            // 封装
            for (String id : ids) {

                // 不用发给自己
                if (id.equals(dto.getSource())) {
                    continue;
                }

                ChannelHandlerContext channel = SingleChatContainer.getChannel(id);
                channels.add(channel);
            }

            // 广播
            broadcast (channels, dto);

        }

    }


    /**
     * 群聊广播
     * @param channels
     * @param dto
     *
     */
    private void broadcast(List<ChannelHandlerContext> channels, MessageDTO dto) {

        String strDto = JSON.toJSONString(dto);
        byte[] bytes = strDto.getBytes();
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
     * @param dto
     * @param sourceCtx
     */
    private void response(ChannelHandlerContext ctx, MessageDTO dto, ChannelHandlerContext sourceCtx) {


        if (ctx == null) {

            autoRepsone(sourceCtx, "对方已经下线了......");

            return;
        }

        //封装
        List<ChannelHandlerContext> channels = new ArrayList<>();
        channels.add(ctx);

        broadcast(channels, dto);
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
    