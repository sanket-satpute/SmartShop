package com.smartshop.ProductService.repository;

import com.smartshop.ProductService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    public List<Product> findByNameContainingIgnoreCase(String name);

    public List<Product> findByPriceBetween(double minPrice, double maxPrice);
}
