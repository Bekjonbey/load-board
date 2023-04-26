package com.example.transaction2.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardAddDTO {
    @NotNull(message = "CARD_BALANCE_MUST_NOT_BE_NULL")
    @Min(value = 0,message = "Balance must not be negative")
    private Long balance;
    @NotBlank(message = "CARD_NAME_MUST_NOT_BE_BLANK")
    private String name;
    @Size(min = 16,max = 16, message = "At least must be 16 numbers")
    @NotBlank(message = "EMPTY_FIELD")
    private String cardNumber;
}
