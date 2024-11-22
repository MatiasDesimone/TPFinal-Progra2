package org.example.models;

import org.example.enums.EStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String accountOwnerId;
    private String accountRecipientId;
    private String transactionId;
    private double amount;
    private String description;
    private LocalDateTime date;
    private EStatus status;

    public Transaction() {
    }

    public Transaction(String accountOwnerId, String accountRecipientId, double amount, String description, LocalDateTime date, EStatus status) {
        this.accountOwnerId = accountOwnerId;
        this.accountRecipientId = accountRecipientId;
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.status = status;
    }
}
