package com.github.Ashirios.bookshop_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.Ashirios.bookshop_api.entity.Order;
import com.github.Ashirios.bookshop_api.entity.enums.Status;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(Status status);
    
}
