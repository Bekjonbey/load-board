package com.example.transaction2.payload;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
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
public class DriverDTO {
    private UUID id;
    private String name;
    private String CompanyName;
    private String driverLicenceNumber;
    private String currentLocation;
    private boolean hasLoad;
    private UUID userId;
}
