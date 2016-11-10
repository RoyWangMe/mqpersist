package com.netease.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by hzwangyujie on 2016/11/1.
 */
public class MessageReceiverNoDurable implements MessageListener{

    public void onMessage(Message message) {
        System.out.println("first test nodurable:" + message);
    }
}
