package com.swcourse.rocketmq;

import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-06-22 16:06
 **/
public class OrderConsumeListener implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        for (MessageExt msg : msgs) {
            System.out.println("order listener 开始消费:" + new String(msg.getBody()));
        }
        try {
            Boolean success = RandomUtils.nextBoolean();
            if(success){
                System.out.println(new String(msgs.get(0).getBody()) + "处理order逻辑完成");
                return ConsumeOrderlyStatus.SUCCESS;
            }else {
                throw new RuntimeException("异常");
            }
        }catch (Exception e){
            System.out.println(new String(msgs.get(0).getBody()) + "处理order逻辑异常，重试");
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }

    }
}
