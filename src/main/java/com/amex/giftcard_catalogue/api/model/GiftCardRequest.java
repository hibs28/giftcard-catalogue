package com.amex.giftcard_catalogue.api.model;

public class GiftCardRequest{
    public GiftCardRequest(String company_name, int value, int points_cost) {
        this.company_name = company_name;
        this.value = value;
        this.points_cost = points_cost;
    }

    public String company_name;
    public int value;
    public int points_cost;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPoints_cost() {
        return points_cost;
    }

    public void setPoints_cost(int points_cost) {
        this.points_cost = points_cost;
    }
}
