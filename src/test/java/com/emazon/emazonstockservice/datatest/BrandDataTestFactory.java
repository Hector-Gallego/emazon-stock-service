package com.emazon.emazonstockservice.datatest;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;

public class BrandDataTestFactory {

    public static Brand createValidBrand(){
        return new Brand(1L,
                "AstraZeneca",
                "Famosa por sus medicamentos y tratamientos innovadores.");

    }

    public static Brand createBrandWhitNameExceedsMaxLength(){
        return new Brand(
                1L,
                "a".repeat(BrandConstants.MAX_BRAND_NAME_LENGTH + 1),
                "Reconocida por sus deportivos y autos de alto rendimiento.");

    }

    public static Brand createBrandWhitDescriptionExceedsMaxLength(){
        return new Brand(
                1L,
                "Land Rover",
                "a".repeat(BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH + 1));

    }

    public static Brand createBrandWhitNameEmpty(){
        return new Brand(
                1L,
                "",
                "Especialista en vehículos todoterreno y SUVs de lujo.");


    }

    public static Brand createBrandWhitDescriptionEmpty(){
        return new Brand(
                1L,
                "AstraZeneca",
                "");
    }






}
