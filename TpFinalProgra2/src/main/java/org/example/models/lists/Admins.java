package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Admin;
import org.example.models.Bank;

public class Admins extends GenericList<Admin> implements ICRUD {

    public Admins() {
        super();
    }

    public Admins(int size) {
        super(size);
    }

    @Override
    public void create() {
        Admin admin = new Admin("Nombre", "Apellido", "admin123@gmail.com", "admin123");
        Bank.getInstance().getAdmins().add(admin);
    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
