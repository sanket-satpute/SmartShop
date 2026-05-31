package com.smartshop.OrderService.repository;

import com.smartshop.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    public List<Order> findByUserId(String userId);

    public List<Order> findByOrderStatus(String orderStatus);

    public List<Order> findByUserIdAndOrderDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);

    public List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    public List<Order> findByUserIdAndOrderStatus(String userId, String orderStatus);

    public List<Order> findByOrderStatusAndOrderDateBetween(String orderStatus, LocalDateTime startDate, LocalDateTime endDate);

}
