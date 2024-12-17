package com.example.transaction2.payload;

import lombok.Data;

@Data
public class ProblemCreateRequestDto {
    private Long allProductCount1;
    private Long allProductCount2;

    private Double permanentCost1;
    private Double permanentCost2;

    private Double changeableCost1;
    private Double changeableCost2;
}
