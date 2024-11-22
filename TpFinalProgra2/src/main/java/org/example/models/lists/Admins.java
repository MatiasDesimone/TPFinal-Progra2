package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Admin;

public class Admins extends GenericList<Admin> implements ICRUD {

    public Admins() {
        super();
    }

    public Admins(int size) {
        super(size);
    }

    @Override
    public void create() {

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
