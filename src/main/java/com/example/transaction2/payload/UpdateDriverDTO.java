package com.example.transaction2.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class UpdateDriverDTO {
    private String name;
    private String currentLocation;
}
