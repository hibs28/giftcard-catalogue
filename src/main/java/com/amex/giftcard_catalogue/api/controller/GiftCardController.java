package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.api.model.GiftCardRequest;
import com.amex.giftcard_catalogue.service.GiftCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "gift_cards")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @Autowired
    public GiftCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    @GetMapping
    public List<GiftCard> getAll() {
        return giftCardService.getGiftCards();
    }


    @PostMapping
    public ResponseEntity<GiftCard> createGiftCard(@RequestBody @Valid GiftCardRequest giftCardRequest) {
        GiftCard response = giftCardService.createGiftCard(giftCardRequest);
        return ResponseEntity.ok(response);
    }

}