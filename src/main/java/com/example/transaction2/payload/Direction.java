package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class Direction {
    private String type;
    private PassRoute passRoute;
    private Train[] trains;
    private Object notAllTrains;
}