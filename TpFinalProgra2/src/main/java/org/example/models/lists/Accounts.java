package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Account;

import java.util.ArrayList;
import java.util.List;

public class Accounts extends GenericList<Account> implements ICRUD {

    public Accounts() {
        super();
    }

    public Accounts(int size) {
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
