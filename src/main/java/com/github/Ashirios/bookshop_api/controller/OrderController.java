package com.github.Ashirios.bookshop_api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Ashirios.bookshop_api.dto.CreateOrderRequest;
import com.github.Ashirios.bookshop_api.dto.OrderMessageDto;
import com.github.Ashirios.bookshop_api.entity.Order;
import com.github.Ashirios.bookshop_api.entity.enums.Status;
import com.github.Ashirios.bookshop_api.repository.OrderRepository;
import com.github.Ashirios.bookshop_api.service.OrderMessageProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMessageProducer producer;
    private final OrderRepository repository;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalPrice(request.getTotalPrice());
        order.setStatus(Status.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setBooks(new ArrayList<>()); 
        
        Order savedOrder = repository.save(order);
        
        OrderMessageDto message = new OrderMessageDto(
            savedOrder.getId(),
            savedOrder.getUserId(),
            0, 
            savedOrder.getTotalPrice(),
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        
        producer.sendOrderMessage(message);
        
        return ResponseEntity.ok("Order created successfully. Processing in background...");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderStatus(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
