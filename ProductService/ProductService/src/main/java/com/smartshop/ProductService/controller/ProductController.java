package com.smartshop.ProductService.controller;

import com.smartshop.ProductService.api_response.dto.ProductCreateDTO;
import com.smartshop.ProductService.api_response.dto.ProductResponseDTO;
import com.smartshop.ProductService.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/products")
@Tag(name = "Product Management")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(productCreateDTO));
    }

    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @Operation(summary = "Get all products", description = "Retrieves a list of all products available in the system.")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @ApiResponse(responseCode = "200", description = "Product found and returned successfully")
    @Operation(summary = "Get product by ID", description = "Retrieves the details of a specific product by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @ApiResponse(responseCode = "200", description = "Products found matching the search criteria")
    @Operation(summary = "Search products by name", description = "Searches for products that match the provided name or partial name.")
    @GetMapping("/search/name")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchProductsByName(name));
    }

    @ApiResponse(responseCode = "200", description = "Products found within the specified price range")
    @Operation(summary = "Search products by price range", description = "Searches for products that fall within the specified minimum and maximum price range.")
    @GetMapping("/search/price")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByPriceRange(@PositiveOrZero @RequestParam double minPrice, @Positive @RequestParam double maxPrice) {
        return ResponseEntity.ok(service.searchProductsByPriceRange(minPrice, maxPrice));
    }

    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @Operation(summary = "Update product details", description = "Updates the details of an existing product identified by its unique identifier.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable String id, @Valid @RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseEntity.ok(service.updateProduct(id, productCreateDTO));
    }

    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @Operation(summary = "Delete a product", description = "Deletes a product from the system based on its unique identifier.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "201", description = "Product Ordered Successfully")
    @Operation(summary = "Order a Product", description = "Order a product and reduce the stock.")
    @PostMapping("/order/{id}")
    public ResponseEntity<?> orderProduct(@PathVariable String id, @RequestParam int howMuch) {
        return ResponseEntity.ok(service.orderProduct(id, howMuch));
    }

    @ApiResponse(responseCode = "201", description = "Product Ordered Successfully")
    @Operation(summary = "Update Product stock", description = "Update the product stock.")
    @PostMapping("/updateStock/{id}")
    public ResponseEntity<?> updateProductStock(@PathVariable String id, @RequestParam int howMuch) {
        return ResponseEntity.ok(service.updateStock(id, howMuch));
    }


}
