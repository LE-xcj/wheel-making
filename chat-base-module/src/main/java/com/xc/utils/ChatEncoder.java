package com.xc.utils;

import com.xc.pojo.ChatProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author chujian
 * @ClassName ChatEncoder
 * @Description 功能描述
 *              自定义协议编码器
 * @date 2019/5/14 18:25
 */

public class ChatEncoder extends MessageToByteEncoder<ChatProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          ChatProtocol chatProtocol,
                          ByteBuf byteBuf) throws Exception {

        //数据包中的必要信息

        //1、头部标志，用于定位数据读取的起始位置
        byteBuf.writeInt(chatProtocol.getHead());

        //2、内容长度，用于限制读取的长度
        byteBuf.writeInt(chatProtocol.getContentLength());

        //3、具体的内容信息
        byteBuf.writeBytes(chatProtocol.getContent());
    }
}
    