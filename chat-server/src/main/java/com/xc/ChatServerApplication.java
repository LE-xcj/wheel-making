package com.xc;


import com.xc.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;

/**
 * @author chujian
 * @ClassName ChatServerApplication
 * @Description 功能描述
 * @date 2019/5/14 17:35
 */

public class ChatServerApplication {

    public static void main(String[] args){
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
    