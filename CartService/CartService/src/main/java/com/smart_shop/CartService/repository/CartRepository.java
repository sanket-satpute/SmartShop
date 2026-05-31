package com.smart_shop.CartService.repository;

import com.smart_shop.CartService.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    public List<Cart> findByUserId(String userId);

    public void deleteByUserId(String userId);
}
