package com.swcourse.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 0.1.0
 * @create 2022-06-21 15:37
 **/
@RestController
public class MQController {
    @GetMapping(value = "/syncSend")
    public String syncSend() throws Exception{
        // 创建消息生产者 producer，并设置生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        // 设置 Nameserver 地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动 producer
        producer.start();

        for (int i = 0; i < 3; i++) {
            // 创建消息对象，指定主题Topic、Tag和消息体
            Message msg = new Message("swcourse-topic", null, ("Hello SkyWalking" + i).getBytes());
            // 发送消息
            SendResult result = producer.send(msg);
            System.out.println("发送结果:" + result);
        }
        // 关闭生产者producer
        producer.shutdown();
        return "success";
    }

    @GetMapping(value = "/asyncSend")
    public String asyncSend() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        int size = 3;
        for (int i = 0; i < size; i++) {
            Message msg = new Message("swcourse-topic", null, ("Hello SkyWalking" + i).getBytes());
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("回调成功结果：" + sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    System.out.println("回调异常结果：" + e);
                }
            });
        }
        producer.shutdown();
        return "success";
    }

    @GetMapping(value = "/oneWaySend")
    public String oneWaySend() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("swcourse-topic", null, ("Hello World" + i).getBytes());
            producer.sendOneway(msg);
        }
        producer.shutdown();
        return "success";
    }

    @GetMapping(value = "/consume")
    public String consume() throws Exception{
        // 创建消费者Consumer，设置消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");
        // 指定Nameserver地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅主题Topic和Tag
        consumer.subscribe("swcourse-topic", "*");
        //设置回调函数，处理消息
        consumer.registerMessageListener(new ConsumeListener());
        consumer.start();
        return "success";
    }

    @GetMapping(value = "/consumeOrder")
    public String consumeOrder() throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("swcourse-topic", "*");
        consumer.registerMessageListener(new OrderConsumeListener());
        consumer.start();
        return "success";
    }

    @GetMapping(value = "/oneQueueSyncSend")
    public String oneQueueSyncSend() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 3; i++) {
            Message msg = new Message("swcourse-topic2", null, ("Hello SkyWalking" + i).getBytes());
            // 发送消息时指定队列选择器
            SendResult result = producer.send(msg, new MQMessageQueueSelector(), null);
            System.out.println("发送结果:" + result);
        }
        producer.shutdown();
        return "success";
    }

    @GetMapping(value = "/oneQueueConsumeOrder")
    public String oneQueueConsumeOrder() throws Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("swcourse-topic2", "*");
        consumer.registerMessageListener(new OrderConsumeListener());
        consumer.start();
        return "success";
    }
}
