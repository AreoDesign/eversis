package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.ProductDTO;
import com.eversis.spaceagencydatahub.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductAssembler {

    public Product convert(ProductDTO productDTO){
        Product product = null;
        if (Objects.nonNull(productDTO)){
            product = new Product();
            product.setId(productDTO.getId());
            product.setMission(productDTO.getMission());
            product.setAquisitionDate(productDTO.getAquisitionDate());
            product.setFootprintLatitude(productDTO.getFootprintLatitude());
            product.setFootprintLongitude(productDTO.getFootprintLongitude());
            product.setFootprintAltitude(productDTO.getFootprintAltitude());
            product.setFootprintFourthCoordinate(productDTO.getFootprintTime());
            product.setPrice(productDTO.getPrice());
            product.setUrl(productDTO.getUrl());
            product.setActive(productDTO.isActive());
            product.setDeactivationDate(productDTO.getDeactivationDate());
        }

        return product;
    }

    public ProductDTO convert(Product product){
        ProductDTO productDTO = null;
        if (Objects.nonNull(product)) {
            productDTO = ProductDTO.builder()
                                   .id(product.getId())
                                   .mission(product.getMission())
                                   .aquisitionDate(product.getAquisitionDate())
                                   .footprintLatitude(product.getFootprintLatitude())
                                   .footprintLongitude(product.getFootprintLongitude())
                                   .footprintAltitude(product.getFootprintAltitude())
                                   .footprintTime(product.getFootprintFourthCoordinate())
                                   .price(product.getPrice())
                                   .url(product.getUrl())
                                   .isActive(product.isActive())
                                   .deactivationDate(product.getDeactivationDate())
                                   .build();
        }

        return productDTO;
    }
}
