package com.netease.mq.client;

import com.netease.mq.heartbeat.HeartBeatHandler;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.message.dto.RMQMessageDto;
import com.netease.mq.message.generator.NumGenerator;
import com.netease.mq.message.service.DelayQueueListener;
import com.netease.mq.message.service.MessageSender;
import com.netease.mq.utils.HostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * Created by hzwangyujie on 2016/11/10.
 */
@Component
public class DefaultSenderImpl implements HSender {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private HeartBeatHandler heartBeatHandler;

    @Autowired
    private MessageMapper rmqMessageMapper;

    @Autowired
    private NumGenerator numGenerator;

    public static BlockingQueue<RMQMessage> innerQueue = new DelayQueue<RMQMessage>();

    @Autowired
    private DelayQueueListener delayQueueListener;

    private volatile boolean heartBeatOpen = false;

    private volatile boolean persistListenOpen = false;

    public void send(String message, String exchange, String routingKey) {
        String messageNo = numGenerator.generate();

        innerQueue.add(this.buildRMQMessage(message, messageNo, exchange, routingKey));

        messageSender.send(exchange, routingKey, this.buildRMQMessageDto(message, messageNo));
        if(!heartBeatOpen){
            // 开始发送就开始heartBeat 线程
            heartBeatHandler.sendHeartBeat();
            heartBeatOpen = true;
        }
        if(!persistListenOpen){
            delayQueueListener.persistMessage();
        }
    }

    private RMQMessageDto buildRMQMessageDto(String content, String messageNo){

        RMQMessageDto rmqMessageDto = new RMQMessageDto();
        rmqMessageDto.setContent(content);
        rmqMessageDto.setMsgNo(messageNo);

        return rmqMessageDto;
    }

    private RMQMessage buildRMQMessage(String content, String msgNo, String exchange, String routingKey){
        RMQMessage rmqMessage = new RMQMessage();

        rmqMessage.setClient(HostUtils.MAC_ADDR);
        rmqMessage.setContent(content);
        rmqMessage.setExchange(exchange);
        rmqMessage.setRoutingKey(routingKey);
        rmqMessage.setMsgNo(msgNo);
        // 2s 不进行callback则进行持久化
        rmqMessage.setExpireTime(System.currentTimeMillis() + 2000l);

        return rmqMessage;
    }
}
