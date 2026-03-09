package com.elias.budgetmanager.controller;

import com.elias.budgetmanager.dto.TransactionRequest;
import com.elias.budgetmanager.dto.TransactionResponse;
import com.elias.budgetmanager.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(this.transactionService.findUserTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(this.transactionService.findUserTransaction(id));
    }

    @PostMapping()
    public ResponseEntity<TransactionResponse> saveTransaction(@Valid @RequestBody TransactionRequest request) {
        return new ResponseEntity<>(
                this.transactionService.createUserTransaction(request),
                HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransactionById(@Valid @PathVariable Long id, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(this.transactionService.updateUserTransaction(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {

        this.transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }


}
