package com.s21.devops.sample.loyaltyservice.Communication;

public class LoyaltyBalanceRes {
    private String status;
    private Double discount;

    public static LoyaltyBalanceRes LoyaltyBalanceResFromParams(String status, Double discount){
        LoyaltyBalanceRes loyaltyBalanceRes = new LoyaltyBalanceRes();
        loyaltyBalanceRes.setDiscount(discount);
        loyaltyBalanceRes.setStatus(status);
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
