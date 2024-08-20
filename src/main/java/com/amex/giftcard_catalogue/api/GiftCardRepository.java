package com.amex.giftcard_catalogue.api;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GiftCardRepository extends JpaRepository<GiftCard, UUID> {
    boolean existsByCompanyName(String name);
    boolean existsById(UUID id);

    @Query("select g From GiftCard g where g.id = ?1")
    Optional<GiftCard> findGiftCardById(UUID id);

    @Query("select g From GiftCard g where g.companyName = ?1")
    Optional<List<GiftCard>> findGiftCardByCompanyName(String companyName);

    @Query("select g From GiftCard g where g.value = ?1")
    Optional<List<GiftCard>> findGiftCardByValue(int companyName);

    @Query("select g From GiftCard g where g.value = ?1 and g.companyName = ?2")
    Optional<List<GiftCard>> findGiftCardByValueAndCompany(int value, String companyName);
}
