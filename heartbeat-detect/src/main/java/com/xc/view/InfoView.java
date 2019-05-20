package com.xc.view;

import com.xc.pojo.vo.ProcessInfoVO;
import com.xc.pojo.vo.SystemInfoVO;

import java.util.Map;

/**
 * @author chujian
 * @ClassName InfoView
 * @Description 功能描述
 * @date 2019/5/19 14:45
 */
public class InfoView {


    public void showSystems(SystemInfoVO systemInfoVO) {

        System.out.println(systemInfoVO);
    }

    public void showProcesses(Map<Long, ProcessInfoVO> processes) {

        // 遍历记录的进程信息

        for (Map.Entry<Long, ProcessInfoVO> process : processes.entrySet()) {

            ProcessInfoVO pro = process.getValue();
            System.out.println(pro);

        }

        System.out.println("========================");
    }
}
    