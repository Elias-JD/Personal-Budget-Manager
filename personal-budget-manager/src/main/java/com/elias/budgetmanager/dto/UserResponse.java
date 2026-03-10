package com.elias.budgetmanager.dto;

public record UserResponse(Long id,
                           String username,
                           String email,
                           boolean isEnabled
) {
}
