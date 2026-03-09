package com.elias.budgetmanager.dto;

import com.elias.budgetmanager.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(
                                @NotBlank
                                String description,

                                @NotNull
                                @DecimalMin(value = "0.01", message = "The amount must be greater than 0")
                                BigDecimal amount,

                                @NotNull
                                TransactionType type
) {
}
