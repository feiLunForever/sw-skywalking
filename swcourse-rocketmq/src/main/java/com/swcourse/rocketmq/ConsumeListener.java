package com.swcourse.rocketmq;

import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-06-22 16:06
 **/
public class ConsumeListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs) {
            System.out.println("正常 listener 开始消费:" + new String(msg.getBody()));
        }
        try {
            Boolean success = RandomUtils.nextBoolean();
            if(success){
                System.out.println(new String(msgs.get(0).getBody()) + "处理正常逻辑完成");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }else {
                throw new RuntimeException("异常");
            }
        }catch (Exception e){
            System.out.println(new String(msgs.get(0).getBody()) + "处理正常逻辑异常，重试");
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
