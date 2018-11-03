package com.eversis.spaceagencydatahub.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product")
@Data
@ToString(exclude = "mission")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_name", nullable = false)
    private Mission mission;

    @NotNull
    private Instant aquisitionDate;

    //acc. to 1st normal form principle - all data shall be atomic
    private Double footprintLatitude;
    private Double footprintLongitude;
    private Double footprintAltitude;
    private Double footprintFourthAngle;

    @NotNull
    @NonNull
    private BigDecimal price;

    @NotNull
    @NonNull
    private String url;

    //when mission is removed isActive flag is set to false (not physically removed from DB)
    private boolean isActive = true;

}
