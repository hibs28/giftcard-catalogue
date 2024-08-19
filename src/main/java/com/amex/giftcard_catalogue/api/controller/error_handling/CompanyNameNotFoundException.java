package com.amex.giftcard_catalogue.api.controller.error_handling;

public class CompanyNameNotFoundException extends RuntimeException {
    private final String companyName;

    public CompanyNameNotFoundException(String companyName) {
        super("Company name:" + companyName + "not found");
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
