package com.example.transaction2.repository;

import com.example.transaction2.entity.Rate;
import com.example.transaction2.entity.enums.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate,Long> {
    Optional<Rate> findByFromCurrencyAndToCurrency(CurrencyEnum fromCurrency, CurrencyEnum toCurrency);
}
