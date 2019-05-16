package com.xc.mapper;

import com.xc.pojo.Message;
import com.xc.pojo.dto.MessageConditionDTO;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);


    int insertSelective(Message record);

    List<Message> listMessage(MessageConditionDTO conditionDTO);

    void updateByCondition(MessageConditionDTO conditionDTO);
}