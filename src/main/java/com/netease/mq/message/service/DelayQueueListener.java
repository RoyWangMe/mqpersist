package com.netease.mq.message.service;

import com.netease.mq.client.DefaultSenderImpl;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hzwangyujie on 2016/11/12.
 */
@Component
public class DelayQueueListener {

    @Autowired
    private MessageMapper messageMapper;

    public void persistMessage(){
        Thread thread = new Thread(new DelayQueueHandleThread());
        thread.start();
    }

    class DelayQueueHandleThread implements Runnable{

        public void run() {
            while (true) {
                try {
                    // 超过2s未被callback的消息需要持久化
                    RMQMessage rmqMessage = DefaultSenderImpl.innerQueue.take();
                    messageMapper.saveRMQMessage(rmqMessage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
