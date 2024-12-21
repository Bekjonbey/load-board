package com.example.transaction2.payload;

import lombok.Data;

@Data
public class StringResponse {
    private String message;

    public StringResponse(String success) {
        this.message = success;
    }
}
