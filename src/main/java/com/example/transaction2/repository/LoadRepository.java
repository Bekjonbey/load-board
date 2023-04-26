package com.example.transaction2.repository;

import com.example.transaction2.entity.Load;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load,Long> {

    List<Load> findAllByUserId(UUID id);

    Optional<Load> findById(Long id);
}
