package com.eversis.spaceagencydatahub.dto;

import com.eversis.spaceagencydatahub.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;    //TODO: consider if remove as redundant (@PathVariable)?
    private Mission mission;
    private Instant aquisitionDate;
    private Double footprintLatitude;
    private Double footprintLongitude;
    private Double footprintAltitude;
    private Double footprintTime;
    private BigDecimal price;
    private String url;
    private boolean isActive;
    private Instant deactivationDate;

}
