package com.example.transaction2.service;

import com.example.transaction2.entity.Card;
import com.example.transaction2.entity.User;
import com.example.transaction2.entity.enums.CardTypeEnum;
import com.example.transaction2.entity.enums.CurrencyEnum;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.CardAddDTO;
import com.example.transaction2.payload.CardDTO;
import com.example.transaction2.payload.ErrorData;
import com.example.transaction2.repository.CardRepository;
import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ValidatorService validator;

    public ApiResult<CardDTO> add(CardAddDTO cardDTO, User user) {
        List<ErrorData> errors = validator.checkCardNumberIsValidOrThrew(cardDTO.getCardNumber());
        if (!errors.isEmpty())
            throw RestException.restThrow("card number invalid",HttpStatus.BAD_REQUEST);
        if(cardRepository.existsByCardNumber(cardDTO.getCardNumber()))
            throw RestException.restThrow("Card number already existed",HttpStatus.FORBIDDEN);

        Card card = mapCardDtoToCard(cardDTO);
        card.setUser(user);
        cardRepository.save(card);
        return ApiResult.successResponse();
    }

    public ApiResult<List<CardDTO>> getAll(User user) {

        List<Card> cards = cardRepository.findAllByUserIdAndDeletedFalse(user.getId());

        return ApiResult.successResponse(cards.stream().map(this::mapCardToCardDTO)
                .collect(Collectors.toList()));
    }


    public ApiResult<CardDTO> get(String cardNumber,User user) {


        List<ErrorData> errorData = validator.checkCardNumberIsValidOrThrew(cardNumber);
        if (!errorData.isEmpty())
            throw RestException.restThrow("errorData",HttpStatus.BAD_REQUEST);

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() ->
                RestException.restThrow("CARD_NOT_FOUND", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(mapCardToCardDTO(card));
    }
    private CardTypeEnum findCardType(String cardNumber) {
        if (cardNumber.startsWith("9860"))
            return CardTypeEnum.HUMO;
        return CardTypeEnum.VISA;
    }
    private CurrencyEnum findCardCurrency(CardTypeEnum cardTypeEnum) {
        if (Objects.equals(cardTypeEnum,CardTypeEnum.HUMO))
            return CurrencyEnum.SUM;
        return CurrencyEnum.USD;
    }

    private Card mapCardDtoToCard(CardAddDTO cardDTO) {
        CardTypeEnum cardType = findCardType(cardDTO.getCardNumber());
        CurrencyEnum cardCurrency = findCardCurrency(cardType);
//        Card card = new Card();
        return Card.builder()
                .name(cardDTO.getName())
                .cardNumber(cardDTO.getCardNumber())
                .currency(cardCurrency)
                .type(cardType)
                .balance(0l)
                .build();
    }
    private CardDTO mapCardToCardDTO(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .name(card.getName())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .cardType(card.getType())
                .currency(card.getCurrency())
                .build();
    }
}
