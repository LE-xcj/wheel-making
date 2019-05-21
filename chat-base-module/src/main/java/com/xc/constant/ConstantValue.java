package com.xc.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chujian
 * @ClassName ConstantValue
 * @Description 功能描述
 * @date 2019/5/14 18:21
 */
public class ConstantValue {

    public static final int HEAD_DATA = 0x7f;

    //标志和内容长度这两个信息
    public static final int BASE_LENGTH = 4 + 4;

    public static final String CHARSET = "utf-8";

    public static final int REGISTE = 1;

    public static final int SINGLECHAT = 1;

    public static final int READ_IDLE_TIME_OUT = 60; // 读超时

    public static final int WRITE_IDLE_TIME_OUT = 0;// 写超时

    public static final int ALL_IDLE_TIME_OUT = 0; // 所有超时


    public static final int TCP_PORT = 10086;

    public static final int WEBSOCKET_PORT = 10087;

    public static final List<Integer> ports;

    static {
        ports = new ArrayList<Integer>();
        ports.add(TCP_PORT);
        ports.add(WEBSOCKET_PORT);
    }

    public static final int UNREAD = 0;


    // 分隔符
    public static final String SEPERATOR = "${}";



    public static final String HEARTBEAT_DETECT_IP = "127.0.0.1";

    public static final int HEARTBEAT_DETECT_PORT = 10086;


    public static final double MEMORY_PERCENT_THRESHOLD = 0.5;

    public static final int HEARTBEAT_DETECT_PERIOD = 5;
}
    