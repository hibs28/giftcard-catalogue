package com.amex.giftcard_catalogue.api.controller.error_handling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
    private int status;
    private String message;

    @JsonCreator
    public ErrorResponse(@JsonProperty("status") int status, @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


