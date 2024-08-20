package com.amex.giftcard_catalogue.utils;

import com.amex.giftcard_catalogue.api.model.GiftCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static final UUID GIFT_CARD_ID = UUID.fromString("cf02dd1b-33ee-4c8d-8303-f32d35b407ba");
    public static final UUID GIFT_CARD_ID_2 = UUID.fromString("9a4c59ef-e876-495e-8986-e080b88fb4ad");
    public static final UUID GIFT_CARD_ID_3 = UUID.fromString("0a91fe46-4f5f-478d-957c-2effe46aa497");
    public static final int VALUE = 1000;
    public static final int VALUE_2 = 35;
    public static final String COMPANY_NAME_1 = "Disney";
    public static final String COMPANY_NAME_2 = "AirBnb";

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static List<GiftCard> buildMultipleGiftCard() {
        return List.of(new GiftCard(GIFT_CARD_ID, COMPANY_NAME_1, VALUE, 100000),
                new GiftCard(GIFT_CARD_ID_2, COMPANY_NAME_1, VALUE, 100000));
    }

    public static String loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = TestUtils.class.getResourceAsStream("/" + filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + filePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}
