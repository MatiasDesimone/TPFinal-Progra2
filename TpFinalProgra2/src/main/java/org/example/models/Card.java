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

    public Card(String clientId, ECard type) {
        this.clientId = clientId;
        this.cardId = UUID.randomUUID().toString();
        this.number = generateUniqueCardNumber();
        this.cvv = generateRandomCvv();
        this.expirationDate = generateRandomExpirationDate(type);
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ECard getType() {
        return type;
    }

    public void setType(ECard type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        return cardId.equals(card.cardId) && number.equals(card.number);
    }

    @Override
    public int hashCode() {
        int result = cardId.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber();
        } while (Bank.getInstance().getExistingCardNumbers().contains(cardNumber)); //Probabilidad de 0,000000000000000000743 de que esto ocurra.
        Bank.getInstance().getExistingCardNumbers().add(cardNumber);
        return cardNumber;
    }

    private String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append((int) (Math.random() * 10));
        }
        return cardNumber.toString();
    }

    private String generateRandomCvv() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append((int) (Math.random() * 10));
        }
        return cvv.toString();
    }

    private LocalDate generateRandomExpirationDate(ECard type) {
        LocalDate currentDate = LocalDate.now();
        int yearsToAdd = (type == ECard.CREDIT) ? 5 : 4;
        return currentDate.plusYears(yearsToAdd);
    }
}
