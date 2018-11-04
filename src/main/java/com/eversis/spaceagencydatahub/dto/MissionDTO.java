package com.eversis.spaceagencydatahub.dto;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionDTO {

    private String name; //TODO: consider if remove as redundant (@PathVariable)?
    private ImageType imageType;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;
    private Instant deactivationDate;

}
