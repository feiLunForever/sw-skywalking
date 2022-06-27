package com.swcourse.rocketmq;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-06-23 10:08
 **/
public class MQMessageQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        return mqs.get(0);
    }
}
