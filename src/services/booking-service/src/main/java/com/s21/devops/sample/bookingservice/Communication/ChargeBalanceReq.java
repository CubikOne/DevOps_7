package com.s21.devops.sample.bookingservice.Communication;

public class ChargeBalanceReq {
    private Float charge;

    public static ChargeBalanceReq chargeBalanceReqFromCharge(Float cost) {
        ChargeBalanceReq chargeBalanceReq = new ChargeBalanceReq();
        chargeBalanceReq.setCharge(cost);
        return chargeBalanceReq;
    }

    public Float getCharge() {
        return charge;
    }

    public void setCharge(Float charge) {
        this.charge = charge;
    }
}
