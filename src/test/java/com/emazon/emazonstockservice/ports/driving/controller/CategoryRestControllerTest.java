package com.emazon.emazonstockservice.ports.driving.controller;

import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driving.dto.request.CategoryRequestDto;
import com.emazon.emazonstockservice.ports.driving.mapper.CategoryResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @MockBean
    private  ICategoryServicePort categoryServicePort;
    @MockBean
    private CategoryResponseMapper categoryMapper;

    private  CategoryRequestDto categoryRequestDto;
    private Category category;




    @BeforeEach
    void setUp() {

        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Electronics");
        categoryRequestDto.setDescription("Category for electronic items");

        category = new Category();
        category.setName("Electronics");
        category.setDescription("Category for electronic items");
    }

    @Test
    void saveCategory_shouldReturnCreatedStatus() throws Exception {
        // Mock del mapper y del servicio
        Mockito.when(categoryMapper.toDto(any(CategoryRequestDto.class))).thenReturn(category);
        Mockito.doNothing().when(categoryServicePort).saveCategory(any(Category.class));

        mockMvc.perform(post("/api/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void saveCategory_shouldReturnBadRequestStatus_whenInvalidInput() throws Exception {

        CategoryRequestDto invalidRequest = new CategoryRequestDto();

        mockMvc.perform(post("/api/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}