package com.smartshop.OrderService.controller;

import com.smartshop.OrderService.api_response.dto.OrderCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderResponseDTO;
import com.smartshop.OrderService.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiResponse(responseCode = "201", description = "Order created successfully")
    @Tag(name = "Order Item", description = "APIs for making new order")
    @Operation(summary = "Create Order", description = "API to create a new order")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderCreateDTO));
    }

    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @Tag(name = "Get All Orders", description = "API to get all orders")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @ApiResponse(responseCode = "200", description = "Order retrieved successfully")
    @Tag(name = "Get Order By Id", description = "API to get order by id")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(userId));
    }

    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @Tag(name = "Update Order Status", description = "API to update order status")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @Tag(name = "Delete Order", description = "API to delete an order")
    @ApiResponse(responseCode = "204", description = "Order deleted successfully")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
