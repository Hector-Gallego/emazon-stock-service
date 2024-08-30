package com.emazon.emazonstockservice.ports.driving.mapper;

public interface GenericMapper <T, D>{
    D toDto(T entity);
}
