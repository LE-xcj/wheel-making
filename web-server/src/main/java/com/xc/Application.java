package com.xc;

import com.xc.constant.ConstantValue;
import com.xc.server.WebServerAlive;

/**
 * @author chujian
 * @ClassName Application
 * @Description 功能描述
 * @date 2019/5/19 11:57
 */
public class Application {

    public static void main(String[] args){
        WebServerAlive.connect(ConstantValue.HEARTBEAT_DETECT_IP, ConstantValue.HEARTBEAT_DETECT_PORT);
    }

}
    