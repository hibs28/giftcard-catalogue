package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.api.model.GiftCardRequest;
import com.amex.giftcard_catalogue.service.GiftCardService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "gift_cards")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @Autowired
    public GiftCardController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GiftCard> getById(@PathVariable(value = "id") UUID id) {
        GiftCard response = giftCardService.getGiftCardById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GiftCard>> getByValueAndCompanyName(@RequestParam(value = "value") int value,
                                                                   @RequestParam(value = "companyName") String companyName) throws BadRequestException {
        if (companyName.isEmpty()) {
            throw new BadRequestException("companyName must be provided");
        }
        if (value < 5) {
            throw new BadRequestException("Gift card value must be greater than 5");
        }
        List<GiftCard> response = giftCardService.getGiftCardByValueAndCompanyName(value, companyName);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<GiftCard> createGiftCard(@RequestBody @Valid GiftCardRequest giftCardRequest) {
        GiftCard response = giftCardService.createGiftCard(giftCardRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable UUID id) {
        giftCardService.removeGiftCard(id);
        return ResponseEntity.noContent()
                .header("message", "Gift card with ID " + id + " deleted successfully")
                .build();
    }
}