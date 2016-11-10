package com.netease.mq.message.dao;

import com.netease.mq.message.dao.annotation.Mapper;
import com.netease.mq.message.dao.meta.RMQMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hzwangyujie on 2016/11/10.
 */
@Mapper
public interface MessageMapper {

    /**
     * 保存RMQ Message
     *
     * @param rmqMessage
     * @return
     */
    int saveRMQMessage(RMQMessage rmqMessage);

    /**
     * 通过当前客户端获取持久化但未发送成功消息
     *
     * @param client
     * @return
     */
    List<RMQMessage> getMessageByClient(@Param("client") String client);

}
