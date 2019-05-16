package com.xc.controller;

import com.alibaba.fastjson.JSON;
import com.xc.constant.ConstantValue;
import com.xc.container.MultiChatContainer;
import com.xc.container.SingleChatContainer;
import com.xc.pojo.Message;
import com.xc.pojo.dto.MessageConditionDTO;
import com.xc.pojo.dto.MessageDTO;
import com.xc.pojo.entity.ChannelHandlerContextEntity;
import com.xc.service.MessageService;
import com.xc.strategy.WriteStrategy;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chujian
 * @ClassName ChatServerController
 * @Description 功能描述
 * @date 2019/5/15 11:35
 */
public class ChatServerController {

    private static ChatServerController controller;

    private MessageService messageService = new MessageService();

    static {
        controller = new ChatServerController();
    }

    private ChatServerController() { }

    /**
     * 注意：传送的数据 dtoStr 必须是满足格式的json字符串
     * 格式：
     *
     *
     * @param sourceCtx
     * @param dtoStr
     */
    public void operate(ChannelHandlerContextEntity sourceCtx, String dtoStr) {

        MessageDTO dto = JSON.parseObject(dtoStr, MessageDTO.class);

        int action = dto.getAction();

        // 连接注册
        if (action == ConstantValue.REGISTE) {

            registe(sourceCtx, dto);

        } else {

            //转发
            foward(sourceCtx, dto);
        }

    }

    /**
     * 连接注册
     * @param sourceCtx
     * @param dto
     */
    private void registe(ChannelHandlerContextEntity sourceCtx, MessageDTO dto) {

        if (!checkIdentity (dto.getToken())) {
            autoRepsone(sourceCtx, "非法访问.....");
            sourceCtx.getChannelHandlerContext().close();
            return;
        }

        // 获取聊天的方式，单聊或群聊
        int type = dto.getType();

        // 发送员
        String source = dto.getSource();

        // 注册到容器中
        SingleChatContainer.add(source, sourceCtx);

        // 响应消息，服务器这边给用户反馈群聊的人数
        if (type == ConstantValue.SINGLECHAT) {

            // 获取未读的历史消息,并且更新他们之间的消息为已读
            getAndUpdateMessage(sourceCtx, dto, ConstantValue.UNREAD);

        } else {
            // target 是房号
            String target = dto.getTarget();

            // 将用户添加到对应房号
            MultiChatContainer.add(target, dto.getSource());

            int size = MultiChatContainer.getSize(target);
            autoRepsone(sourceCtx, "当前群里人数： " + size);

            MessageDTO comeDto = getDTO(dto, "服务器",dto.getSource() + "加入群聊.......");
            broadcast(comeDto);
        }

    }
    /**
     * 身份验证
     * @param token
     * @return
     */
    private boolean checkIdentity(String token) {

        // 这里模拟从缓存或者是token验证系统中检验该token是否有效
        if (null == token || token.length() > 4) {
            return false;
        }

        return true;
    }


    /**
     * 消息转发
     * @param sourceCtx
     * @param dto
     */
    public void foward(ChannelHandlerContextEntity sourceCtx, MessageDTO dto) {


        // 目的channel
        ChannelHandlerContextEntity ctx;

        // 聊天类型，传送目的地， 传送内容
        int type = dto.getType();
        String target = dto.getTarget();

        // 单聊
        if (type == ConstantValue.SINGLECHAT) {

            ctx = SingleChatContainer.getChannel(target);
            response(ctx, dto, sourceCtx);

        } else {

            broadcast(dto);

        }

    }


    /**
     * 群聊广播
     * @param channels
     * @param dto
     *
     */
    private void broadcast(List<ChannelHandlerContextEntity> channels, MessageDTO dto) {

        // 将数据编程json字符串
        String strDto = JSON.toJSONString(dto);

        // 遍历广播
        for (ChannelHandlerContextEntity ctx : channels) {

            // 获取其中绑定的策略
            WriteStrategy strategy = ctx.getStrategy();

            // 获取目的channel
            ChannelHandlerContext channelHandlerContext = ctx.getChannelHandlerContext();

            // 发送
            strategy.writeAndFlush(channelHandlerContext, strDto);

        }

    }


