package com.amex.giftcard_catalogue.api.controller.error_handling;

import java.util.NoSuchElementException;
import java.util.UUID;

public class GiftCardNotFoundException extends NoSuchElementException {
    private final String id;

    public GiftCardNotFoundException(UUID id) {
        super("Gift card not found with ID: " + id);
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }
}
