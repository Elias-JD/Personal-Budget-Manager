package com.elias.budgetmanager.service;

import com.elias.budgetmanager.dto.TransactionRequest;
import com.elias.budgetmanager.dto.TransactionResponse;
import com.elias.budgetmanager.exception.ResourceNotFoundException;
import com.elias.budgetmanager.model.CustomUserDetails;
import com.elias.budgetmanager.model.Transaction;
import com.elias.budgetmanager.model.User;
import com.elias.budgetmanager.repository.TransactionRepository;
import com.elias.budgetmanager.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, UserRepository userRepository1) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<TransactionResponse> findUserTransactions() {

        return transactionRepository
                .findByUserId(getCurrentUser().getId())
                .stream()
                .map(this::mapToResponse)
                .toList();

    }


    public TransactionResponse findUserTransaction(Long id) {

        Transaction transaction = getTransactionForCurrentUser(id);

        return mapToResponse(transaction);
    }


    public TransactionResponse createUserTransaction(TransactionRequest request) {

        User user = userRepository.getReferenceById(getCurrentUser().getId());

        Transaction newTransaction = new Transaction();

        newTransaction.setDescription(request.description());
        newTransaction.setAmount(request.amount());
        newTransaction.setType(request.type());
        newTransaction.setUser(user);

        transactionRepository.save(newTransaction);


        return mapToResponse(newTransaction);
    }


    public TransactionResponse updateUserTransaction(Long id, TransactionRequest request) {

        Transaction transactionDb = getTransactionForCurrentUser(id);

        transactionDb.setDescription(request.description());
        transactionDb.setAmount(request.amount());
        transactionDb.setType(request.type());

        transactionRepository.save(transactionDb);

        return mapToResponse(transactionDb);
    }

    public void deleteTransaction(Long id) {

        Transaction transactionDb = getTransactionForCurrentUser(id);

        transactionRepository.delete(transactionDb);
    }



    private CustomUserDetails getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails user)) {
            throw new AccessDeniedException("There is no valid active session");
        }

        return user;
    }


    private Transaction getTransactionForCurrentUser(Long id) {
        Long userId = getCurrentUser().getId();

        return transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The transaction with ID " + id + " for user " + userId + " could not be found."));
    }


    private TransactionResponse mapToResponse(Transaction transaction) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCreatedAt(),
                transaction.getUser().getId());
    }
}

