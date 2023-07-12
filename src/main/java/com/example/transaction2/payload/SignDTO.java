package com.example.transaction2.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SignDTO {
    @NotBlank
    private String phone;

    @NotBlank
    private String password;
    @NotBlank
    private String position;

    @Override
    public boolean equals(Object obj) {
        SignDTO other = (SignDTO) obj;

        if (!other.getPhone().equals(getPhone()))
            return false;

        return other.getPassword().equals(getPassword());
    }
}
