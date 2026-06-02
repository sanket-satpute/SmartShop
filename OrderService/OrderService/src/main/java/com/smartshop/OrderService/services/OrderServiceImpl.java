package com.smartshop.OrderService.services;

import com.smartshop.OrderService.api_response.dto.OrderCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderItemResponseDTO;
import com.smartshop.OrderService.api_response.dto.OrderResponseDTO;
import com.smartshop.OrderService.api_response.dto.UserResponseMailDTO;
import com.smartshop.OrderService.entity.Notification;
import com.smartshop.OrderService.entity.Order;
import com.smartshop.OrderService.enums.OrderStatus;
import com.smartshop.OrderService.exception.OrderNotFoundException;
import com.smartshop.OrderService.http_web_feign_calls.HttpUserService;
import com.smartshop.OrderService.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    KafkaTemplate<String, Notification> kafkaTemplate;

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final HttpUserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemService orderItemService, HttpUserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
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
        OrderResponseDTO response = OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .orderDate(savedOrder.getOrderDate())
                .orderStatus(savedOrder.getOrderStatus())
                .orderItemResponseDTO(orderItems)
                .totalAmount(savedOrder.getTotalAmount())
                .build();
        sendNotification(response);
        return response;
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

    public void sendNotification(OrderResponseDTO order) {
        // 1. Fetch User details
        UserResponseMailDTO user = userService.getUserMailById(order.getUserId());
        String recipientMail = user.getEmail();
        String userName = user.getName();

        // 2. Format Dates
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        String deliveryDateStr = order.getOrderDate().plusDays(5).format(dateFormatter);

        // 3. Format Subject
        String shortOrderId = order.getId().substring(0, 13);
        String subject = "Your Smart Shop Order #" + shortOrderId + " is confirmed";

        // 4. Construct Order Items HTML from the DTO
        StringBuilder itemsTable = new StringBuilder();

        // Check if items exist to avoid NullPointerException
        if (order.getOrderItemResponseDTO() != null && !order.getOrderItemResponseDTO().isEmpty()) {
            for (var item : order.getOrderItemResponseDTO()) {

                // Adjust this math based on whether your DTO stores 'unit price' or 'total item price'
                // Assuming item.getPrice() returns the subtotal for that specific item group.
                double subtotal = item.getPrice();
                double unitPrice = subtotal / item.getQuantity();

                itemsTable.append(String.format("""
                <tr>
                    <td style="padding: 8px; border-bottom: 1px solid #ddd;">
                        <b>%s</b><br>
                        <span style="font-size: 12px; color: #555;">Sold by: Smart Shop Retail</span>
                    </td>
                    <td style="padding: 8px; border-bottom: 1px solid #ddd; text-align: center;">%d</td>
                    <td style="padding: 8px; border-bottom: 1px solid #ddd; text-align: right;">₹%,.2f</td>
                    <td style="padding: 8px; border-bottom: 1px solid #ddd; text-align: right;">₹%,.2f</td>
                </tr>
                """,
                        item.getProductResponseDTO().getName(), // Replace with your actual getter for the product name
                        item.getQuantity(),    // Replace with your actual getter for quantity
                        unitPrice,
                        subtotal
                ));
            }
        } else {
            // Fallback if the order DTO arrives with an empty item list
            itemsTable.append("<tr><td colspan='4' style='padding: 8px; text-align: center; color: #888;'><em>No item details available for this order.</em></td></tr>");
        }

        // 5. Construct the HTML Message using String.format and Text Blocks
        String htmlMessage = String.format("""
        <div style="font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; border: 1px solid #eee; padding: 20px;">
            <h2 style="color: #ff9900; margin-top: 0;">Smart Shop</h2>
            <p>Dear <b>%s</b>,</p>
            <p>Thank you for your purchase. Your order has been successfully placed and is currently being processed by our fulfillment center. Below is the comprehensive summary of your transaction and the expected delivery schedule.</p>
            
            <div style="background-color: #f9f9f9; padding: 15px; border-left: 4px solid #ff9900; margin-bottom: 20px;">
                <p style="margin: 0 0 5px 0;"><b>Expected Delivery:</b> %s</p>
                <p style="margin: 0 0 5px 0;"><b>Order Status:</b> %s</p>
                <p style="margin: 0;"><b>Order ID:</b> %s</p>
            </div>

            <h3 style="border-bottom: 2px solid #eee; padding-bottom: 5px;">Order Details</h3>
            <table style="width: 100%%; border-collapse: collapse; margin-bottom: 20px; font-size: 14px;">
                <thead>
                    <tr style="background-color: #f2f2f2;">
                        <th style="padding: 8px; text-align: left; border-bottom: 2px solid #ddd;">Item</th>
                        <th style="padding: 8px; text-align: center; border-bottom: 2px solid #ddd;">Qty</th>
                        <th style="padding: 8px; text-align: right; border-bottom: 2px solid #ddd;">Unit Price</th>
                        <th style="padding: 8px; text-align: right; border-bottom: 2px solid #ddd;">Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    %s
                </tbody>
            </table>

            <h3 style="border-bottom: 2px solid #eee; padding-bottom: 5px;">Payment Summary</h3>
            <table style="width: 100%%; font-size: 14px; margin-bottom: 20px;">
                <tr>
                    <td style="padding: 4px 0;">Item(s) Subtotal:</td>
                    <td style="text-align: right; padding: 4px 0;">₹%,.2f</td>
                </tr>
                <tr>
                    <td style="padding: 4px 0;">Shipping & Handling:</td>
                    <td style="text-align: right; padding: 4px 0;">₹0.00</td>
                </tr>
                <tr>
                    <td style="padding: 4px 0;">Tax:</td>
                    <td style="text-align: right; padding: 4px 0;">₹0.00</td>
                </tr>
                <tr>
                    <td style="padding: 8px 0; border-top: 1px solid #ddd; font-weight: bold; font-size: 16px;">Grand Total:</td>
                    <td style="text-align: right; padding: 8px 0; border-top: 1px solid #ddd; font-weight: bold; font-size: 16px;">₹%,.2f</td>
                </tr>
            </table>

            <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">
            
            <h3>Next Steps</h3>
            <p>You will receive a separate notification with tracking information once your items have been dispatched.</p>
            
            <p style="font-size: 12px; color: #777; margin-top: 30px;">
                <b>Need Assistance?</b><br>
                To track your package, modify your delivery instructions, or request a cancellation, please visit the <b>Your Orders</b> dashboard on our platform.
            </p>
            <p style="font-size: 12px; color: #777;">
                Thank you for choosing Smart Shop.<br>
                <b>Smart Shop Order Administration</b><br>
                <i>This is an automatically generated email. Please do not reply to this address.</i>
            </p>
        </div>
        """,
                userName,
                deliveryDateStr,
                order.getOrderStatus().name(),
                order.getId(),
                itemsTable.toString(),
                order.getTotalAmount(),
                order.getTotalAmount()
        );

        // 6. Build and Send Notification
        Notification notification = Notification.builder()
                .userId(order.getUserId())
                .recipientEmail(recipientMail)
                .subject(subject)
                .message(htmlMessage)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send("notification-topic", notification);
        System.out.println("Notification sent: " + notification.getSubject());
    }
}
