package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Card;

public class Cards extends GenericList<Card> implements ICRUD {

    public Cards() {
        super();
    }

    public Cards(int size) {
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
