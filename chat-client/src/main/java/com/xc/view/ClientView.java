package com.xc.view;

import com.xc.client.Client;

import java.util.Scanner;

/**
 * @author chujian
 * @ClassName ClientView
 * @Description 功能描述
 * @date 2019/5/15 10:23
 */
public class ClientView {

    public ClientView(String ip, int port) {
        
        init(ip, port);

    }

    private void init(String ip, int port) {

        Scanner input = new Scanner(System.in);

        System.out.print("你的id： ");
        String source = input.next();

        System.out.print("1：单聊； 2：群聊 ");
        int type = input.nextInt();



        if (type == 1) {
            System.out.print("另一个人的id：");
        } else {
            System.out.print("房间号：");
        }

        String target = input.next();

        System.out.print("你的token：");
        String token = input.next();

        //实例化
        new Client(ip, port, source, target, token, type);
    }

}
    