package com.amex.giftcard_catalogue.api.controller;

import com.amex.giftcard_catalogue.api.controller.error_handling.CompanyNameNotFoundException;
import com.amex.giftcard_catalogue.api.controller.error_handling.ErrorResponse;
import com.amex.giftcard_catalogue.api.controller.error_handling.GiftCardNotFoundException;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.amex.giftcard_catalogue.api.model.GiftCardRequest;
import com.amex.giftcard_catalogue.service.GiftCardService;
import com.amex.giftcard_catalogue.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.amex.giftcard_catalogue.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GiftCardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GiftCardService giftCardService;
    @InjectMocks
    private GiftCardController controller;

    @Test
    public void getAll_shouldReturnAllExistingGiftCard() throws Exception {
        // GIVEN
        List<GiftCard> expectedGiftCards = TestUtils.buildMultipleGiftCard();
        String expectedJson = TestUtils.toJson(expectedGiftCards);

        //WHEN
        when(giftCardService.getGiftCards(null, null)).thenReturn(expectedGiftCards);

        //THEN
        mockMvc.perform(get("/gift_cards"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(giftCardService, times(1)).getGiftCards(null, null);
    }

    @Test
    public void getAll_shouldReturnErrorIfNoGiftCards() throws Exception {
        //WHEN
        when(giftCardService.getGiftCards(null, null)).thenReturn(Collections.emptyList());

        //THEN
        mockMvc.perform(get("/gift_cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(giftCardService, times(1)).getGiftCards(null, null);
    }


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

        verify(giftCardService, times(1)).getGiftCardById(id);
    }

    @Test
    public void getById_shouldThrowExceptionWithInvalidId_404() throws Exception {
        //Given
        UUID id = UUID.fromString("39942cdc-a568-43ed-9a54-b64eb207c103");

        //When
        when(giftCardService.getGiftCardById(id)).thenThrow(new GiftCardNotFoundException(id));

        // Mock Rest Call
        MvcResult result = mockMvc.perform(get("/gift_cards/" + id))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
        assertEquals("Gift card not found with ID " + id, errorResponse.getMessage());

        verify(giftCardService, times(1)).getGiftCardById(id);
    }

    @Test
    public void getByQuery_shouldReturnSingleGiftCardForValidQuery_200() throws Exception {

        GiftCard expectedResponse = new GiftCard(GIFT_CARD_ID, COMPANY_NAME_1, VALUE, 100000);
        String expectedJson = TestUtils.toJson(Collections.singletonList(expectedResponse));

        when(giftCardService.getGiftCards(eq(VALUE), eq(COMPANY_NAME_1)))
                .thenReturn(List.of(expectedResponse));

        mockMvc.perform(get("/gift_cards")
                        .param("value", "1000")
                        .param("companyName", "Disney"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(giftCardService, times(1)).getGiftCards(VALUE, COMPANY_NAME_1);
    }

    @Test
    public void getByQuery_shouldReturnMultipleGiftCardsForValidQuery_200() throws Exception {

        List<GiftCard> expectedResponse = TestUtils.buildMultipleGiftCard();
        String expectedJson = TestUtils.toJson(expectedResponse);

        when(giftCardService.getGiftCards(eq(VALUE), eq(COMPANY_NAME_1)))
                .thenReturn(expectedResponse);

        mockMvc.perform(get("/gift_cards")
                        .param("value", "1000")
                        .param("companyName", "Disney"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(giftCardService, times(1)).getGiftCards(VALUE, COMPANY_NAME_1);
    }

    @Test
    public void getByQuery_shouldReturnNotFoundForInvalidQuery_400() throws Exception {
        when(giftCardService.getGiftCards(eq(VALUE), eq(COMPANY_NAME_1)))
                .thenThrow(CompanyNameNotFoundException.class);

        mockMvc.perform(get("/gift_cards")
                        .param("value", "1000")
                        .param("companyName", "Disney"))
                .andExpect(status().isNotFound());

        verify(giftCardService, times(1)).getGiftCards(VALUE, COMPANY_NAME_1);

    }

    @Test
    public void postNewGiftCard_shouldCreateNewGiftCard_200() throws Exception {
        //GIVEN
        UUID id = UUID.fromString("cf02dd1b-33ee-4c8d-8303-f32d35b407ba");
        GiftCard expectedGiftCard = new GiftCard(id, "Test Gift Card", 150, 600);

        String request = TestUtils.toJson(buildPostRequest());

        //When
        when(giftCardService.createGiftCard(any(GiftCardRequest.class))).thenReturn(expectedGiftCard);

        // Mock Rest Call
        mockMvc.perform(post("/gift_cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print()) // print the response for debugging
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.company_name").value("Test Gift Card"))
                .andExpect(jsonPath("$.value").value(150))
                .andExpect(jsonPath("$.points_cost").value(600));

        verify(giftCardService, times(1)).createGiftCard(Mockito.any(GiftCardRequest.class));
    }

    @Test
    public void postNewGiftCard_InvalidRequestShouldReturnBadRequest_400() throws Exception {
        GiftCardRequest invalidRequest = new GiftCardRequest(null, 0, 0);
        mockMvc.perform(post("/gift_cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(giftCardService);
    }

    @Test
    public void deleteById_shouldReturnHeaderConfirmationMessage_204() throws Exception {
        //Given
        UUID id = UUID.randomUUID();

        //When
        Mockito.doNothing().when(giftCardService).removeGiftCard(id);

        // Mock Rest Call
        mockMvc.perform(delete("/gift_cards/" + id))
                .andExpect(status().isNoContent())
                .andExpect(header().string("message", "Gift card with ID " + id + " deleted successfully"));

        verify(giftCardService, times(1)).removeGiftCard(id);
    }


    @Test
    public void deleteById_shouldThrowExceptionWitNonExistingId_404() throws Exception {
        // GIVEN
        UUID nonExistingId = UUID.randomUUID();
        doThrow(new GiftCardNotFoundException(nonExistingId)).when(giftCardService).removeGiftCard(nonExistingId);

        // WHEN THEN
        MvcResult result = mockMvc.perform(delete("/gift_cards/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(giftCardService, times(1)).removeGiftCard(nonExistingId);

    }

    private GiftCardRequest buildPostRequest() {
        return new GiftCardRequest("Test Gift Card", 150, 600);
    }

}