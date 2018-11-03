package com.eversis.spaceagencydatahub.entity;

import com.eversis.spaceagencydatahub.converter.ImageTypeConverter;
import com.eversis.spaceagencydatahub.dictionary.ImageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mission")
@Data
@Builder(builderMethodName = "hiddenBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Mission implements Serializable {

    @Id
    @Setter(AccessLevel.PUBLIC)
    @NotNull
    @NonNull
    private String name;

    @Convert(converter = ImageTypeConverter.class)
    private ImageType imageType;

    private Instant startDate;

    private Instant endDate;

    //when mission is removed isActive flag is set to false (not physically removed from DB)
    @Builder.Default
    private boolean isActive = true;

    public static MissionBuilder builder(@NotNull String name) {
        return hiddenBuilder().name(name);
    }

}
