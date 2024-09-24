package com.emazon.emazonstockservice.configuration.openapi.constants;

public class OpenApiStockConstants {

    private OpenApiStockConstants() {
        throw new IllegalStateException();
    }

    public static final String OPENAPI_UPDATE_STOCK_SUMMARY = "Update Stock Quantity";
    public static final String OPENAPI_UPDATE_STOCK_DESCRIPTION = "Updates the stock quantity for a specific item in the inventory.";
    public static final String STOCK_UPDATED_SUCCESS = "Stock quantity updated successfully.";

    public static final String OPENAPI_VERIFY_STOCK_SUMMARY = "Verify Stock Availability";
    public static final String OPENAPI_VERIFY_STOCK_DESCRIPTION = "Checks if there is sufficient stock for an item and ensures it does not exceed the maximum category limit per item.";
    public static final String STOCK_VERIFICATION_COMPLETED = "Stock verification completed successfully.";

}
