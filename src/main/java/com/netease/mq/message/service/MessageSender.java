package com.netease.mq.message.service;

import com.netease.mq.heartbeat.callback.MessageCallBack;
import com.netease.mq.heartbeat.callback.MessageCorrelationData;
import com.netease.mq.message.dto.RMQMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzwangyujie on 2016/11/10.
 */
@Component
public class MessageSender extends BaseMessageSender implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageCallBack messageCallBack;

    private Logger logger = LoggerFactory.getLogger(MessageSender.class);

    //TODO
    private ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(messageCallBack);
    }

    public void sendAsync(final String exchange, final String routingKey, final RMQMessageDto rmqMessageDto){
        final Message templateMessage = this.buildMessage(rmqMessageDto);
        threadPool.submit(new Runnable() {
            public void run() {
                try {
                    rabbitTemplate.send(exchange, routingKey, templateMessage, new MessageCorrelationData(rmqMessageDto.getMsgNo()));
                }catch (Exception e) {
                    logger.error("rabbitmq send message error!", e);
                }
            }
        });
    }

    public void send(final String exchange, final String routingKey, final RMQMessageDto rmqMessageDto){
        final Message templateMessage = this.buildMessage(rmqMessageDto);
        rabbitTemplate.send(exchange, routingKey, templateMessage, new MessageCorrelationData(rmqMessageDto.getMsgNo()));
    }

}
