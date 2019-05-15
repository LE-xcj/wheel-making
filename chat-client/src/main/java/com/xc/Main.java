package com.xc;

import com.xc.client.Client;
import com.xc.view.ClientView;

/**
 * @author chujian
 * @ClassName Main
 * @Description 功能描述
 * @date 2019/5/14 19:10
 */
public class Main {


    public static void main(String[] args){
        new ClientView("127.0.0.1", 10086);
    }

}
    