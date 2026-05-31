package com.smartshop.OrderService.services;

import com.smartshop.OrderService.api_response.dto.OrderItemCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {

    public OrderItemResponseDTO createOrderItem(String orderId, OrderItemCreateDTO orderItemCreateDTO);

    public OrderItemResponseDTO getOrderItemById(String id);

    public List<OrderItemResponseDTO> getOrdersItemsByOrderId(String orderId);

    public List<OrderItemResponseDTO> getOrdersByProductId(String productId);

    public OrderItemResponseDTO updateOrderItemById(String id, OrderItemCreateDTO orderItemCreateDTO);

    public void deleteOrderItemById(String id);
}
