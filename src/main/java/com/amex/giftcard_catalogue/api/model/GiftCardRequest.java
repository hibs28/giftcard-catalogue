package com.amex.giftcard_catalogue.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GiftCardRequest {
    @NotBlank(message = "Can not be empty")
    public String company_name;
    @Min(25)
    @NotNull(message = "Value can not be less than £25")
    public int value;
    @NotNull
    public int points_cost;

    public GiftCardRequest(String company_name, int value, int points_cost) {
        this.company_name = company_name;
        this.value = value;
        this.points_cost = points_cost;
    }

    public String getCompany_name() {
        return company_name;
    }

    public int getValue() {
        return value;
    }

    public int getPoints_cost() {
        return points_cost;
    }
}
