package com.emazon.emazonstockservice.domain.util;

public class BrandConstants {

    private BrandConstants() {
        throw new IllegalStateException("Utility class");
    }
        public enum BRAND_FIElDS{
            NAME,
            DESCRIPTION
        }

        public static final int MAX_BRAND_NAME_LENGTH = 50;

        public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;

}
