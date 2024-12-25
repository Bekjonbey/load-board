package com.example.transaction2.payload;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Arrival {
    private String time;
    private String localTime;
    private String date;
    private Object stop;
    private String localDate;

}
