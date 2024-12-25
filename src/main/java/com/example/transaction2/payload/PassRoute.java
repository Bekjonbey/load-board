package com.example.transaction2.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PassRoute {
    private String from;
    private String to;
    private String codeFrom;
    private String codeTo;

}