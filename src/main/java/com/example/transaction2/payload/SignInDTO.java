package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SignInDTO {
    @NotBlank
    private String phone;

    @NotBlank
    private String password;
}
