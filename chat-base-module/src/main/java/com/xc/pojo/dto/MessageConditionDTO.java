package com.xc.pojo.dto;

import com.xc.pojo.Message;

/**
 * @author chujian
 * @ClassName MessageConditionDTO
 * @Description 功能描述
 * @date 2019/5/16 21:57
 */
public class MessageConditionDTO {

    private Message condition;

    private int newStatus;

    public Message getCondition() {
        return condition;
    }

    public void setCondition(Message condition) {
        this.condition = condition;
    }

    public int getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(int newStatus) {
        this.newStatus = newStatus;
    }
}
    