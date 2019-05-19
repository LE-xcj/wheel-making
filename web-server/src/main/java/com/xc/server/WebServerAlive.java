package com.xc.server;

import com.alibaba.fastjson.JSON;
import com.xc.constant.ConstantValue;
import com.xc.constant.EncryptionAndDecryption;
import com.xc.initializer.WebServerAliveInitializer;
import com.xc.pojo.vo.SystemAndProcessVO;
import com.xc.utils.GzipUtil;
import com.xc.utils.SigarUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chujian
 * @ClassName WebServer
 * @Description 功能描述
 * @date 2019/5/18 22:44
 */
public class WebServerAlive {

    private static ScheduledExecutorService scheduled;


    private static WebServerAlive webServerAlive;

    static {

        // 定时线程池
        scheduled = Executors.newSingleThreadScheduledExecutor();


        // 初始化alive检测类
        webServerAlive = new WebServerAlive(
                ConstantValue.HEARTBEAT_DETECT_IP, ConstantValue.HEARTBEAT_DETECT_PORT);


    }

    public static void create() { }

    /**
     * 单例，饿汉式
     * @param ip
     * @param port
     */
    private WebServerAlive(String ip, int port) {

        init(ip, port);

    }

    /**
     * 初始化Netty那部分
     * @param ip
     * @param port
     */
    private void init(String ip, int port) {

        // 连接线程组
        EventLoopGroup connection = new NioEventLoopGroup();

        Bootstrap client = new Bootstrap();


        // 参数配置
        client.group(connection)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 1024)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new WebServerAliveInitializer());

        try {

            // 连接
            ChannelFuture future = client.connect(ip, port).sync();
            System.out.println("client alive.....");

            // 开始执行定时任务
            start(future);

            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            connection.shutdownGracefully();
        }

    }


    /**
     * 启动执行定时任务
     * @param future
     */
    private static void start(ChannelFuture future) {

        // 每隔一秒就发送自己的运行信息
        scheduled.scheduleAtFixedRate(
                new ServerAliveTak(future), 10, 1, TimeUnit.SECONDS);

    }


    /**
     * 任务
     */
    private static class ServerAliveTak implements Runnable {

        private ChannelFuture future;

        public ServerAliveTak(ChannelFuture future) {

            this.future = future;

        }

        public void run() {

            // 获取系统和进程运行信息
            SystemAndProcessVO systemAndProcessInfo = SigarUtil.getSystemAndProcessInfo();

            // 转为json字符串
            String info = JSON.toJSONString(systemAndProcessInfo);

            // 加工
            byte[] target = process(info);


            // 发送系统的信息
            future.channel().writeAndFlush(Unpooled.copiedBuffer(target));


        }

        /**
         * 信息加工处理
         * 1、加上分隔符
         * 2、压缩和加密
         * @param info
         * @return
         */
        private byte[] process(String info) {

            // 添加分隔符
            String data = info;

            try {

                // 压缩，并设置加密方式
                byte[] target = GzipUtil.zip(data.getBytes(), EncryptionAndDecryption.negateEncryption);


                target = getTarget(target, ConstantValue.SEPERATOR);

                //String temp = new String(target);
                //temp = temp + ConstantValue.SEPERATOR;
                //target = temp.getBytes();


                // 返回
                return target;

            } catch (IOException e) {

                e.printStackTrace();
            }

            return "".getBytes();


        }


        /**
         * 字节拷贝
         * @param source
         * @param seperator
         * @return
         */
        private byte[] getTarget(byte[] source, String seperator) {

            // 分隔符的字节数组
            byte[] seperatorBytes = seperator.getBytes();

            // 各自数组的长度
            int seperatorLength = seperatorBytes.length;
            int sourceLength = source.length;

            // 总长度
            int all = sourceLength + seperatorLength;

            // 传送的字节数组
            byte[] target = new byte[all];

            // 字节拷贝
            System.arraycopy(source, 0, target,0, sourceLength);
            System.arraycopy(seperatorBytes, 0, target, sourceLength, seperatorLength);

            return target;
        }
    }

}
    