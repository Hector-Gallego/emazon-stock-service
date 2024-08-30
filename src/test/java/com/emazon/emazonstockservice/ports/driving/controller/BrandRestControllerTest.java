package com.emazon.emazonstockservice.ports.driving.controller;

import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driving.dto.request.BrandRequestDto;
import com.emazon.emazonstockservice.ports.driving.mapper.BrandRequestMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.BrandResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandRestController.class)
class BrandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBrandServicePort brandServicePort;
    @MockBean
    private BrandResponseMapper brandResponseMapper;

    @MockBean
    private BrandRequestMapper brandRequestMapper;

    private BrandRequestDto brandRequestDto;
    private Brand brand;



    @BeforeEach
    void setUp() {

        brandRequestDto = new BrandRequestDto();
        brandRequestDto.setName("Brand_name");
        brandRequestDto.setDescription("brand_description");

        brand = new Brand();
        brand.setName("Brand_name");
        brand.setDescription("brand_description");
    }

    @Test
    void saveBrand_shouldReturnCreatedStatus() throws Exception {
        // Mock del mapper y del servicio
        when(brandRequestMapper.toDomain(any(BrandRequestDto.class))).thenReturn(brand);
        Mockito.doNothing().when(brandServicePort).saveBrand(any(Brand.class));

        mockMvc.perform(post("/api/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void saveBrand_shouldReturnBadRequestStatus_whenInvalidInput() throws Exception {

        BrandRequestDto invalidRequest = new BrandRequestDto();

        mockMvc.perform(post("/api/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testListBrandsWithoutParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/brand/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Required parameter 'pageNo' is not present."));
    }





}