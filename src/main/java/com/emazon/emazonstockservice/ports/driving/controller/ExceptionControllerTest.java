package com.emazon.emazonstockservice.ports.driving.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLDataException;

@RestController
@RequestMapping("/api/exceptions/test")
public class ExceptionControllerTest {


    @GetMapping("/sql-exception")
    public ResponseEntity<String> triggerException() throws SQLDataException {
        throw new SQLDataException("Field limit exceeded error!");
    }
}
