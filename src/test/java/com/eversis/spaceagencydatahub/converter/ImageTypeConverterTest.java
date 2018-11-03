package com.eversis.spaceagencydatahub.converter;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.exception.DataInconsistencyException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageTypeConverterTest {

    @Test
    public void convertToDbTest() {
        ImageTypeConverter itc = new ImageTypeConverter();

        Assert.assertEquals("panchromatic", itc.convertToDatabaseColumn(ImageType.PANCHROMATIC));
        Assert.assertEquals("multispectral", itc.convertToDatabaseColumn(ImageType.MULTISPECTRAL));
        Assert.assertEquals("hyperspectral", itc.convertToDatabaseColumn(ImageType.HYPERSPECTRAL));
        Assert.assertEquals(null, itc.convertToDatabaseColumn(null));
    }

    @Test
    public void convertToEntityTest() {
        ImageTypeConverter itc = new ImageTypeConverter();

        Assert.assertEquals(ImageType.HYPERSPECTRAL, itc.convertToEntityAttribute("hyperspectral"));
        Assert.assertEquals(ImageType.MULTISPECTRAL, itc.convertToEntityAttribute("multispectral"));
        Assert.assertEquals(ImageType.PANCHROMATIC, itc.convertToEntityAttribute("panchromatic"));
        Assert.assertEquals(null, itc.convertToEntityAttribute(null));
    }


    @Test
    public void exceptionTest() {
        ImageTypeConverter itc = new ImageTypeConverter();

        int errCnt = 0;

        try {
            itc.convertToEntityAttribute("ultramatic");
        } catch (DataInconsistencyException ex) {
            ++errCnt;
            assertTrue(ex.getMessage().contains("'ultramatic' is not known image type, not possible to convert."));
        }

        assertEquals(1, errCnt);
    }

}