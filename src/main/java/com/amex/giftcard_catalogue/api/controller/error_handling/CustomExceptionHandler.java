package com.amex.giftcard_catalogue.api.controller.error_handling;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(GiftCardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGiftCardNotFoundException(GiftCardNotFoundException ex) {
        String id = ex.getId();
        ErrorResponse errorResponse = new ErrorResponse(404, "Gift card not found with ID " + id);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad request " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(400, "Required request parameter '" + ex.getParameterName() + "' is not present");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyNameNotFoundException(CompanyNameNotFoundException ex) {
        String companyName = ex.getCompanyName();
        return new ErrorResponse(404, "Company name: " + companyName + " not found");
    }

    @ExceptionHandler(GiftCardValueNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyValueNotFoundException(GiftCardValueNotFoundException ex) {
        String value = ex.getValue();
        String companyName = ex.getCompanyName();
        return new ErrorResponse(404, "Gift card value £ " + value + " for company name " + companyName + " not found");

    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
