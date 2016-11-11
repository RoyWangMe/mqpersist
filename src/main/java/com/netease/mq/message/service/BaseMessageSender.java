package com.netease.mq.message.service;

import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.message.dto.RMQMessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;

import java.nio.charset.Charset;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
public abstract class BaseMessageSender {

    protected Message buildMessage(RMQMessageDto rmqMessageDto) {
        return this.build(rmqMessageDto.getContent(), rmqMessageDto.getMsgNo());
    }

    protected Message buildMessage(RMQMessage rmqMessage) {
        return this.build(rmqMessage.getContent(), rmqMessage.getMsgNo());
    }

    private Message build(String content, String messageId){
        MessageBuilder builder = MessageBuilder.withBody(content.getBytes(Charset.forName("UTF-8")));
        builder.setContentType("application/json");
        builder.setMessageId(messageId);
        Message message = builder.build();
        return message;
    }
}
