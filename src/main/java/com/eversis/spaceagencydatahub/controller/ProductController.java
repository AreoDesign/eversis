package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.dto.ProductDTO;
import com.eversis.spaceagencydatahub.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getProduct(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productService.add(productDTO);
    }

    @DeleteMapping("/products/{productId}")
    public ProductDTO removeProduct(@PathVariable Long productId) {
        return productService.remove(productId);
    }

}
