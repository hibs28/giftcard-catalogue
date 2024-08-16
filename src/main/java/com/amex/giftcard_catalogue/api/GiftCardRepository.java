package com.amex.giftcard_catalogue.api;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import jakarta.persistence.criteria.From;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GiftCardRepository extends JpaRepository<GiftCard, UUID> {
    @Query("select g From GiftCard g where g.id = ?1" )
     Optional<GiftCard> findGiftCardById(UUID id);
}
