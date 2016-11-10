package com.netease.mq.message.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzwangyujie on 2016/11/10.
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(MessageSender.class);

    //TODO
    private ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public void sendAsync(final String exchange, final String routingKey, final String message){
        final Message templateMessage = this.buildMessage(message);
        threadPool.submit(new Runnable() {
            public void run() {
                try {
                    rabbitTemplate.send(exchange, routingKey, templateMessage);
                }catch (Exception e) {
                    logger.error("rabbitmq send message error!");
                }
            }
        });
    }

    public void send(final String exchange, final String routingKey, final String message) {
        final Message templateMessage = this.buildMessage(message);
        rabbitTemplate.send(exchange, routingKey, templateMessage);
    }

    private Message buildMessage(String messageContent) {
        MessageBuilder builder = MessageBuilder.withBody(messageContent.getBytes(Charset.forName("UTF-8")));
        builder.setContentType("application/json");
        Message message = builder.build();
        return message;
    }

}
