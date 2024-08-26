package com.emazon.emazonstockservice.domain.util;

public final class DomainsConstants {

    private DomainsConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static String getDuplicateNameFieldMessage(String name) {
        return String.format("A category with the name '%s' already exists.", name);
    }
    public static final String FAIL_SAVE_CATEGORY_MESSAGE = "Filed to save category";

}
