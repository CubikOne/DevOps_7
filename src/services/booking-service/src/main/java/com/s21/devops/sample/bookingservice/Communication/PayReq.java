package com.s21.devops.sample.bookingservice.Communication;

public class PayReq {
    private Float cost;
    private String paymentInfo;

    public static PayReq payReqFromParams(String paymentInfo, Float cost) {
        PayReq payReq = new PayReq();
        payReq.setCost(cost);
        payReq.setPaymentInfo(paymentInfo);
        return payReq;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
