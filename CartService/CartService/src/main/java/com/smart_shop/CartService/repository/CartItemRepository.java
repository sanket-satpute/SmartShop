package com.smart_shop.CartService.repository;

import com.smart_shop.CartService.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
        void deleteByCartIdIn(List<String> cartId);

        List<CartItem> findByCartId(String cartId);
}
