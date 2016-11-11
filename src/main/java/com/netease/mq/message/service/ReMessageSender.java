package com.netease.mq.message.service;

import com.netease.mq.heartbeat.callback.MessageCorrelationData;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.utils.HostUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
@Component
public class ReMessageSender extends BaseMessageSender {

    @Autowired
    private MessageMapper rmqMessageMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void resend(){
        // 获取物理地址
        String macAddr = HostUtils.getMacAddr();

        List<RMQMessage> messages = rmqMessageMapper.getMessageByClient(macAddr);

        for(RMQMessage message :  messages){
            final Message templateMessage = this.buildMessage(message);
            rabbitTemplate.send(message.getExchange(), message.getRoutingKey(), templateMessage, new MessageCorrelationData(message.getMsgNo()));
            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {

            }
        }
    }
}
