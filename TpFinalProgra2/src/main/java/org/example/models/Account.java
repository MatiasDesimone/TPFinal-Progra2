package org.example.models;

import org.example.enums.EAccountType;

import java.util.UUID;

public class Account {
    private String clientId;
    private String accountId;
    private EAccountType accountType;
    private double balance;
    private double maintenanceFee;
    private boolean active;

    public Account() {
    }

    public Account(String clientId, EAccountType accountType, double balance, double maintenanceFee) {
        this.clientId = clientId;
        this.accountId = UUID.randomUUID().toString();
        this.accountType = accountType;
        this.balance = balance;
        this.maintenanceFee = maintenanceFee;
        this.active = true;
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
}
