package org.example.models;

import org.example.enums.EAccountType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.example.utils.Mocks.getRandomAlias;
import static org.example.utils.ValidationUtils.BANK_IDENTIFIER;

public class Account {
    private String clientId;
    private String accountId;
    private EAccountType accountType;
    private double balance;
    private double maintenanceFee;
    private boolean active;
    private LocalDateTime createdAt;
    private String alias;
    private String cbu;


    public Account() {
    }

    public Account(String clientId, EAccountType accountType, double balance) {
        this.clientId = clientId;
        this.accountId = UUID.randomUUID().toString();
        this.accountType = accountType;
        this.balance = balance;
        this.maintenanceFee = accountType.getMaintenanceFee();
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.alias = generateUniqueAlias();
        this.cbu = generateUniqueCBU();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public EAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(EAccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String generateUniqueAlias(){
        String alias;
        do{
            alias = generateRandomAlias();
        }while (Bank.getInstance().getExistingAlias().contains(alias));
        Bank.getInstance().getExistingAlias().add(alias);
        return alias;
    }

    private static String generateRandomAlias(){
        StringBuilder alias = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                alias.append(".");
            }
            alias.append(getRandomAlias());
        }
        return alias.toString();
    }

    public String generateRandomCBU(){
        StringBuilder cbu = new StringBuilder();
        cbu.append(BANK_IDENTIFIER);
        for (int i = 0; i < 16; i++) {
            cbu.append((int) (Math.random() * 10));
        }
        return cbu.toString();
    }

    public String generateUniqueCBU(){
        String cbu;
        do{
            cbu = generateRandomCBU();
        }while (Bank.getInstance().getExistingCBU().contains(cbu));
        Bank.getInstance().getExistingCBU().add(cbu);
        return cbu;
    }


}
