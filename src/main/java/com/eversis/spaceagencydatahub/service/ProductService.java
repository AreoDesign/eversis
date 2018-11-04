package com.eversis.spaceagencydatahub.service;

import com.eversis.spaceagencydatahub.assembler.ProductAssembler;
import com.eversis.spaceagencydatahub.dto.ProductDTO;
import com.eversis.spaceagencydatahub.entity.Product;
import com.eversis.spaceagencydatahub.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.Instant;

/**
 * This service adds and removes missions - implementation for Process Content Administrator PRODUCTS management
 */
@Service
@Slf4j
public class ProductService {

    private ProductRepository productRepository;

    private ProductAssembler productAssembler;

    public ProductService(ProductRepository productRepository, ProductAssembler productAssembler) {
        this.productRepository = productRepository;
        this.productAssembler = productAssembler;
    }

    public ProductDTO add(ProductDTO productDTO) {
        Validate.notNull(productDTO, "New product cannot be null!");
        if (productRepository.findById(productDTO.getId()).isPresent()) {
            String errMsg = String.format("Product id: '%d', exists already.", productDTO.getId());
            log.error(errMsg);
            throw new EntityExistsException(errMsg);
        }
        Product productEntity = productAssembler.convert(productDTO);
        Product product = productRepository.save(productEntity);

        return productAssembler.convert(product);
    }

    public ProductDTO remove(Long productId) {
        Validate.notNull(productId, "Existing product id cannot be null!");
        Validate.validState(productId > 0, String.format("Product id must be positive, while given id: '%d'", productId));
        Product productEntity = productRepository.findById(productId)
                                                 .orElseThrow(() -> {
                                                     String errMsg = String.format("Product with id: %d not found.", productId);
                                                     log.error(errMsg);
                                                     return new EntityNotFoundException(errMsg);
                                                 });
        productEntity.setActive(false);
        productEntity.setDeactivationDate(Instant.now());
        Product product = productRepository.save(productEntity);

        return productAssembler.convert(product);
    }

}