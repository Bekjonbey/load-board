package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class Express {
    private boolean hasError;
    private String type;
    private Object showWithoutPlaces;
    private Direction[] direction;
    private String reqExpressZK;
    private String reqLocalSend;
    private String reqLocalRecv;
    private String reqAddress;
    private String reqExpressDateTime;
    private String content;
    private String code;
}