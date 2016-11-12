package com.netease.mq.heartbeat.callback;

import com.netease.mq.client.DefaultSenderImpl;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.utils.LogUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
@Component
public class MessageCallBack implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MessageMapper messageMapper;

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        MessageCorrelationData messageCorrelationData = (MessageCorrelationData)correlationData;
        if(ack){
            String msgId = messageCorrelationData.getId();
            // 如果再内存queue里面还没被持久化，则直接内存删除，否则去db删除
            if(DefaultSenderImpl.innerQueue.remove(new RMQMessage(msgId))){
                messageMapper.deleteRMQMessage(msgId);
            }
            LogUtils.LOGGER.info("ack delete persist msg {}", msgId);
        }else{
            LogUtils.LOGGER.info("message call back ack false");
        }
    }
}
