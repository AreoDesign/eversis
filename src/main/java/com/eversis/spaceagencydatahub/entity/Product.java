package com.eversis.spaceagencydatahub.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)//todo: switch back to lazy
    @JoinColumn(name = "mission_name", nullable = false)
    private Mission mission;

    @NotNull
    @Builder.Default
    private Instant aquisitionDate = Instant.now();

    //acc. to 1st normal form principle - all data shall be atomic
    private Double footprintLatitude;
    private Double footprintLongitude;
    private Double footprintAltitude;
    private Double footprintFourthCoordinate;

    @NonNull
    @NotNull(message = "Product price cannot be null.")
    @Min(value = 0, message = "Price cannot be negative value.")
    @Max(value = 200, message = "Maximum price for the product is 200.")
    @Column(precision = 5, scale = 2)
    private BigDecimal price;

    @NonNull
    @NotNull(message = "URL address of a product cannot be null")
    @URL(message = "Given URL is not valid.")
    private String url;

    /**
     * when mission is removed isActive flag is set to false (not physically removed from DB)
     */
    @Builder.Default
    private boolean isActive = true;

    /**
     * when mission is removed isActive flag is set to false and deactivation time is set.
     * Null == still active.
     */
    @Setter(AccessLevel.NONE)
    @Builder.Default
    private Instant deactivationDate = null;

    //binding field isActive with deactivationDate
    public void setActive(boolean active) {
        isActive = active;
        deactivationDate = active ? null : Instant.now();
    }
}
