package com.example.transaction2.payload;


import lombok.Data;

@Data
public class ProblemCreateResponseDto {

    private Long allProductCount1;
    private Long allProductCount2;
    private Long allProductCountDifference;//allProductCount1 - allProductCount2

    private Double permanentCost1;
    private Double permanentCost2;
    private Double permanentCostDifference;

    private Double changeableCost1;
    private Double changeableCost2;
    private Double changeableCostDifference;

    private Double totalCost1;//changeableCost1 + permanentCost1
    private Double totalCost2;
    private Double totalCostDifference;

    private Double changeableCostProductRatio1;//changeableCost1 / allProductCount1
    private Double changeableCostProductRatio2;
    private Double changeableCostProductRatioDifference;

    private Double permanentCostProductRatio1;//permanentCost1 / allProductCount1
    private Double permanentCostProductRatio2;
    private Double permanentCostProductRatioDifference;

    private Double ratioPrice1;//changeableCostProductRatio1 + permanentCostProductRatio1
    private Double ratioPrice2;
    private Double ratioPriceDifference;

    private Double row1Result;
    private Double row2Result;
    private Double row3Result;
    private Double row4Result;

    private Double effect1;
    private Double effect2;
    private Double effect3;

    public ProblemCreateResponseDto(ProblemCreateRequestDto request) {

        double changeableCostProductRatioNew1 = request.getChangeableCost1() / request.getAllProductCount1();
        double changeableCostProductRatioNew2 = request.getChangeableCost2() / request.getAllProductCount2();

        double totalCostNew1 = request.getChangeableCost1() + request.getPermanentCost1();
        double totalCostNew2 = request.getChangeableCost2() + request.getPermanentCost2();

        double permanentCostProductRatioNew1 = request.getPermanentCost1() / request.getAllProductCount1();
        double permanentCostProductRatioNew2 = request.getPermanentCost2() / request.getAllProductCount2();


        this.allProductCount1 = request.getAllProductCount1();
        this.allProductCount2 = request.getAllProductCount2();
        this.allProductCountDifference = request.getAllProductCount2() - request.getAllProductCount1();

        this.permanentCost1 = request.getPermanentCost1();
        this.permanentCost2 = request.getPermanentCost2();
        this.permanentCostDifference = request.getPermanentCost2() - request.getPermanentCost1();

        this.changeableCost1 = request.getChangeableCost1();
        this.changeableCost2 = request.getChangeableCost2();
        this.changeableCostDifference = request.getChangeableCost2() - request.getChangeableCost1();

        this.totalCost1 = totalCostNew1;
        this.totalCost2 = totalCostNew2;
        this.totalCostDifference = (totalCostNew2 - totalCostNew1);

        this.changeableCostProductRatio1 = changeableCostProductRatioNew1;
        this.changeableCostProductRatio2 = changeableCostProductRatioNew2;
        this.changeableCostProductRatioDifference = changeableCostProductRatioNew2 - changeableCostProductRatioNew1;

        this.permanentCostProductRatio1 = permanentCostProductRatioNew1;
        this.permanentCostProductRatio2 = permanentCostProductRatioNew2;
        this.permanentCostProductRatioDifference = permanentCostProductRatioNew2 - permanentCostProductRatioNew1;

        this.ratioPrice1 = changeableCostProductRatioNew1 + permanentCostProductRatioNew1;
        this.ratioPrice2 = changeableCostProductRatioNew2 + permanentCostProductRatioNew2;
        this.ratioPriceDifference = -(changeableCostProductRatioNew1 + permanentCostProductRatioNew1) + (changeableCostProductRatioNew2 + permanentCostProductRatioNew2);

        this.row1Result = permanentCostProductRatioNew1 + changeableCostProductRatioNew1;
        this.row2Result = request.getPermanentCost1() / request.getAllProductCount2() + changeableCostProductRatioNew1;
        this.row3Result = permanentCostProductRatioNew2 + changeableCostProductRatioNew1;
        this.row4Result = permanentCostProductRatioNew2 + changeableCostProductRatioNew2;

        this.effect1 = (request.getPermanentCost1() / request.getAllProductCount2() + changeableCostProductRatioNew1) - (permanentCostProductRatioNew1 + changeableCostProductRatioNew1);
        this.effect2 = (permanentCostProductRatioNew2 + changeableCostProductRatioNew1) - (request.getPermanentCost1() / request.getAllProductCount2() + changeableCostProductRatioNew1);
        this.effect3 = (permanentCostProductRatioNew2 + changeableCostProductRatioNew2) - (permanentCostProductRatioNew2 + changeableCostProductRatioNew1);
    }
}
