package com.sour.mq;

import com.sour.mq.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringbootMqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     *  1,单播(点对点)
     *
     * @author xgl
     * @date 2020/8/9 17:22
     **/
    @Test
    void contextLoads() {

//        message需要自己构造一个, 定义消息体内容和信息头
//        rabbitTemplate.send(exchange, routeKey, message);


//        只需要传入要发送的对象, 就可以自动序列化发送给rabbitmq
//        object默认当成消息体
//        rabbitTemplate.convertAndSend(exchange, routeKey, object);

        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个信息");
        map.put("data", Arrays.asList("helloworld", 1234, true));

        // 对象被默认序列化以后发送  (后面在MyAmqpConfig里面配置成转成json才发送)
        rabbitTemplate.convertAndSend("exchange.direct", "sout.new", map);

        Book book = new Book("我是一本书", "Sour");
        rabbitTemplate.convertAndSend("exchange.direct", "sout.new", book);
    }


    /**
     *      接收消息
     *
     * @author xgl
     * @date 2020/8/9 17:36
     **/
    @Test
    public void receive() {

        Object o = rabbitTemplate.receiveAndConvert("sout.new");
        System.out.println(o.getClass());
        System.out.println(o);
        //  class java.util.HashMap
        //  {msg=这是第一个信息, data=[helloworld, 1234, true]}

        o = rabbitTemplate.receiveAndConvert("sout.new");
        System.out.println(o.getClass());
        System.out.println(o);

    }


    /**
     *  广播发送
     *
     * @author xgl
     * @date 2020/8/9 18:06
     **/
    @Test
    public void sendMsg() {

        Book book = new Book("广播发送", "Sourlun");

        rabbitTemplate.convertAndSend("exchange.fanout", "", book);

    }


    /**
     *  创建交换机,创建队列,创建绑定关系
     *
     * @author xgl
     * @date 2020/8/16 13:32
     **/
    @Test
    public void createExchangeAndQueue() {
        // 创建交换机
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("-----创建交换机完成--------");

        // 创建队列     Queue(队列名字, 是否持久化)
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("-----创建队列完成--------");

        // 创建绑定关系
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue",
                Binding.DestinationType.QUEUE, "amqpadmin.exchange", "amqp.haha",
                null));
        System.out.println("-----绑定成功完成--------");
    }


}
