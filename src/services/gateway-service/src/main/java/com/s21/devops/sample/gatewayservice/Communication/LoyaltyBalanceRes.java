package com.s21.devops.sample.gatewayservice.Communication;

public class LoyaltyBalanceRes {
    private String status;
    private Double discount;

    public static LoyaltyBalanceRes loyaltyBalanceResFromParams(String status, Double discount) {
        LoyaltyBalanceRes loyaltyBalanceRes = new LoyaltyBalanceRes();
        loyaltyBalanceRes.setStatus(status);
        loyaltyBalanceRes.setDiscount(discount);
        return loyaltyBalanceRes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
