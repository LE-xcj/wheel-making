package com.xc.constant;

import com.xc.strategy.imp.TCPWriteStrategy;
import com.xc.strategy.imp.WebSocketWriteStrategy;

/**
 * @author chujian
 * @ClassName StrategyValue
 * @Description 功能描述
 * @date 2019/5/16 19:44
 */
public class StrategyValue {

    public static final TCPWriteStrategy TCP_WRITE_STRATEGY = new TCPWriteStrategy();

    public static final WebSocketWriteStrategy WEB_SOCKET_WRITE_STRATEGY = new WebSocketWriteStrategy();

}
    