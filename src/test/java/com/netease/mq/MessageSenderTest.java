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
    public void testMessageSend() throws InterruptedException {
//        for(int index = 0 ; index < 1000 ; index ++ ){
//            for(int i = 0 ; i < 10000 ; i++){
//                MessageSender messageSender = (MessageSender)context.getBean("messageSender");
//                byte[] bigData = new byte[1000];
//                messageSender.sendMessage(bigData);
//            }
//            Thread.sleep(10l);
//        }
        System.out.println("mq start");
        Thread.sleep(1000000000l);
    }

    @Test
    public void testMessageSendNoDurable() throws InterruptedException {
        HSender messageSender = (HSender) context.getBean("defaultSenderImpl");
        for(int index = 0 ; index < 1000 ; index ++ ) {
            String message = "test" + index;
            messageSender.send(message, "myChangeNoDurable", "hello_test");
            Thread.sleep(3000l);
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
    public void testConsumeMessage() throws InterruptedException {
        Thread.sleep(1000000l);
    }

}
