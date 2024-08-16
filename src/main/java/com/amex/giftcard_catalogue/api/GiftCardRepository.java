package com.amex.giftcard_catalogue.api;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GiftCardRepository extends JpaRepository<GiftCard, UUID> {
}
