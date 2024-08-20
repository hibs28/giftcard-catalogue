package com.amex.giftcard_catalogue.service;

import com.amex.giftcard_catalogue.api.GiftCardRepository;
import com.amex.giftcard_catalogue.api.controller.error_handling.CompanyNameNotFoundException;
import com.amex.giftcard_catalogue.api.controller.error_handling.GiftCardNotFoundException;
import com.amex.giftcard_catalogue.api.controller.error_handling.GiftCardValueNotFoundException;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.api.model.GiftCardRequest;
import com.amex.giftcard_catalogue.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.amex.giftcard_catalogue.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        // GIVEN
        UUID expectedID = UUID.fromString("89a0d90f-cc11-450a-9acf-ae597def764d");
        GiftCard expectedGiftCard = new GiftCard(expectedID, "Disney", 1000, 10);
        Optional<GiftCard> optionalGiftCard = Optional.of(expectedGiftCard);

        // WHEN
        when(giftCardRepository.findGiftCardById(expectedID)).thenReturn(optionalGiftCard);
        GiftCard actualGiftCard = giftCardService.getGiftCardById(expectedID);

        //THEN
        assertNotNull(actualGiftCard);
        assertEquals(expectedGiftCard, actualGiftCard);
    }

    @Test
    public void testGetGiftCardById_NonExistingId_ThrowsException() {
        // GIVEN
        UUID nonExistingId = UUID.randomUUID();

        // WHEN
        when(giftCardRepository.findGiftCardById(nonExistingId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(GiftCardNotFoundException.class, () -> giftCardService.getGiftCardById(nonExistingId));
    }

    @Test
    public void testGetGiftCardByCompanyNameAndValue_ForNonExistingCompanyName_ThrowsException() {
        // WHEN
        when(giftCardRepository.existsByCompanyName(COMPANY_NAME_1)).thenReturn(false);

        // THEN
        assertThrows(CompanyNameNotFoundException.class, () -> giftCardService.getGiftCardByValueAndCompanyName(VALUE, COMPANY_NAME_1));
    }

    @Test
    public void testGetGiftCardByCompanyNameAndValue_ForNonExistingGiftCardValue_ThrowsException() {

        // WHEN
        when(giftCardRepository.existsByCompanyName(COMPANY_NAME_1)).thenReturn(true);
        when(giftCardRepository.findGiftCardByValueAndCompany(VALUE, COMPANY_NAME_1)).thenReturn(Optional.empty());

        // THEN
        assertThrows(GiftCardValueNotFoundException.class, () -> giftCardService.getGiftCardByValueAndCompanyName(VALUE, COMPANY_NAME_1));
    }

    @Test
    public void testCreateGiftCard_validRequest_createsGiftCard() {
        // GIVEN
        GiftCardRequest giftCardRequest = new GiftCardRequest("Company Name", 10, 100);
        GiftCard expectedGiftCard = new GiftCard(giftCardRequest.getCompanyName(), giftCardRequest.getValue(), giftCardRequest.getPointsCost());

        // Mock the repository to return the saved gift card
        when(giftCardRepository.save(any(GiftCard.class))).thenReturn(expectedGiftCard);

        // WHEN
        GiftCard actualGiftCard = giftCardService.createGiftCard(giftCardRequest);

        // THEN
        assertEquals(expectedGiftCard, actualGiftCard);
        verify(giftCardRepository, times(1)).save(any(GiftCard.class));
    }

    @Test
    public void testCreateGiftCard_invalidRequest_throwsIllegalStateException() {
        //GIVEN
        GiftCardRequest giftCardRequest = new GiftCardRequest(null, 5000, 0);

        //THEN
        assertThrows(IllegalStateException.class, () -> giftCardService.createGiftCard(giftCardRequest));
    }

    @Test
    public void testRemoveGiftCard_existingGiftCard() {
        TestUtils.buildMultipleGiftCard();

        when(giftCardRepository.existsById(GIFT_CARD_ID)).thenReturn(true);
        giftCardService.removeGiftCard(GIFT_CARD_ID);

        verify(giftCardRepository, times(1)).deleteById(GIFT_CARD_ID);
    }

    @Test
    public void testRemoveGiftCard_nonExistingGiftCard() {
        UUID id = UUID.randomUUID();
        when(giftCardRepository.existsById(id)).thenReturn(false);

        assertThrows(GiftCardNotFoundException.class, () -> giftCardService.removeGiftCard(id), "Gift card not found with ID: " + id);
    }
}