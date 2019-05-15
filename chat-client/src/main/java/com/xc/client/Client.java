package com.xc.client;

import com.alibaba.fastjson.JSON;
import com.xc.constant.ConstantValue;
import com.xc.initializer.ClientInitializer;
import com.xc.pojo.ChatProtocol;
import com.xc.pojo.dto.MessageDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;

/**
 * @author chujian
 * @ClassName Client
 * @Description 功能描述
 * @date 2019/5/14 19:11
 */
public class Client {

    private String source;
    private String target;
    private String tooken;
    private int type;

    public Client(String ip, int port, String source, String target, String tooken, int type) {
        this.source = source;
        this.target = target;
        this.tooken = tooken;
        this.type = type;
        init(ip, port);
    }

    private void init(String ip, int port) {

        EventLoopGroup connection = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();

        client.group(connection)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 2 * 1024)
                .option(ChannelOption.SO_RCVBUF, 2 * 1024)
                .handler(new ClientInitializer());

        try {

            ChannelFuture future = client.connect(ip, port).sync();
            System.out.println("client connect.....");

            send(future);
            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {

            connection.shutdownGracefully();

        }

    }

    /**
     * 发送消息
     * @param future
     */
    private void send(ChannelFuture future) {

        registe (future);

        Scanner input = new Scanner(System.in);

        while (true) {

            String data = input.nextLine();
            String strDto = getMessageDTO(data);

            ChatProtocol protocol = new ChatProtocol(strDto.getBytes().length, strDto.getBytes());
            future.channel().writeAndFlush(protocol);
        }

    }

    /**
     * 连接注册
     * @param future
     */
    private void registe(ChannelFuture future) {

        MessageDTO dto = new MessageDTO();
        dto.setAction(ConstantValue.REGISTE);
        dto.setSource(this.source);
        dto.setTarget(this.target);
        String strDto = JSON.toJSONString(dto);

        ChatProtocol protocol = new ChatProtocol(strDto.getBytes().length, strDto.getBytes());
        future.channel().writeAndFlush(protocol);
    }


    /**
     * 包装传送信息
     * @param data
     */
    private String getMessageDTO(String data) {

        MessageDTO dto = new MessageDTO();

        dto.setSource(this.source);
        dto.setTarget(this.target);
        dto.setType(this.type);
        dto.setToken(this.tooken);
        dto.setContent(data);

        String strDto = JSON.toJSONString(dto);

        return strDto;
    }

}
    