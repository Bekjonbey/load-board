package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long receiver_amount;
    @NotBlank
    private String receiver_card_number;
    private Long sender_amount;
    @NotBlank
    private String sender_card_number;
}
