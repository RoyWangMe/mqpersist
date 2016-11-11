package com.netease.mq.message.dto;

import java.io.Serializable;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
public class RMQMessageDto implements Serializable{

    private String content;

    private String msgNo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }
}
