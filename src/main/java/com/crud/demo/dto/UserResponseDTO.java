package com.crud.demo.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime dateCreated
) {}
