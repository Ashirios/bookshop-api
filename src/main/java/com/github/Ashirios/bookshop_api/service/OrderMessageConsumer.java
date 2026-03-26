package com.github.Ashirios.bookshop_api.service;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.Ashirios.bookshop_api.dto.OrderMessageDto;
import com.github.Ashirios.bookshop_api.entity.Order;
import com.github.Ashirios.bookshop_api.entity.enums.Status;
import com.github.Ashirios.bookshop_api.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderMessageConsumer {

    private final OrderRepository orderRepository;

    @RabbitListener(queues = "${order.queue.name}")
    @Transactional
    public void ConsumerOrderMessage(OrderMessageDto message){
        log.info("Received order message ", message);

        try {
           
            processOrder(message);
            
           
            Order order = orderRepository.findById(message.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus(Status.PROCESSED);
            order.setProcessedAt(LocalDateTime.now());
            orderRepository.save(order);
            
            log.info("Order {} processed successfully", message.getOrderId());
        } catch (Exception e) {
            log.error("Failed to process order {}: {}", message.getOrderId(), e.getMessage());
            Order order = orderRepository.findById(message.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus(Status.FAILED);
            orderRepository.save(order);
        }
    }

    private void processOrder(OrderMessageDto message) {
        
        log.info("Processing order: orderId={}, totalPrice={}, bookCount={}", 
                message.getOrderId(), message.getTotalPrice(), message.getBookCount());
        
    
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Order processing interrupted", e);
        }
    }
}

