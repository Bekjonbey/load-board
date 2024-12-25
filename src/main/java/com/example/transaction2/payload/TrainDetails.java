package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainDetails {
    private String length;
    private String type;
    private String number;
    private Route route;
    private String brand;
    private Places places;
    private Departure departure;
    private Arrival arrival;
    private String comments;
    private String timeInWay;
    private String number2;
    private DepartureTrain departureTrain;
    private String elRegPossible;
    private String firmName;
    private String parom;
    private String bus;
}