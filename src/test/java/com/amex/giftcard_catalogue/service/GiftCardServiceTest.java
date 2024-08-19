package com.amex.giftcard_catalogue.service;

import com.amex.giftcard_catalogue.api.GiftCardRepository;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GiftCardServiceTest {

    @Mock
    private GiftCardRepository giftCardRepository;

    private GiftCardService giftCardService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        giftCardService = new GiftCardService(giftCardRepository);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void getGiftCardById_WithExistingId_ReturnsGiftCard() {
        //GIVEN
        UUID expectedID = UUID.fromString("89a0d90f-cc11-450a-9acf-ae597def764d");
        GiftCard expectedGiftCard = new GiftCard(expectedID, "Disney", 1000, 10);
        Optional<GiftCard> optionalGiftCard = Optional.of(expectedGiftCard);

        //WHEN
        when(giftCardRepository.findGiftCardById(expectedID)).thenReturn(optionalGiftCard);
        GiftCard actualGiftCard = giftCardService.getGiftCardById(expectedID);

        assertNotNull(actualGiftCard);
        assertEquals(expectedGiftCard, actualGiftCard);

    }

    @Test
    public void testGetGiftCardById_NonExistingId_ThrowsException() {
        // Given
        UUID nonExistingId = UUID.randomUUID();

        // When
        when(giftCardRepository.findGiftCardById(nonExistingId)).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> giftCardService.getGiftCardById(nonExistingId));
    }
}