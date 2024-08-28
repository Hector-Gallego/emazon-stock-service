package com.emazon.emazonstockservice.domain.util;

import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;

public class FieldValidator {

    private FieldValidator(){
        throw new IllegalStateException();
    }

    public static void validateName(String name, int maxNameLength) {
        if (name == null) {
            throw new FieldEmptyException(DomainsConstants.NAME_CANNOT_BE_NULL);
        }

        if (name.isBlank()) {
            throw new FieldEmptyException(DomainsConstants.NAME_CANNOT_BE_EMPTY);
        }

        if (name.length() > maxNameLength) {
            throw new FieldLimitExceededException(DomainsConstants.MAX_NAME_LENGTH);
        }
    }

    public static void validateDescription(String description, int maxDescriptionLength) {
        if (description == null) {
            throw new FieldEmptyException(DomainsConstants.DESCRIPTION_CANNOT_BE_NULL);
        }

        if (description.isBlank()) {
            throw new FieldEmptyException(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY);
        }

        if (description.length() > maxDescriptionLength) {
            throw new FieldLimitExceededException(DomainsConstants.MAX_DESCRIPTION_LENGTH);
        }
    }

    public static void validateNameAndDescription(String name, int maxNameLength, String description, int maxDescriptionLength) {
        validateName(name, maxNameLength);
        validateDescription(description, maxDescriptionLength);
    }
}
