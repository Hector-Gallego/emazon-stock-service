package com.emazon.emazonstockservice.ports.util;

public class PortsConstants {

    private PortsConstants(){
        throw new IllegalStateException();
    }

    public static final String QUANTITY_CANNOT_BE_NULL = "Quantity cannot be null";
    public static final String QUANTITY_MUST_BE_POSITIVE_OR_ZERO = "Quantity must be zero or a positive number";
    public static final String PRICE_CANNOT_BE_NULL = "Price cannot be null";
    public static final String PRICE_MUST_BE_POSITIVE_OR_ZERO = "Price must be zero or a positive number";

    public static final String DESCRIPTION_CANNOT_BE_NULL = "Description cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";

    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";

}
