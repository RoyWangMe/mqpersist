package com.netease.mq.message.generator;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hzwangyujie on 2016/11/11.
 */
@Component
public class NumGenerator {

    private AtomicInteger numSuffix = new AtomicInteger();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public String generate(){
        String num = sdf.format(new Date());
        return num + numSuffix.incrementAndGet();
    }

}
