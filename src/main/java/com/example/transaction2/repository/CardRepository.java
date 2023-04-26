package com.example.transaction2.repository;

import com.example.transaction2.entity.Card;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card,Long> {
    boolean existsByCardNumber(@Size(min = 16, max = 16) String cardNumber);
    boolean existsByCardNumberAndUserId(@Size(min = 16, max = 16) String cardNumber, UUID user_id);

    List<Card> findAllByUserIdAndDeletedFalse(UUID user_id);

    Optional<Card> findByCardNumber(String cardNumber);

    Optional<Card> findByUserId(UUID senderId);
}
