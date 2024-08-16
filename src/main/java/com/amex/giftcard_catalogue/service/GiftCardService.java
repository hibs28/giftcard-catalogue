package com.amex.giftcard_catalogue.service;

import com.amex.giftcard_catalogue.api.GiftCardRepository;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GiftCardService {
    @Autowired
    private final GiftCardRepository giftCardRepository;

    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;
    }

    public List<GiftCard> getGiftCards() {
        return giftCardRepository.findAll();
    }

    private List<GiftCard> createGiftCardList() {
        return List.of(
                new GiftCard(UUID.fromString("af7c1fe6-d669-414e-b066-e9733f0de7a8"), "Disney", 100, 2000),
                new GiftCard(UUID.fromString("08c71152-c552-42e7-b094-f510ff44e9cb"), "Airbnb", 60, 150),
                new GiftCard(UUID.fromString("c558a80a-f319-4c10-95d4-4282ef745b4b"), "Hotels.com", 99, 75),
                new GiftCard(UUID.fromString("1ad1fccc-d279-46a0-8980-1d91afd6ba67"), "Adidas", 300, 8000)
        );
    }

}