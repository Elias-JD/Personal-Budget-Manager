package com.elias.budgetmanager.dto;

import com.elias.budgetmanager.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long id,
                                 String description,
                                 BigDecimal amount,
                                 TransactionType type,
                                 LocalDateTime createdAt,
                                  Long userId) {
}
