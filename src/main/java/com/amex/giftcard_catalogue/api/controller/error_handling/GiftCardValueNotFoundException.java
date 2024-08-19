package com.amex.giftcard_catalogue.api.controller.error_handling;

public class GiftCardValueNotFoundException extends RuntimeException {
    private final String value;
    private final String companyName;

    public GiftCardValueNotFoundException(int value, String companyName) {
        super("Gift card value of £" + value + " for company name " + companyName + " not found");
        this.value = String.valueOf(value);
        this.companyName = companyName;
    }

    public String getValue() {
        return value;
    }

    public String getCompanyName() {
        return companyName;
    }

}
