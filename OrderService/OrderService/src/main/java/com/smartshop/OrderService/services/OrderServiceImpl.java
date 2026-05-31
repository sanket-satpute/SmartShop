package com.smartshop.OrderService.services;

import com.smartshop.OrderService.api_response.dto.OrderCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderItemResponseDTO;
import com.smartshop.OrderService.api_response.dto.OrderResponseDTO;
import com.smartshop.OrderService.entity.Order;
import com.smartshop.OrderService.enums.OrderStatus;
import com.smartshop.OrderService.exception.OrderNotFoundException;
import com.smartshop.OrderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }

    @Transactional
    @Override
    public OrderResponseDTO placeOrder(OrderCreateDTO orderCreateDTO) {
        String uuid = UUID.randomUUID().toString();
        List<OrderItemResponseDTO> orderItems = orderCreateDTO.getOrderItem().stream()
                .map(orderItem -> orderItemService.createOrderItem(uuid, orderItem))
                .toList();

        Order order = Order.builder()
                .id(uuid)
                .userId(orderCreateDTO.getUserId())
                .orderStatus(OrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .totalAmount(orderItems.stream().map(OrderItemResponseDTO::getPrice).reduce(0.0, Double::sum))
                .build();
        Order savedOrder = orderRepository.save(order);
        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .orderDate(savedOrder.getOrderDate())
                .orderStatus(savedOrder.getOrderStatus())
                .orderItemResponseDTO(orderItems)
                .totalAmount(savedOrder.getTotalAmount())
                .build();
    }

    @Override
    public OrderResponseDTO getOrderById(String orderId) {
        List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .orderItemResponseDTO(orderItems)
                .totalAmount(order.getTotalAmount())
                .build();
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
                List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
                return OrderResponseDTO.builder()
                        .id(order.getId())
                        .userId(order.getUserId())
                        .orderDate(order.getOrderDate())
                        .orderStatus(order.getOrderStatus())
                        .orderItemResponseDTO(orderItems)
                        .totalAmount(order.getTotalAmount())
                        .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId) {
        List<Order> orders = orderRepository.findByUserId(customerId);
        return orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .orderItemResponseDTO(orderItems)
                    .totalAmount(order.getTotalAmount())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        List<Order> orders = orderRepository.findByOrderStatus(status);
        return orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .orderItemResponseDTO(orderItems)
                    .totalAmount(order.getTotalAmount())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByDateRange(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<Order> orders = orderRepository.findByOrderDateBetween(start, end);
        return orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .orderItemResponseDTO(orderItems)
                    .totalAmount(order.getTotalAmount())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerIdAndStatus(String customerId, String status) {
        List<Order> orders = orderRepository.findByUserIdAndOrderStatus(customerId, status);
        return orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .orderItemResponseDTO(orderItems)
                    .totalAmount(order.getTotalAmount())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerIdAndDateRange(String customerId, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<Order> orders = orderRepository.findByUserIdAndOrderDateBetween(customerId, start, end);
        return orders.stream().map(order -> {
            List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(order.getId());
            return OrderResponseDTO.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .orderItemResponseDTO(orderItems)
                    .totalAmount(order.getTotalAmount())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByStatusAndDateRange(String status, String startDate, String endDate) {
        return List.of();
    }

    @Override
    public OrderResponseDTO updateOrder(String orderId, OrderCreateDTO orderCreateDTO) {
        OrderResponseDTO orderResponse = getOrderById(orderId);
        Order order = new Order();
        order.setId(orderResponse.getId());
        order.setUserId(orderCreateDTO.getUserId());
        order.setOrderDate(orderResponse.getOrderDate());
        order.setOrderStatus(orderResponse.getOrderStatus());

        Order updatedOrder = orderRepository.save(order);
        List<OrderItemResponseDTO> orderItems = orderCreateDTO.getOrderItem().stream()
                .map(orderItem -> orderItemService.updateOrderItemById(orderId, orderItem))
                .toList();
        return OrderResponseDTO.builder()
                .id(updatedOrder.getId())
                .userId(updatedOrder.getUserId())
                .orderItemResponseDTO(orderItems)
                .orderStatus(updatedOrder.getOrderStatus())
                .totalAmount(orderResponse.getTotalAmount()).build();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(String orderId, String status) {
         Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
         order.setOrderStatus(OrderStatus.valueOf(status));
         Order updatedOrder = orderRepository.save(order);
         List<OrderItemResponseDTO> orderItems = orderItemService.getOrdersItemsByOrderId(orderId);
         return OrderResponseDTO.builder()
                 .id(updatedOrder.getId())
                 .userId(updatedOrder.getUserId())
                 .orderDate(updatedOrder.getOrderDate())
                 .orderStatus(updatedOrder.getOrderStatus())
                 .orderItemResponseDTO(orderItems)
                 .totalAmount(updatedOrder.getTotalAmount())
                 .build();
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
