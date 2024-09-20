package com.emazon.emazonstockservice.datatest;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.constants.CategoryConstants;

public class CategoryDataTestFactory {

    public static Category createValidCategory() {
        return new Category(1L,
                "Tecnología",
                "Gadgets y dispositivos electrónicos como teléfonos y computadoras.");

    }


    public static Category createCategoryWhitNameExceedsMaxLength(){
        return new Category(
                1L,
                "a".repeat(CategoryConstants.MAX_CATEGORY_NAME_LENGTH + 1),
                "Ropa, calzado y equipo para actividades físicas.");

    }

    public static Category createCategoryWhitDescriptionExceedsMaxLength(){
        return  new Category(
                1L,
                "Muebles",
                "a".repeat(CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1));

    }

    public static Category createCategoryWhitNameEmpty(){
        return new Category(
                1L,
                "",
                "Ropa, calzado y equipo para actividades físicas");

    }

    public static Category createCategoryWhitDescriptionEmpty(){
        return new Category(
                1L,
                "AstraZeneca",
                "");
    }
}
