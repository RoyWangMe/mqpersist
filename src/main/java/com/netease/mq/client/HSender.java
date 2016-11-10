package com.netease.mq.client;

/**
 * Created by hzwangyujie on 2016/11/10.
 */
public interface HSender {

    void send(String message, String exchange, String routingKey);
}
