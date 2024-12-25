package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class Departure {
    private String time;
    private String localTime;
    private String stop;
    private String localDate;

    // Getters and Setters
}
