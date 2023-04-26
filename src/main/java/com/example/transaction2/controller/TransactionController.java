package com.example.transaction2.controller;
import com.example.transaction2.entity.User;
import com.example.transaction2.payload.TransactionDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/add")
    public ApiResult<TransactionDTO> addTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.add(transactionDTO);
    }
    @PostMapping("/confirm/{id}")
    public ApiResult<TransactionDTO> confirm(@PathVariable UUID id, User user) {
        return transactionService.confirm(id,user);
    }
}
