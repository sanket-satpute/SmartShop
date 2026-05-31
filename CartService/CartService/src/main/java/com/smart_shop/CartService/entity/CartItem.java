package com.smart_shop.CartService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items", uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(name = "uk_cart_product", columnNames = {"cart_id", "product_id"})
})
public class CartItem {

    @Id
    private String id;
    private String cartId;
    private String productId;
    private int quantity;
    private double unitPriceSnapshot;
    private double totalPriceSnapshot;
}
