package com.eversis.spaceagencydatahub.assembler;

import com.eversis.spaceagencydatahub.dto.ProductDTO;
import com.eversis.spaceagencydatahub.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductAssembler {

    private MissionAssembler missionAssembler;

    public ProductAssembler(MissionAssembler missionAssembler) {
        this.missionAssembler = missionAssembler;
    }

    public Product convert(ProductDTO productDTO) {
        Product product = null;
        if (Objects.nonNull(productDTO)) {
            product = Product.builder()
                             .id(productDTO.getId())
                             .mission(missionAssembler.convert(productDTO.getMissionDTO()))
                             .aquisitionDate(productDTO.getAquisitionDate())
                             .footprintLatitude(productDTO.getFootprintLatitude())
                             .footprintLongitude(productDTO.getFootprintLongitude())
                             .footprintAltitude(productDTO.getFootprintAltitude())
                             .footprintFourthCoordinate(productDTO.getFootprintTime())
                             .price(productDTO.getPrice())
                             .url(productDTO.getUrl())
                             .isActive(productDTO.isActive())
                             .build();
        }

        return product;
    }

    public ProductDTO convert(Product product) {
        ProductDTO productDTO = null;
        if (Objects.nonNull(product)) {
            productDTO = ProductDTO.builder()
                                   .id(product.getId())
                                   .missionDTO(missionAssembler.convert(product.getMission()))
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
