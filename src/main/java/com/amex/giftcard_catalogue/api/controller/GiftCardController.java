package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "gift_cards")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @Autowired
    public GiftCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

//    @GetMapping
//    public List<GiftCard> getAll() {
//        return giftCardService.getGiftCards();
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCard> getById(@PathVariable(value = "id") UUID id) {
        GiftCard response = giftCardService.getGiftCardById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<GiftCard> getByValueAndCompanyName(@RequestParam(value = "value") int value,
                                             @RequestParam(value = "companyName") String companyName) {
        GiftCard response = giftCardService.getGiftCardByValueAndCompanyName(value, companyName);
        return ResponseEntity.ok(response);
    }

}