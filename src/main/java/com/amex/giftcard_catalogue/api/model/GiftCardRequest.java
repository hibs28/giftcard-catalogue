package com.amex.giftcard_catalogue.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GiftCardRequest {
    @NotBlank(message = "Can not be empty")
    @JsonProperty(value = "company_name" )
    public String companyName;
    @Min(5)
    @NotNull(message = "Value can not be less than £25")
    public int value;
    @NotNull
    @JsonProperty(value = "points_cost")
    public int pointsCost;
    public GiftCardRequest(String companyName, int value, int pointsCost) {
        this.companyName = companyName;
        this.value = value;
        this.pointsCost = pointsCost;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getValue() {
        return value;
    }

    public int getPointsCost() {
        return pointsCost;
    }
}
