package com.crud.demo.dto;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password
) {}
