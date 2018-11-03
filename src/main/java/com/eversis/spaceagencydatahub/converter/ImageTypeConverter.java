package com.eversis.spaceagencydatahub.converter;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.exception.DataInconsistencyException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

/**
 * This converter is used by Hibernate to map appropriately DB column value type String to Java Enum type
 * notice: null value allows saving Entity in case we don't know the ImageType, yet.
 */
@Slf4j
public class ImageTypeConverter implements AttributeConverter<ImageType, String> {
    @Override
    public String convertToDatabaseColumn(ImageType imageType) {
        return imageType == null ? null : imageType.getValue();
    }

    @Override
    public ImageType convertToEntityAttribute(String dbImageType) {
        ImageType type = null;
        if (dbImageType != null) {
            type = Arrays.stream(ImageType.values())
                         .filter(imageType -> dbImageType.equals(imageType.getValue()))
                         .findFirst()
                         .orElseThrow(() -> {
                             String errMsg = String.format("'%s' is not known image type, not possible to convert.", dbImageType);
                             log.error(errMsg);
                             return new DataInconsistencyException(errMsg);
                         });
        }
        return type;
    }
}
