package com.example.transaction2.entity;

import com.example.transaction2.entity.enums.CurrencyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long rate;
    @Column(nullable = false)
    private CurrencyEnum toCurrency;
    @Column(nullable = false)
    private CurrencyEnum fromCurrency;
}
