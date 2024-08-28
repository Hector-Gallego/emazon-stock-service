package com.emazon.emazonstockservice.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaginationValidatorTest {

    @Test
    void testValidatePageNo() {
        assertEquals(0, PaginationValidator.validatePageNo(null));
        assertEquals(0, PaginationValidator.validatePageNo(-1));
        assertEquals(1, PaginationValidator.validatePageNo(1));
    }

    @Test
    void testValidatePageSize() {
        assertEquals(10, PaginationValidator.validatePageSize(null));
        assertEquals(10, PaginationValidator.validatePageSize(-1));
        assertEquals(1, PaginationValidator.validatePageSize(1));
    }

    @Test
    void testValidateSortDirection() {
        assertEquals("asc", PaginationValidator.validateSortDirection(null));
        assertEquals("asc", PaginationValidator.validateSortDirection("invalid"));
        assertEquals("asc", PaginationValidator.validateSortDirection("ASC"));
        assertEquals("desc", PaginationValidator.validateSortDirection("DESC"));
    }

    @Test
    void testValidateSortBy() {
        assertEquals("name", PaginationValidator.validateSortBy(null));
        assertEquals("name", PaginationValidator.validateSortBy(""));
        assertEquals("price", PaginationValidator.validateSortBy("price"));
    }

}