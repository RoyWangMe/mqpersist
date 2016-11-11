package com.netease.mq.heartbeat.listener;

import com.netease.mq.message.service.ReMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hzwangyujie on 2016/11/10.
 *
 * 将持久化的消息重新推送
 *
 */
@Component
public class HBSendMsgListener extends ConnectListener{

    @Autowired
    private ReMessageSender messageSender;

    public void doListen() {
        messageSender.resend();
        // TODO: 2016/11/10
        System.out.println("push message again");
    }
}
