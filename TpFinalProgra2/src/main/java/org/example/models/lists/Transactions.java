package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Transaction;

public class Transactions extends GenericList<Transaction> implements ICRUD {

    public Transactions() {
        super();
    }

    public Transactions(int size) {
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
