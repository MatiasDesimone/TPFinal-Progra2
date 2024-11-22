package org.example.models;

import org.example.enums.ECard;

import java.time.LocalDate;
import java.util.UUID;

public class Card {
    private String clientId;
    private String cardId;
    private String number;
    private String cvv;
    private LocalDate expirationDate;
    private ECard type;

    public Card() {
    }

    public Card(String clientId, String number, String cvv, LocalDate expirationDate, ECard type) {
        this.clientId = clientId;
        this.cardId = UUID.randomUUID().toString();
        this.number = number;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.type = type;
    }
}
