package com.amex.giftcard_catalogue.api.controller.error_handling;

import java.util.NoSuchElementException;

public class GiftCardNotFoundException extends NoSuchElementException {
    private final String id;

    public GiftCardNotFoundException(String id) {
        super("Gift card not found with ID: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
