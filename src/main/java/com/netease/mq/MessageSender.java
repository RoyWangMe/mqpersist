package com.netease.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzwangyujie on 2016/11/1.
 */
public class MessageSender {

    @Resource(name = "myAmqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Resource(name = "myAmqpTemplateNoDurable")
    private AmqpTemplate amqpTemplateNoDurable;

    public void sendMessage(Object message){
        amqpTemplate.convertAndSend("hello", message);
    }

    public void sendMessageNoDurable(Object message){
        amqpTemplateNoDurable.convertAndSend("hello_test", message);
    }
}
