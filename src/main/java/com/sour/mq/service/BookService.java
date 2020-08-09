package com.sour.mq.service;

import com.sour.mq.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xgl
 * @date 2020/8/9 18:34
 **/
@Service
public class BookService {


    /**
     *  监听内容
     * @RabbitListener 监听rabbitmq的内容
     *
     * @author xgl
     * @date 2020/8/9 18:39
     **/
    @RabbitListener(queues = "sout.new")
    public void receive(Book book) {
        System.out.println(new Date().toString() + " - sout.new收到消息 :");
        System.out.println(book);
    }

    @RabbitListener(queues = "sour")
    public void receive02(Message e) {

        System.out.println(new Date().toString() + " - sour收到消息 :");

        System.out.println( e.getBody() );
        System.out.println(e.getMessageProperties());

    }


}
