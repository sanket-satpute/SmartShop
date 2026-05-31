package com.smartshop.OrderService.services;

import com.smartshop.OrderService.api_response.dto.OrderCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderItemResponseDTO;
import com.smartshop.OrderService.api_response.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    public OrderResponseDTO placeOrder(OrderCreateDTO orderCreateDTO);

    public OrderResponseDTO getOrderById(String orderId);

    public List<OrderResponseDTO> getAllOrders();

    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId);

    public List<OrderResponseDTO> getOrdersByStatus(String status);

    public List<OrderResponseDTO> getOrdersByDateRange(String startDate, String endDate);

    public List<OrderResponseDTO> getOrdersByCustomerIdAndStatus(String customerId, String status);

    public List<OrderResponseDTO> getOrdersByCustomerIdAndDateRange(String customerId, String startDate, String endDate);

    public List<OrderResponseDTO> getOrdersByStatusAndDateRange(String status, String startDate, String endDate);

    public OrderResponseDTO updateOrder(String orderId, OrderCreateDTO orderCreateDTO);

    public OrderResponseDTO updateOrderStatus(String orderId, String status);

    public void deleteOrder(String orderId);
}
