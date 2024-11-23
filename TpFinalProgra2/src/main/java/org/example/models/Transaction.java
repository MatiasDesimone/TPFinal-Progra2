package org.example.models;

import org.example.enums.EStatus;
import org.example.enums.ETransactionDescription;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Transaction {
    private String accountId;
    private String ownerCBU;
    private String recipientCBU;
    private String transactionId;
    private double amount;
    private ETransactionDescription description;
    private LocalDateTime date;
    private EStatus status;

    public Transaction() {
    }

    public Transaction(String accountId, String ownerCBU, String recipientCBU, double amount, ETransactionDescription description, EStatus status) {
        this.accountId = accountId;
        this.ownerCBU = ownerCBU;
        this.recipientCBU = recipientCBU;
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOwnerCBU() {
        return ownerCBU;
    }

    public void setOwnerCBU(String ownerCBU) {
        this.ownerCBU = ownerCBU;
    }

    public String getRecipientCBU() {
        return recipientCBU;
    }

    public void setRecipientCBU(String recipientCBU) {
        this.recipientCBU = recipientCBU;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ETransactionDescription getDescription() {
        return description;
    }

    public void setDescription(ETransactionDescription description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public static EStatus statusRandomGenerator(){
        Random random = new Random();
        int randomNumber = random.nextInt(30) + 1;

        if (randomNumber >= 1 && randomNumber <= 25) {
            return EStatus.APPROVED;
        } else {
            return EStatus.REJECTED;
        }
    }

    @Override
    public String toString() {
        return "\n------------------------Transacción------------------------" +
                "\nID de transacción: " + transactionId +
                "\nID de cuenta: " + accountId +
                "\nCBU del propietario: " + ownerCBU +
                "\nCBU del destinatario: " + recipientCBU +
                "\nMonto: $" + amount +
                "\nDescripción: " + description.getDescription() +
                "\nFecha: " + date +
                "\nEstado: " + status.getDescription() +
                "\n------------------------------------------------------------";
    }
}
