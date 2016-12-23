package com.netease.mq;

import com.netease.mq.client.HSender;
import com.netease.mq.message.dao.MessageMapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hzwangyujie on 2016/11/1.
 */
public class MessageSenderTest {

    private ApplicationContext context;

    @Before
    public void before(){
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testMessageSendNoDurable() throws InterruptedException {
        HSender messageSender = (HSender) context.getBean("defaultSenderImpl");
        int count = 1;
        for(int index = 0 ; index < 1000 ; index ++ ){
            for(int i = 0 ; i < 1000 ; i++){
                byte[] bigData = new byte[10];
                System.out.println("send msg = " + count++);
                messageSender.send(new String(bigData), "myChangeNoDurableNew", "hello_test_new");
            }
        }
    }

    @Test
    public void testDBSaveMessage(){
        MessageMapper rmqMessageMapper = (MessageMapper)context.getBean("messageMapper");
        RMQMessage message = new RMQMessage();
        message.setRoutingKey("44");
        message.setExchange("33");
        message.setContent("22");
        message.setClient("11");
        rmqMessageMapper.saveRMQMessage(message);
    }

    @Test
    public void testDBDeleteMessage(){
        MessageMapper rmqMessageMapper = (MessageMapper)context.getBean("messageMapper");
        rmqMessageMapper.deleteRMQMessage("201611111603152");
    }

    @Test
    public void testConsumeMessage() throws InterruptedException {
        Thread.sleep(1000000l);
    }

}
