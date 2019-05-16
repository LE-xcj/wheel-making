package com.xc.service;

import com.xc.container.MapperContainer;
import com.xc.mapper.MessageMapper;
import com.xc.pojo.Message;
import com.xc.pojo.dto.MessageConditionDTO;

import java.util.List;

/**
 * @author chujian
 * @ClassName MessageService
 * @Description 功能描述
 * @date 2019/5/16 21:54
 */
public class MessageService {

    private MessageMapper messageMapper = MapperContainer.getMessageMapper();


    public void insertSelective(Message message) {

        if (null == message) {
            return;
        }

        messageMapper.insertSelective(message);

    }


    /**
     * 获取所有消息的字符串形式
     * @param conditionDTO
     * @return
     */
    public String selectByCondition(MessageConditionDTO conditionDTO) {

        StringBuilder history = new StringBuilder();
        List<Message> messages = this.listMessage(conditionDTO);

        for (Message message : messages) {

            // 注明消息的来源
            history.append(message.getSendDate())
                    .append("\t")
                    .append(message.getSources())
                    .append(" : ")
                    .append(message.getContent())
                    .append("\n");

        }

        return history.toString();

    }


    /**
     * 批量获取消息
     * @param conditionDTO
     * @return
     */
    public List<Message> listMessage(MessageConditionDTO conditionDTO) {

        if (null == conditionDTO) {
            return null;
        }

        return messageMapper.listMessage(conditionDTO);
    }

    public void updateByCondition(MessageConditionDTO conditionDTO) {

        if (null == conditionDTO) {
            return;
        }

        messageMapper.updateByCondition(conditionDTO);

    }
}
    