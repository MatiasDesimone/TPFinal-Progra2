package org.example.models;

import org.example.interfaces.IShow;

import java.util.UUID;

public class Admin extends Person implements IShow {
    private String adminId;

    public Admin() {
    }

    public Admin(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
        this.adminId = UUID.randomUUID().toString();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public void show() {
    }
}
