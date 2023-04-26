package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;



@Builder
@Getter
public class VerificationDTO {
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$", message = "MISMATCH")
    @NotBlank(message = "EMPTY_FIELD")
    private String phoneNumber;
    @Size(min = 8,max = 8, message = "At least must be 8 characters")
    @NotBlank(message = "EMPTY_FIELD")
    private String verificationCode;
}