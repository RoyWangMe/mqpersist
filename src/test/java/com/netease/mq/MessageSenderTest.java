package com.netease.mq;

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
        for(int index = 0 ; index < 1000 ; index ++ ){
            for(int i = 0 ; i < 10000 ; i++){
                MessageSender messageSender = (MessageSender)context.getBean("messageSender");
                byte[] bigData = new byte[1000];
                messageSender.sendMessage(bigData);
            }
            Thread.sleep(10l);
        }
    }

    @Test
    public void testMessageSendNoDurable() throws InterruptedException {
        for(int index = 0 ; index < 1000 ; index ++ ) {
            for (int i = 0; i < 10000; i++) {
                MessageSender messageSender = (MessageSender) context.getBean("messageSender");
                byte[] bigData = new byte[1000];
                messageSender.sendMessageNoDurable(bigData);
            }
            Thread.sleep(10l);
        }
    }

    @Test
    public void testConsumeMessage() throws InterruptedException {
        Thread.sleep(1000000l);
    }


}
