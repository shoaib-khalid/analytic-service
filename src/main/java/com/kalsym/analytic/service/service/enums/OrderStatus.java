package com.kalsym.analytic.service.service.enums;

public enum OrderStatus {
    BEING_DELIVERED,
    BEING_PREPARED,
    CANCELED_BY_CUSTOMER,
    DELIVERED_TO_CUSTOMER,
    PAYMENT_CONFIRMED,
    READY_FOR_DELIVERY,
    RECEIVED_AT_STORE,
    REFUNDED,
    REJECTED_BY_STORE,
    REQUESTING_DELIVERY_FAILED,
    AWAITING_PICKUP,
    FAILED,
    PAID;
}