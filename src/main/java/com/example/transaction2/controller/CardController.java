package com.example.transaction2.controller;

import com.example.transaction2.aop.CurrentUser;
import com.example.transaction2.entity.User;
import com.example.transaction2.payload.CardAddDTO;
import com.example.transaction2.payload.CardDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;


    @PostMapping("/add")
    public ApiResult<CardDTO> addCard(@Valid @RequestBody CardAddDTO cardDTO, @CurrentUser User user) {
        return cardService.add(cardDTO,user);
    }
    @GetMapping("/my-cards")
    ApiResult<List<CardDTO>> getAll(User user){
        return cardService.getAll(user);
    };

    @GetMapping("/card-info/{cardNumber}")
    ApiResult<CardDTO> get(@PathVariable String cardNumber, User user){
        return cardService.get(cardNumber,user);
    };

}
