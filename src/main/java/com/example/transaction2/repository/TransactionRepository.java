package com.example.transaction2.repository;

import com.example.transaction2.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction,UUID> {

}
