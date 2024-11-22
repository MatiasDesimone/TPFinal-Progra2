package org.example.models;

import org.example.models.lists.Admins;
import org.example.models.lists.Clients;

import java.util.HashSet;
import java.util.Set;

public class Bank {
    private String name;
    private Address address;
    private String phone;
    private String email;
    private Clients clients;
    private Admins admins;
    private static Bank instance;
    private Client loggedInClient;
    private Set<String> existingCardNumbers = new HashSet<>();
    private Set<String> existingAlias = new HashSet<>();
    private Set<String> existingCBU = new HashSet<>();


    public Bank() {
        this.clients = new Clients();
        this.admins = new Admins();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Admins getAdmins() {
        return admins;
    }

    public void setAdmins(Admins admins) {
        this.admins = admins;
    }

    public static void setInstance(Bank instance) {
        Bank.instance = instance;
    }

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public void setLoggedInClient(Client loggedInClient) {
        this.loggedInClient = loggedInClient;
    }

    public Set<String> getExistingCardNumbers() {
        return existingCardNumbers;
    }

    public void setExistingCardNumbers(Set<String> existingCardNumbers) {
        this.existingCardNumbers = existingCardNumbers;
    }

    public Set<String> getExistingAlias() {
        return existingAlias;
    }

    public void setExistingAlias(Set<String> existingAlias) {
        this.existingAlias = existingAlias;
    }

    public Set<String> getExistingCBU() {
        return existingCBU;
    }

    public void setExistingCBU(Set<String> existingCBU) {
        this.existingCBU = existingCBU;
    }
}
