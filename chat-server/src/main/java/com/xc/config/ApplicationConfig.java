package com.xc.config;

import com.xc.container.MapperContainer;
import com.xc.mapper.MessageMapper;
import com.xc.server.ChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chujian
 * @ClassName ApplicationConfig
 * @Description 功能描述
 * @date 2019/5/14 17:36
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.xc")
public class ApplicationConfig implements CommandLineRunner{

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(messageMapper);
        MapperContainer.setMessageMapper(this.messageMapper);
        ChatServer.work();
    }

}
    