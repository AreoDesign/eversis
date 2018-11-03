package com.eversis.spaceagencydatahub.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ImageType {

    PANCHROMATIC("panchromatic"),
    MULTISPECTRAL("multispectral"),
    HYPERSPECTRAL("hyperspectral");

    @Getter
    private final String value;

}
