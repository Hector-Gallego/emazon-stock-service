package com.emazon.emazonstockservice.ports.driven.mysql.constants;

public class ValueConstants {

    private ValueConstants(){
        throw new IllegalArgumentException();
    }

    public static final int INITIAL_COUNT = 0;
    public static final int INCREMENT_ONE = 1;
    public static final int MAX_CATEGORY_LIMIT_PER_ARTICLE = 3;
    public static final int MIN_DAYS_FOR_RESTOCKING_SUPPLY = 5;
}
