package com.smartshop.ProductService.services;

import com.smartshop.ProductService.api_response.dto.ProductCreateDTO;
import com.smartshop.ProductService.api_response.dto.ProductOrderResponseDTO;
import com.smartshop.ProductService.api_response.dto.ProductResponseDTO;
import com.smartshop.ProductService.entity.Product;
import com.smartshop.ProductService.exceptions.ProductNotFoundException;
import com.smartshop.ProductService.exceptions.ProductOutOfStockException;
import com.smartshop.ProductService.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO) {
        String uuid = "P-" + UUID.randomUUID();
        Product product = new Product(uuid, productCreateDTO.getName(), productCreateDTO.getDescription(), productCreateDTO.getPrice(), productCreateDTO.getStock());
        Product result = repository.save(product);
        return ProductResponseDTO.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .price(result.getPrice())
                .stock(result.getStock())
                .build();
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll().stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    @Override
    public List<ProductResponseDTO> searchProductsByName(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).toList();
    }

    @Override
    public List<ProductResponseDTO> searchProductsByPriceRange(double minPrice, double maxPrice) {
        if(minPrice < 0 || maxPrice <= 0 || minPrice >= maxPrice) {
            throw new IllegalArgumentException("Invalid price range. Ensure that minPrice is non-negative, maxPrice is positive, and minPrice is less than maxPrice.");
        }
        return repository.findByPriceBetween(minPrice, maxPrice).stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build()).toList();
    }

    @Override
    public void deleteProductById(String id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductOrderResponseDTO orderProduct(String id, int howMuch) {
        ProductResponseDTO product = this.getProductById(id);
        if (product.getStock() < 1)
            throw new ProductOutOfStockException("Product with id:" + id + " is out of stock!");
        if(product.getStock()-howMuch < 0)
            throw new ProductOutOfStockException("Product is less max you can order is " + product.getStock());
        ProductResponseDTO response = this.updateStock(id, product.getStock() - howMuch);
        return ProductOrderResponseDTO.builder()
                .id(response.getId())
                .name(response.getName())
                .price(response.getPrice())
                .build();
    }

    @Override
    public ProductResponseDTO updateStock(String id, int stock) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " not found!"));
        product.setStock(stock);
        Product updatedProduct = repository.save(product);
        return ProductResponseDTO.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .stock(updatedProduct.getStock())
                .build();
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductCreateDTO productCreateDTO) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStock(productCreateDTO.getStock());
        Product result = repository.save(product);
        return ProductResponseDTO.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .price(result.getPrice())
                .stock(result.getStock())
                .build();
    }
}
