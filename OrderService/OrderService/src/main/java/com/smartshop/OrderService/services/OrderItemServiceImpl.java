package com.smartshop.OrderService.services;

import com.smartshop.OrderService.api_response.dto.OrderItemCreateDTO;
import com.smartshop.OrderService.api_response.dto.OrderItemResponseDTO;
import com.smartshop.OrderService.api_response.dto.ProductDTO;
import com.smartshop.OrderService.api_response.dto.ProductResponseDTO;
import com.smartshop.OrderService.entity.OrderItem;
import com.smartshop.OrderService.exception.FailedToOrderException;
import com.smartshop.OrderService.http_web_feign_calls.HttpProductService;
import com.smartshop.OrderService.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
//    private final RestTemplate restTemplate; -> older and legacy replaced by feign client
    private final HttpProductService httpProductService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, HttpProductService productService) {
        this.orderItemRepository = orderItemRepository;
        this.httpProductService = productService;
    }

    @Transactional
    @Override
    public OrderItemResponseDTO createOrderItem(String orderId, OrderItemCreateDTO orderItemCreateDTO) {
        String uuid = "OI-" + UUID.randomUUID();

        String productId = orderItemCreateDTO.getProductId();
//        calling api of product to check is the product is in the stock or not
        ProductDTO productResponse = httpProductService.orderProduct(productId, orderItemCreateDTO.getQuantity()); //restTemplate.postForObject(
//                productApiUrl,
//                null,
//                ProductDTO.class,
//                productId,
//                orderItemCreateDTO.getQuantity()
//        );

        if (productResponse == null)
            throw new FailedToOrderException("Failed to make the Order!");

        OrderItem orderItem = OrderItem.builder()
                .id(uuid)
                .orderId(orderId)
                .productId(orderItemCreateDTO.getProductId())
                .quantity(orderItemCreateDTO.getQuantity())
                .price((productResponse.getPrice() * orderItemCreateDTO.getQuantity()))
                .build();
        OrderItem savedOrder = orderItemRepository.save(orderItem);
        return OrderItemResponseDTO.builder()
                .id(savedOrder.getId())
                .productResponseDTO(ProductResponseDTO.builder().id(productResponse.getId()).name(productResponse.getName()).price(productResponse.getPrice()).build())
                .quantity(savedOrder.getQuantity())
                .price(savedOrder.getPrice())
                .build();
    }

    @Override
    public OrderItemResponseDTO getOrderItemById(String id) {
        return orderItemRepository.findById(id)
                .map(orderItem -> {
//                    ProductDTO productResponse = //restTemplate.getForObject(
//                            productApiUrl,
//                            ProductDTO.class,
//                            orderItem.getProductId()
//                    );
                    ProductDTO productResponse = httpProductService.getProductById(orderItem.getProductId());
                    if (productResponse == null)
                        throw new FailedToOrderException("Failed to get the Order!");
                    return OrderItemResponseDTO.builder()
                            .id(orderItem.getId())
                            .productResponseDTO(ProductResponseDTO.builder().id(productResponse.getId()).name(productResponse.getName()).price(productResponse.getPrice()).build())
                            .quantity(orderItem.getQuantity())
                            .price(orderItem.getPrice())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public List<OrderItemResponseDTO> getOrdersItemsByOrderId(String orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(orderItem -> {
//            String productApiUrl = PRODUCT_API_URL + "/{id}";
//            ProductDTO productResponse = restTemplate.getForObject(
//                    productApiUrl,
//                    ProductDTO.class,
//                    orderItem.getProductId()
//            );
            ProductDTO productResponse = httpProductService.getProductById(orderItem.getProductId());
            if (productResponse == null)
                throw new FailedToOrderException("Failed to get the Order!");
            return OrderItemResponseDTO.builder()
                    .id(orderItem.getId())
                    .productResponseDTO(ProductResponseDTO.builder().id(productResponse.getId()).name(productResponse.getName()).price(productResponse.getPrice()).build())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
        }).toList();
    }

    @Override
    public List<OrderItemResponseDTO> getOrdersByProductId(String productId) {
        return orderItemRepository.findByProductId(productId).stream().map(orderItem -> {
//            String productApiUrl = PRODUCT_API_URL + "/{id}";
//            ProductDTO productResponse = restTemplate.getForObject(
//                    productApiUrl,
//                    ProductDTO.class,
//                    orderItem.getProductId()
//            );
            ProductDTO productResponse = httpProductService.getProductById(orderItem.getProductId());
            if (productResponse == null)
                throw new FailedToOrderException("Failed to get the Order!");
            return OrderItemResponseDTO.builder()
                    .id(orderItem.getId())
                    .productResponseDTO(ProductResponseDTO.builder().id(productResponse.getId()).name(productResponse.getName()).price(productResponse.getPrice()).build())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
        }).toList();
    }

    @Override
    public OrderItemResponseDTO updateOrderItemById(String id, OrderItemCreateDTO orderItemCreateDTO) {
        orderItemRepository.findById(id).ifPresent(orderItem -> {
//            String productApiUrl = PRODUCT_API_URL + "/order/{id}?howMuch={howMuch}";
//            ProductDTO productResponse = restTemplate.postForObject(
//                    productApiUrl,
//                    null,
//                    ProductDTO.class,
//                    orderItemCreateDTO.getProductId(),
//                    orderItemCreateDTO.getQuantity()
//            );
            ProductDTO productResponse = httpProductService.orderProduct(orderItemCreateDTO.getProductId(), orderItemCreateDTO.getQuantity());
            if(productResponse == null)
                throw new FailedToOrderException("Failed to update the Order!");
            orderItem.setQuantity(orderItemCreateDTO.getQuantity());
            orderItem.setPrice(productResponse.getPrice() * orderItemCreateDTO.getQuantity());
            orderItemRepository.save(orderItem);
        });
        return getOrderItemById(id);
    }

    @Override
    public void deleteOrderItemById(String id) {
        orderItemRepository.deleteById(id);
    }
}
