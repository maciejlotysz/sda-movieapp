package com.maja.sdamovieapp.order.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatusEnum {

    PREPARED_TO_SEND("Twoje zamówienie jest przygotowane do wysyłki"),
    ON_THE_WAY("Twoje zamowienie zostało odebrane przez kuriera"),
    DELIVERED("Twoje zamówienie zostało dostarczone");

    public final String description;
}