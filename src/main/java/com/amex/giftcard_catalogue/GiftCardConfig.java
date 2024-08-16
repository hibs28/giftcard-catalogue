package com.amex.giftcard_catalogue;

import com.amex.giftcard_catalogue.api.GiftCardRepository;
import com.amex.giftcard_catalogue.api.model.GiftCard;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GiftCardConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            GiftCardRepository repository) {
        return args -> {
           GiftCard giftCard1 = new GiftCard("Disney", 100, 2000);
           GiftCard giftCard2 = new GiftCard("Airbnb", 60, 150);
           GiftCard giftCard3 = new GiftCard("Hotels.com", 99, 75);
           GiftCard giftCard4 = new GiftCard( "Adidas", 300, 8000);

           repository.saveAll(List.of(
                   giftCard1, giftCard2, giftCard3, giftCard4
           ));

        };
    }
}
