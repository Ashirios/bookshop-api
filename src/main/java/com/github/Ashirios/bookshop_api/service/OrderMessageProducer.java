package com.github.Ashirios.bookshop_api.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.Ashirios.bookshop_api.dto.OrderMessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMessageProducer {

    private final AmqpTemplate rabbitTemplate;

    @Value("${order.exchange.name}")
    private String exchange;

    @Value("${order.routing.key}")
    private String routingKey;

    public void sendOrderMessage(OrderMessageDto message){
        log.info("Sending order meassge: {}", message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Order message sent successfully");
    }

}
