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

    private static Map<String, List<String>> multi;

    static {
        multi = new ConcurrentHashMap<>();
    }

    /**
     * 一个用户加入到群聊
     * 房号为key
     * @param roomId
     * @param id
     */
    public static void add (String roomId, String id) {

        List<String> ids = multi.get(roomId);

        if (null == ids) {
            ids = new LinkedList<>();
        }

        ids.add(id);

        multi.put(roomId, ids);
    }


    /**
     * 移除群聊
     * @param id
     */
    public static void remove (String roomId, String id) {

        List<String> list = multi.get(roomId);

        if (null == list || list.isEmpty()) {
            return;
        }

        list.remove(id);
    }


    /**
     * 获取某个群中所有用户
     * @param key
     * @return
     */
    public static List<String> getChannels (String key) {

        List<String> ids = multi.get(key);

        return ids;

    }

    /**
     * 群聊人数
     * @param key
     * @return
     */
    public static int getSize(String key) {

        List<String> channelHandlerContexts = multi.get(key);
        return channelHandlerContexts.size();
    }

}
    