package com.netease.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hzwangyujie on 2016/10/31.
 */
public class App {

    public static void main(String[]args){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
    }

}
