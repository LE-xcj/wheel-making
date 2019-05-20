package com.xc.service;

import com.alibaba.fastjson.JSON;
import com.xc.alarm.Alarm;
import com.xc.pojo.vo.ProcessInfoVO;
import com.xc.pojo.vo.SystemAndProcessVO;
import com.xc.pojo.vo.SystemInfoVO;
import com.xc.view.InfoView;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chujian
 * @ClassName HeartBeatService
 * @Description 功能描述
 * @date 2019/5/19 9:44
 */
public class HeartBeatService {

    private Map<String, SystemInfoVO> systemInfos = new ConcurrentHashMap<String, SystemInfoVO>();

    private Map<String, Map<Long, ProcessInfoVO>> processInfos = new ConcurrentHashMap<String, Map<Long, ProcessInfoVO>>();

    private InfoView view = new InfoView();

    public void add(String info, Alarm alarm) {

        // 获取vo中的系统和进程信息
        SystemAndProcessVO vo = JSON.parseObject(info, SystemAndProcessVO.class);
        SystemInfoVO systemInfo = vo.getSystemInfo();
        ProcessInfoVO processInfo = vo.getProcessInfo();

        // 添加到容器中
        add(systemInfo, processInfo);


        // 展示系统和对应进程的信息
        transferSystemAndProcessInfo(alarm);


    }

    private void transferSystemAndProcessInfo(Alarm alarm) {

        // 遍历主机群
        for (Map.Entry<String, SystemInfoVO> entry : systemInfos.entrySet()) {

            // 根据ip获取对应的主机信息
            String ip = entry.getKey();
            SystemInfoVO system = entry.getValue();

            // 展示主机信息
            view.showSystems(system);

            // 告警规则
            alarm.alarm (system);

            // 根据ip获取对应主机下的所有进程信息
            Map<Long, ProcessInfoVO> processes = processInfos.get(ip);
            view.showProcesses(processes);


        }

    }


    /**
     * 添加主机和进程信息
     * @param systemInfo
     * @param processInfo
     */
    private void add(SystemInfoVO systemInfo, ProcessInfoVO processInfo) {

        String ip = systemInfo.getIp();

        addSystem(ip, systemInfo);

        addProcess(ip, processInfo);

    }

    /**
     * 添加进程信息
     * @param processInfo
     * @param ip
     */
    private void addProcess(String ip, ProcessInfoVO processInfo) {

        if (null == ip) {
            return;
        }

        // 某个主机下的进程信息
        Map<Long, ProcessInfoVO> processes = processInfos.get(ip);
        if (null == processes) {
            processes = new ConcurrentHashMap<Long, ProcessInfoVO>();
        }

        // 添加进程信息
        long pid = processInfo.getPid();
        processes.put(pid, processInfo);

        // 添加到进程容器
        processInfos.put(ip, processes);

    }


    /**
     * 添加系统信息
     * @param ip
     * @param systemInfo
     */
    private void addSystem(String ip, SystemInfoVO systemInfo) {

        if (null == ip) {
            return;
        }

        // 天机主机信息
        systemInfos.put(ip, systemInfo);

    }



}
    