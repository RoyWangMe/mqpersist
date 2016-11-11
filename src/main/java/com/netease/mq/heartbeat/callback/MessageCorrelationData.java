package com.netease.mq.heartbeat.callback;

import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
public class MessageCorrelationData extends CorrelationData {

    public MessageCorrelationData(String id) {
        super(id);
    }
}
