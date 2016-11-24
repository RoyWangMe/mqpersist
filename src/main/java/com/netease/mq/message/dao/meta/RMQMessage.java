package com.netease.mq.message.dao.meta;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzwangyujie on 2016/11/10.
 *
 * rmq message meta
 */
public class RMQMessage implements Delayed {

    private Long id;

    private String content;

    private String client;

    private String exchange;

    private String routingKey;

    private String msgNo;

    private long expireTime;

    public RMQMessage(String msgNo) {
        this.msgNo = msgNo;
    }

    public RMQMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof RMQMessage)){
            return false;
        }
        RMQMessage other = (RMQMessage)obj;
        if(other.getMsgNo() == null || this.getMsgNo() == null || !other.getMsgNo().equals(this.getMsgNo())){
            return false;
        }
        return true;
    }

    public long getDelay(TimeUnit unit) {
        return this.getExpireTime() - System.currentTimeMillis();
    }

    public int compareTo(Delayed o) {
        if(o == null || ! (o instanceof RMQMessage)) return 1;
        if(o == this) return 0;
        RMQMessage other = (RMQMessage)o;

        return (int)(this.getExpireTime() - other.getExpireTime());
    }
}
