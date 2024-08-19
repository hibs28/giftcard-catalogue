package com.amex.giftcard_catalogue.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "giftcard")
public class GiftCard {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    public UUID id;

    @Column(nullable = false, name = "company_name")
    @NotBlank
    public String companyName;

    @NotNull
    public int value;

    @Column(nullable = false, name = "points_cost")
    @NotNull
    public int pointsCost;

    //GET
    public GiftCard(UUID id, String companyName, int value, int pointsCost) {
        this.id = id;
        this.companyName = companyName;
        this.value = value;
        this.pointsCost = pointsCost;
    }

    //POST
    public GiftCard(String companyName, int value, int pointsCost) {
        this.companyName = companyName;
        this.value = value;
        this.pointsCost = pointsCost;
    }

    public GiftCard() {
    }

    @JsonSerialize(using = UUIDSerializer.class)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("company_name")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    @JsonProperty("points_cost")

    public void setPointsCost(int pointsCost) {
        this.pointsCost = pointsCost;
    }

    @Override
    public String toString() {
        return "GiftCard{" +
                "id=" + id +
                ", company_name='" + companyName + '\'' +
                ", value=" + value +
                ", points_cost=" + pointsCost +
                '}';
    }
}