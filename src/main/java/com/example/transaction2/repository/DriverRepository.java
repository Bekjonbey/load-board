package com.example.transaction2.repository;

import com.example.transaction2.entity.Card;
import com.example.transaction2.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
        boolean existsByNameOrDriverLicenceNumber(String name,String licenceNumber);

        List<Driver> findAllByUserId(UUID id);

        Driver findByName(String driverName);
}