    /**
     * 单聊
     * @param ctx
     * @param dto
     * @param sourceCtx
     */
    private void response(ChannelHandlerContextEntity ctx, MessageDTO dto, ChannelHandlerContextEntity sourceCtx) {

        int status = 0;

        if (ctx == null) {

            autoRepsone(sourceCtx, "对方已经下线了......");

        } else {

            //封装
            List<ChannelHandlerContextEntity> channels = new ArrayList<>();
            channels.add(ctx);

            // 广播
            broadcast(channels, dto);

            // 消息为已读
            status = 1;
        }


        // 将未送到和发送到的消息保存到数据库
        saveMessage(dto, status);
    }


    /**
     * 服务器自动消息
     * @param sourceCtx
     * @param content
     */
    private void autoRepsone (ChannelHandlerContextEntity sourceCtx, String content) {

        // 发送策略
        WriteStrategy strategy = sourceCtx.getStrategy();

        // 封装响应内容
        MessageDTO dto = new MessageDTO();
        dto.setSource("服务器");
        dto.setContent(content);
        String str = JSON.toJSONString(dto);

        // 响应
        strategy.writeAndFlush(sourceCtx.getChannelHandlerContext(), str);
    }


    public static ChatServerController getController() {
        return controller;
    }


    /**
     * 用于广播哪个用户进入群了
     * @param dto
     */
    private void broadcast(MessageDTO dto) {

        String roomID = dto.getTarget();

        // 获取群聊中的ids
        List<String> ids = MultiChatContainer.getChannels(roomID);
        List<ChannelHandlerContextEntity> channels = new ArrayList<>(ids.size());

        // 封装
        for (String id : ids) {

            // 不用发给自己
            if (id.equals(dto.getSource())) {
                continue;
            }

            // 封装
            ChannelHandlerContextEntity channel = SingleChatContainer.getChannel(id);
            channels.add(channel);
        }

        // 广播
        broadcast (channels, dto);


    }


    /**
     * copy dto
     * @param dto
     * @param source
     * @return
     */
    private MessageDTO getDTO(MessageDTO dto, String source, String content) {

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setSource(source);
        messageDTO.setTarget(dto.getTarget());
        messageDTO.setToken(dto.getToken());
        messageDTO.setType(dto.getType());
        messageDTO.setAction(dto.getAction());
        messageDTO.setContent(content);

        return messageDTO;
    }


    /**
     * 保存消息到数据库
     * @param dto
     * @param status
     */
    private void saveMessage(MessageDTO dto, int status) {

        Message message = new Message();

        message.setSources(dto.getSource());
        message.setTarget(dto.getTarget());
        message.setContent(dto.getContent());
        message.setStatus(status);

        messageService.insertSelective(message);

    }


    /**
     * 获取并更新未读消息
     * @param sourceCtx
     * @param dto
     * @param status
     */
    private void getAndUpdateMessage(ChannelHandlerContextEntity sourceCtx, MessageDTO dto, int status) {

        // 查询条件
        MessageConditionDTO conditionDTO = new MessageConditionDTO();

        // 封装message
        Message condition = new Message();

        // 注意这里要反过来，因为登录的用户就是未读消息的目的用户
        condition.setTarget(dto.getSource());
        condition.setStatus(status);

        // 填充
        conditionDTO.setCondition(condition);

        // 查询
        String messages = messageService.selectByCondition(conditionDTO);

        // 反馈到登录用户
        autoRepsone(sourceCtx, "\n历史消息\n" + messages);

        // 已读
        conditionDTO.setNewStatus(1);

        // 更新信息状态
        messageService.updateByCondition(conditionDTO);
    }

}
    