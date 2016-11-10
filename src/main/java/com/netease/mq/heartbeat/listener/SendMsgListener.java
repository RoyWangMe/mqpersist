package com.netease.mq.heartbeat.listener;

import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import com.netease.mq.message.service.MessageSender;
import com.netease.mq.utils.HostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hzwangyujie on 2016/11/10.
 *
 * 将持久化的消息重新推送
 *
 */
@Component
public class SendMsgListener extends ConnectListener{

    @Autowired
    private MessageMapper rmqMessageMapper;

    @Autowired
    private MessageSender messageSender;

    public void doListen() {
        // 获取物理地址
        String macAddr = HostUtils.getMacAddr();

        List<RMQMessage> messages = rmqMessageMapper.getMessageByClient(macAddr);

        for(RMQMessage message :  messages){
            messageSender.send(message.getExchange(), message.getRoutingKey(), message.getContent());
            try {
                Thread.sleep(200l);
            } catch (InterruptedException e) {

            }
        }
        // TODO: 2016/11/10
        System.out.println("push message again");
    }
}
