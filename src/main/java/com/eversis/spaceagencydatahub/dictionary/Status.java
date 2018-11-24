package com.eversis.spaceagencydatahub.dictionary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public enum Status {

    DRAFT("draft"), // equivalent name for DRAFT
    PENDING("pending"),
    CANCELLED("cancelled"),
    COMPLETED("completed");

    @NotNull(message = "status name must be filled with non-null value.")
    @NonNull
    private String statusName;

}
