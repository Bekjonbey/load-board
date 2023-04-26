package com.example.transaction2.payload;

import com.example.transaction2.entity.enums.CardTypeEnum;
import com.example.transaction2.entity.enums.CurrencyEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class CardDTO {
    private long id;
    private Long balance;
    private String name;
    private String cardNumber;
    private CardTypeEnum cardType;
    private CurrencyEnum currency;
    private UUID userId;

}
