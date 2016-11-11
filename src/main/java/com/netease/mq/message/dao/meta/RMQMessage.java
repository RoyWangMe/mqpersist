package com.netease.mq.message.dao.meta;

/**
 * Created by hzwangyujie on 2016/11/10.
 *
 * rmq message meta
 */
public class RMQMessage {

    private Long id;

    private String content;

    private String client;

    private String exchange;

    private String routingKey;

    private String msgNo;

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj instanceof RMQMessage){
            return false;
        }
        RMQMessage other = (RMQMessage)obj;
        if(other.getMsgNo() == null || this.getMsgNo() == null || !other.getMsgNo().equals(this.getMsgNo())){
            return false;
        }
        return true;
    }
}
