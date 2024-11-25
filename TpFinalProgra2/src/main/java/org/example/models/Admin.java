package org.example.models;

import java.util.UUID;

public class Admin extends Person {
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

    public static Admin getAdminByEmail(String email) {
        for (Admin admin : Bank.getInstance().getAdmins().getList()) {
            if (admin.getEmail().equals(email)) {
                return admin;
            }
        }
        return null;
    }

    public static Admin getAdminByEmailAndPassword(String email, String password) {
        return Bank.getInstance().getAdmins().getList().stream()
                .filter(admin -> admin.getEmail().equals(email) && admin.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "\n------------------------Administrador------------------------" +
                super.toString() +
                "\nID de Administrador: " + adminId +
                "\n------------------------------------------------------------";
    }
}