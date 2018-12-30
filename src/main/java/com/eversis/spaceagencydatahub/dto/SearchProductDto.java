package com.eversis.spaceagencydatahub.dto;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProductDto {

    private String missionName;
    private ImageType imageType;
    private Instant aquisitionDateFrom;
    private Instant aquisitionDateTo;

}
