package org.example.models;

import org.example.models.lists.Admins;
import org.example.models.lists.Clients;

import java.util.List;

public class Bank {
    private String name;
    private Address address;
    private String phone;
    private String email;
    private Clients clients;
    private Admins admins;

    public Bank() {
        this.clients = new Clients();
        this.admins = new Admins();
    }


}
