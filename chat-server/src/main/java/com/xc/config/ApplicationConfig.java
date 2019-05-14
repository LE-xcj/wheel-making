package com.xc.config;

import com.xc.server.ChatServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author chujian
 * @ClassName ApplicationConfig
 * @Description 功能描述
 * @date 2019/5/14 17:36
 */
@SpringBootApplication
@Configuration
public class ApplicationConfig implements CommandLineRunner{

    @Override
    public void run(String... args) throws Exception {
        ChatServer.work();
    }

}
    