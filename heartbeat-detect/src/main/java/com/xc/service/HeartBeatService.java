package com.xc.service;

import com.alibaba.fastjson.JSON;
import com.xc.pojo.vo.ProcessInfoVO;
import com.xc.pojo.vo.SystemAndProcessVO;
import com.xc.pojo.vo.SystemInfoVO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chujian
 * @ClassName HeartBeatService
 * @Description 功能描述
 * @date 2019/5/19 9:44
 */
public class HeartBeatService {

    private Map<String, SystemAndProcessVO> map = new ConcurrentHashMap<String, SystemAndProcessVO>();

    public void show(String info) {

        System.out.println(info);
        SystemAndProcessVO vo = JSON.parseObject(info, SystemAndProcessVO.class);

        SystemInfoVO systemInfo = vo.getSystemInfo();
        ProcessInfoVO processInfo = vo.getProcessInfo();

        System.out.println(systemInfo);
        System.out.println(processInfo);
        String ip = systemInfo.getIp();

        SystemAndProcessVO systemAndProcessVO = map.get(ip);
        if (systemAndProcessVO == null) {

        }


    }

}
    