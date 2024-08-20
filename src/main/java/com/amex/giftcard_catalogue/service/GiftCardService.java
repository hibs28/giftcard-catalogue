package com.amex.giftcard_catalogue.service;

import com.amex.giftcard_catalogue.api.GiftCardRepository;
import com.amex.giftcard_catalogue.api.controller.error_handling.CompanyNameNotFoundException;
import com.amex.giftcard_catalogue.api.controller.error_handling.GiftCardNotFoundException;
import com.amex.giftcard_catalogue.api.controller.error_handling.GiftCardValueNotFoundException;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.api.model.GiftCardRequest;
import jakarta.validation.Valid;
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

    public List<GiftCard> getAllGiftCards() {
        return giftCardRepository.findAll();
    }

    public GiftCard getGiftCardById(UUID id) {
        return giftCardRepository.findGiftCardById(id).orElseThrow(() -> new GiftCardNotFoundException(id.toString()));
    }

    public List<GiftCard> getGiftCardByValueAndCompanyName(int value, String companyName) {
        if (!giftCardRepository.existsByCompanyName(companyName)) {
            throw new CompanyNameNotFoundException(companyName);
        }
        return giftCardRepository.findGiftCardByValueAndCompany(value, companyName)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new GiftCardValueNotFoundException(value, companyName));
    }

    public GiftCard createGiftCard(@Valid GiftCardRequest giftCardRequest) throws IllegalStateException {
        GiftCard giftCard = new GiftCard(giftCardRequest.getCompanyName(), giftCardRequest.getValue(), giftCardRequest.getPointsCost());
        return giftCardRepository.save(giftCard);
    }
}