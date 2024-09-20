package com.emazon.emazonstockservice.ports.driving.rest.mapper;

public interface GenericMapper <T, D>{
    D toDto(T entity);
}
