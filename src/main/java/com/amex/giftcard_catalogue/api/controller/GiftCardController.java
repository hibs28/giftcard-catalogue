package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}