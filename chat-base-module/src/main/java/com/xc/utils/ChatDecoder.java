package com.xc.utils;

import com.xc.constant.ConstantValue;
import com.xc.pojo.ChatProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.net.SocketAddress;
import java.util.List;

/**
 * @author chujian
 * @ClassName ChatDecoder
 * @Description 功能描述
 *              自定义解码器
 * @date 2019/5/14 18:30
 */
public class ChatDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf,
                          List<Object> list) throws Exception {

        //首先可读长度一定要大于等于基本长度，也就是一段没有内容的消息至少也要包括8个字节
        if (byteBuf.readableBytes() >= ConstantValue.BASE_LENGTH) {

            int beginReader;

            while (true) {

                //获取数据包的头部起始下标
                beginReader = byteBuf.readerIndex();

                //记录下标
                byteBuf.markReaderIndex();

                //读取标志
                if (byteBuf.readInt() == ConstantValue.HEAD_DATA) {
                    break;
                }

                //未读到数据包的标志头信息，略过一个字节
                byteBuf.resetReaderIndex();
                byteBuf.readByte();

                //略过一个字节后，再次检测下数据包的长度是否还满足基本长度
                if (byteBuf.readableBytes() < ConstantValue.BASE_LENGTH) {
                    return;
                }
            }  // 读到标志头

            //  读第二个字段，内容长度
            int length = byteBuf.readInt();
            byte[] data = new byte[length];

            //将内容读取到byte数组中
            byteBuf.readBytes(data);

            //信息来源
            SocketAddress socketAddress = channelHandlerContext.channel().remoteAddress();

            // 接收数据和解码，并封装好
            ChatProtocol protocol = new ChatProtocol(length, data, socketAddress);
            list.add(protocol);


        }

    }

}
    