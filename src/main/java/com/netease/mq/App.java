package com.netease.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hzwangyujie on 2016/10/31.
 */
public class App {

    private static byte[] bytes;

    public static void main(String[] args) {
//        bytes = null;
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("1231",bytes);
//        ThreadLocal threadLocal = new ThreadLocal();
//
//        threadLocal.set(new byte[1000000]);
//        //bytes = null;
//        //threadLocal.remove();
//        System.gc();
//
//        threadLocal.remove();
//
//        System.gc();
//
//        System.out.println("done");
//        Thread test = new Thread(new Runnable() {
//            public void run() {
//                byte[] bytes = new byte[100];
//            }
//        });
//        test.start();
        byte[] bytes = new byte[100];
        System.gc();
       // System.out.println("");
    }
}
