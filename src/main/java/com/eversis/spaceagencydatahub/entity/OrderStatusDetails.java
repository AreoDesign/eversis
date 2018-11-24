package com.eversis.spaceagencydatahub.entity;

import com.eversis.spaceagencydatahub.dictionary.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "order_status_details")
@Data
@Builder
public class OrderStatusDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "order status must be filled with non-null value.")
    @NonNull
    @Builder.Default
    private Status status = Status.DRAFT;

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @NotNull
    @NonNull
    private Instant modificationDate = Instant.now();

    public void setStatus(Status status) {
        this.status = status;
        this.modificationDate = Instant.now();
    }

}
