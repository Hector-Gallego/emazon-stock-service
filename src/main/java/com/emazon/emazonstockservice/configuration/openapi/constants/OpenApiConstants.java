package com.emazon.emazonstockservice.configuration.openapi.constants;

public final class OpenApiConstants {



    private OpenApiConstants(){
        throw new IllegalStateException();
    }

    public static final String OPENAPI_TITTLE = "Emazon stock service API";
    public static final String OPENAPI_VERSION = "1.0";
    public static final String OPENAPI_DESCRIPTION = "API for the stock microservice of the eAmazon e-commerce platform";

    public static final String OPENAPI_CODE_201 = "201";
    public static final String OPENAPI_CODE_400 = "400";
    public static final String OPENAPI_CODE_500 = "500";
    public static final String OPENAPI_CODE_200 = "200";
    public static final String OPENAPI_MEDIA_TYPE_JSON = "application/json";
    public static final  String SORT_BY_NAME = "name";
    public static final String OPENAPI_INTERNAL_SERVER_ERROR = "Internal server error";

    public static final String INVALID_INPUT = "Invalid input";

    public static final String OPEN_API_ASC_ORDER = "asc";
    public static final String OPEN_API_DESC_ORDER = "desc";








}
