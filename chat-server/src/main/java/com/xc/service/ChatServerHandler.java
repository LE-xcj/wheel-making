package com.xc.service;

import com.xc.constant.ConstantValue;
import com.xc.constant.StrategyValue;
import com.xc.controller.ChatServerController;
import com.xc.pojo.ChatProtocol;
import com.xc.pojo.entity.ChannelHandlerContextEntity;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * @author chujian
 * @ClassName ChatServerHandler
 * @Description 功能描述
 *              具体的读写业务
 * @date 2019/5/14 18:17
 */
public class ChatServerHandler extends ChannelHandlerAdapter{


    private ChannelHandlerContextEntity entity;

    /**
     * channel通道激活，也就是新建状态
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)  {

        // 初始化entity
        this.entity = new ChannelHandlerContextEntity(ctx, StrategyValue.TCP_WRITE_STRATEGY);

        //不管同一个应用程序连多少次，就建立多少条channel
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        System.out.println(socketAddress + "的通道激活了.....");
    }

    /**
     * IO操作
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        if (msg instanceof ChatProtocol) {

            //将封装好的协议强转
            ChatProtocol protocol = (ChatProtocol) msg;
            byte[] content = protocol.getContent();

            //内容解码
            String dtoStr = new String(content, ConstantValue.CHARSET);
            System.out.println(dtoStr);

            //内容转发
            operate(ctx, dtoStr);

        }

    }



    /**
     * 读取完毕的事后处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //SocketAddress socketAddress = ctx.channel().remoteAddress();
        //System.out.println(socketAddress + "读完了.....");
        ctx.flush();

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        System.out.println("与另一台主机断开连接......");
        ctx.close();
    }


    private ChatServerController controller = ChatServerController.getController();


    /**
     * 处理自定义协议的消息
     * @param sourceCtx
     * @param data
     */
    private void operate(ChannelHandlerContext sourceCtx, String data) {

        if (this.entity == null) {
            this.entity = new ChannelHandlerContextEntity(sourceCtx, StrategyValue.TCP_WRITE_STRATEGY);
        }

        controller.operate(this.entity, data);

    }

}
    