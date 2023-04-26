package com.example.transaction2.entity;

import com.example.transaction2.entity.enums.CardTypeEnum;
import com.example.transaction2.entity.enums.CurrencyEnum;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private Long balance;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Size(min=16, max=16)
    private String cardNumber;

    @Column(nullable = false)
    private CardTypeEnum type;

    @Column(nullable = false)
    private CurrencyEnum currency;

    @ManyToOne(optional = false)
    private User user;

    private boolean deleted;


}
