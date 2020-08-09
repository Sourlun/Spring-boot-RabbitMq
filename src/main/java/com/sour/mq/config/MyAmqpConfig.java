package com.sour.mq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SourLun
 */
@Configuration
public class MyAmqpConfig {

    /**
     *  设置发送的时候的格式(以json的格式发送mq)
     *
     * @author xgl
     * @date 2020/8/9 17:56
     **/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
