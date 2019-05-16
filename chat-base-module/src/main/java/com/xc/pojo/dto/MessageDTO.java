package com.xc.pojo.dto;

/**
 * @author chujian
 * @ClassName MessageDTO
 * @Description 功能描述
 * @date 2019/5/15 10:49
 */
public class MessageDTO {

    private String source;
    private String target;
    private String token;
    private int type;
    private int action;

    private String content;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
    