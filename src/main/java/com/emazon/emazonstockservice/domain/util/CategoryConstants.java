package com.emazon.emazonstockservice.domain.util;

public class CategoryConstants {


    private CategoryConstants(){
        throw new IllegalStateException("Utility class");
    }


    public enum CATEGORY_FIELDS{
        NAME,
        DESCRIPTION
    }
    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;

}
