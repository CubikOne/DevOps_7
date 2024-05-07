package com.s21.devops.sample.bookingservice.Communication;

import java.util.UUID;

public class RefundReq {
    private UUID paymentUid;

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
    }
}
