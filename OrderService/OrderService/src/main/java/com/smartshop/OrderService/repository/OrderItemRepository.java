package com.smartshop.OrderService.repository;

import com.smartshop.OrderService.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    public List<OrderItem> findByOrderId(String orderId);

    public List<OrderItem> findByProductId(String productId);

}
