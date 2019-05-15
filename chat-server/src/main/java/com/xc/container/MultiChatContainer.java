package com.xc.container;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chujian
 * @ClassName MultiChat
 * @Description 功能描述
 *              群聊的channel存储容器
 * @date 2019/5/15 11:04
 */
public class MultiChatContainer {

    private static Map<String, List<ChannelHandlerContext>> multi;

    static {
        multi = new ConcurrentHashMap<>();
    }

    /**
     * 一个用户加入到群聊
     * 房号为key
     * @param id
     * @param channelHandlerContext
     */
    public static void add (String id, ChannelHandlerContext channelHandlerContext) {

        List<ChannelHandlerContext> list = multi.get(id);

        if (null == list) {
            list = new LinkedList<>();
        }

        list.add(channelHandlerContext);
    }


    /**
     * 移除群聊
     * @param id
     */
    public static void remove (String id, ChannelHandlerContext context) {

        List<ChannelHandlerContext> list = multi.get(id);

        if (null == list || list.isEmpty()) {
            return;
        }

        list.remove(context);
    }


    public static List<ChannelHandlerContext> getChannels (String key) {

        List<ChannelHandlerContext> channelHandlerContexts = multi.get(key);

        return channelHandlerContexts;

    }

    public static int getSize(String key) {

        List<ChannelHandlerContext> channelHandlerContexts = multi.get(key);
        return channelHandlerContexts.size();
    }

}
    