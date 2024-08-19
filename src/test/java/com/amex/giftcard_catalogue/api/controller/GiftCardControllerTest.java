package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.service.GiftCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GiftCardControllerTest {

    private static final UUID GIFT_CARD_ID = UUID.fromString("cf02dd1b-33ee-4c8d-8303-f32d35b407ba");
    private static final int VALUE = 1000;
    private static final String COMPANY_NAME = "Disney";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GiftCardService giftCardService;

    @InjectMocks
    private GiftCardController controller;


    @Test
    public void getById_shouldReturnExistingGiftCard_200() throws Exception {
        //Given
        UUID id = UUID.randomUUID();
        GiftCard expectedGiftCard = new GiftCard(id, "Test Gift Card", 50, 10000);

        //When
        when(giftCardService.getGiftCardById(id)).thenReturn(expectedGiftCard);

        // Mock Rest Call
        mockMvc.perform(get("/gift_cards/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.company_name").value("Test Gift Card"))
                .andExpect(jsonPath("$.value").value(50))
                .andExpect(jsonPath("$.points_cost").value(10000));

    }


    @Test
    public void getById_shouldThrowExceptionWithInvalidId_() throws Exception {
        //Given
        UUID id = UUID.randomUUID();

        //When
        when(giftCardService.getGiftCardById(id)).thenThrow(NoSuchElementException.class);

        // Mock Rest Call
        mockMvc.perform(get("/gift_cards/" + id))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getByQuery_shouldReturnGiftCardForValidQuery() throws Exception {

        GiftCard expectedResponse = new GiftCard(GIFT_CARD_ID, COMPANY_NAME, VALUE, 100000);

        String jsonString = "{\"id\":\"" + expectedResponse.getId() + "\",\"company_name\":\"" + expectedResponse.getCompany_name() + "\",\"value\":" + expectedResponse.getValue() + ",\"points_cost\":" + expectedResponse.getPoints_cost() + "}";


        when(giftCardService.getGiftCardByValueAndCompanyName(eq(VALUE), eq(COMPANY_NAME)))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/gift_cards")
                        .param("value", "1000")
                        .param("companyName", "Disney"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString));

    }

    @Test
    public void shouldReturnNotFoundForInvalidQuery() throws Exception {
        when(giftCardService.getGiftCardByValueAndCompanyName(eq(VALUE), eq(COMPANY_NAME)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/gift_cards")
                        .param("value", "1000")
                        .param("companyName", "Disney"))
                .andExpect(status().isNotFound());
    }

    private GiftCard createGiftCard() {
        return new GiftCard(GIFT_CARD_ID, COMPANY_NAME, VALUE, 100000);
    }

}