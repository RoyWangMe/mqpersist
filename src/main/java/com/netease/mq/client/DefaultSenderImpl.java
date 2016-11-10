package com.netease.mq.client;

import com.netease.mq.heartbeat.HeartBeatHandler;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.message.service.MessageSender;
import com.netease.mq.utils.HostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void send(String message, String exchange, String routingKey) {

        messageSender.sendAsync(exchange, routingKey, message);
        // 开始发送就开始heartBeat 线程
        heartBeatHandler.sendHeartBeat();

        rmqMessageMapper.saveRMQMessage(this.buildRMQMessage(message, exchange, routingKey));
    }

    private RMQMessage buildRMQMessage(String message, String exchange, String routingKey){
        RMQMessage rmqMessage = new RMQMessage();

        rmqMessage.setClient(HostUtils.getMacAddr());
        rmqMessage.setContent(message);
        rmqMessage.setExchange(exchange);
        rmqMessage.setRoutingKey(routingKey);

        return rmqMessage;
    }
}
