package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class LoadAddDTO {
    @NotBlank(message = "REQUIRED_FIELD")
    private String description;
    @NotNull(message = "REQUIRED_FIELD")
    private Long payment;
}

