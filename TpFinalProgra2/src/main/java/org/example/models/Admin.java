package org.example.models;

import org.example.interfaces.IShow;

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
        for (Admin admin : Bank.getInstance().getAdmins().getList()) {
            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "\n------------------------Administrador------------------------" +
                super.toString() +
                "ID de Administrador: " + adminId +
                "\n------------------------------------------------------------";
    }
}
