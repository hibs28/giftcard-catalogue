package com.amex.giftcard_catalogue.api.controller.error_handling;

public class ValueNotFoundException extends RuntimeException {
    private final int value;

    public ValueNotFoundException(int value) {
        super("Could not find gift cards with a value of £" + value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
