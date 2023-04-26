package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class DriverAddDTO {
    @NotBlank(message = "REQUIRED_FIELD")
    private String name;
    @NotBlank(message = "REQUIRED_FIELD")
    private String CompanyName;
    @NotBlank(message = "REQUIRED_FIELD")
    private String driverLicenceNumber;
}
