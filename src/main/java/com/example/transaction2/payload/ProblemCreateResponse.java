package com.example.transaction2.payload;

import lombok.Data;

@Data
public class ProblemCreateResponse {
    private String title;
    private String row1Result;
    private String row2Result;
    private String row3Result;
    private String row4Result;

    private String effect1;
    private String effect2;
    private String effect3;

    public ProblemCreateResponse(ProblemCreateResponseDto request) {
        this.title = String.format("%.2f", (request.getRatioPrice2() - request.getRatioPrice1()));
        this.row1Result = request.getPermanentCost1() + " / " + request.getAllProductCount1() + " + " + request.getTotalCost1() + " / " + request.getAllProductCount1() + " = " + String.format("%.2f", request.getRow1Result());
        this.row2Result = request.getPermanentCost1() + " / " + request.getAllProductCount2() + " + " + request.getTotalCost1() + " / " + request.getAllProductCount1() + " = " + String.format("%.2f", request.getRow2Result());
        this.row3Result = request.getPermanentCost2() + " / " + request.getAllProductCount2() + " + " + request.getTotalCost1() + " / " + request.getAllProductCount1() + " = " + String.format("%.2f", request.getRow3Result());
        this.row4Result = request.getPermanentCost2() + " / " + request.getAllProductCount2() + " + " + request.getChangeableCost2() + " / " + request.getAllProductCount2() + " = " + String.format("%.2f", request.getRow4Result());

        this.effect1 = request.getRow2Result() + " - " + request.getRow1Result() + " = " + String.format("%.2f", request.getEffect1());
        this.effect2 = request.getRow3Result() + " - " + getRow2Result() + " = " + String.format("%.2f", request.getEffect2());
        this.effect3 = request.getRow4Result() + " - " + getRow3Result() + " = " + String.format("%.2f", request.getEffect3());
    }
}
