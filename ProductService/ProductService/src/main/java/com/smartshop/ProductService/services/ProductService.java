package com.smartshop.ProductService.services;

import com.smartshop.ProductService.api_response.dto.ProductCreateDTO;
import com.smartshop.ProductService.api_response.dto.ProductOrderResponseDTO;
import com.smartshop.ProductService.api_response.dto.ProductResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO);

    public List<ProductResponseDTO> getAllProducts();

    public ProductResponseDTO getProductById(String id);

    public List<ProductResponseDTO> searchProductsByName(String name);

    public List<ProductResponseDTO> searchProductsByPriceRange(double minPrice, double maxPrice);

    public void deleteProductById(String id);

    public ProductOrderResponseDTO orderProduct(String id, int howMuch);

    public ProductResponseDTO updateStock(String id, int stock);

    public ProductResponseDTO updateProduct(String id, ProductCreateDTO productCreateDTO);
}
