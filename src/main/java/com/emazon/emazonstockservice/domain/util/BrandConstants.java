package com.emazon.emazonstockservice.domain.util;

public final class BrandConstants {

    private BrandConstants() {
        throw new IllegalStateException();
    }

        public static final int MAX_BRAND_NAME_LENGTH = 50;

        public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;
        public static final String BRAND_NOT_FOUND = "The Brand with ID %d was not found in the system.";

}
