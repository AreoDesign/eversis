package com.eversis.spaceagencydatahub.controller;

import com.eversis.spaceagencydatahub.dictionary.Role;
import com.eversis.spaceagencydatahub.dto.ProductDTO;
import com.eversis.spaceagencydatahub.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isManager = auth.getAuthorities().contains(Role.CONTENT_MANAGER);

        return isManager ? productService.getAllProducts() : productService.getAllProducts(auth);
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
