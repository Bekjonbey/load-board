package com.example.transaction2.controller;
import com.example.transaction2.aop.CurrentUser;
import com.example.transaction2.entity.User;
import com.example.transaction2.payload.TransactionDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/add/{id}")
    public ApiResult<UUID> addTransaction(@PathVariable Long id, @CurrentUser User user) {
        return transactionService.add(id,user);
    }
    @PostMapping("/confirm/{id}")
    public ApiResult<TransactionDTO> confirm(@PathVariable UUID id, User user) {
        return transactionService.confirm(id);
    }
}
