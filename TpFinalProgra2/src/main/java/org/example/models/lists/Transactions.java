package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.Bank;
import org.example.models.Client;
import org.example.models.Transaction;

import java.util.Scanner;

import static org.example.utils.ValidationUtils.getLoggedInClient;

public class Transactions extends GenericList<Transaction> implements ICRUD {

    public Transactions() {
        super();
    }

    public Transactions(int size) {
        super(size);
    }

    @Override
    public void create() {
        Transaction transaction = createTransaction();
        Bank.getInstance().getLoggedInClient().getTransactions().add(transaction);

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

    private Transaction createTransaction(){
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String clientId = loggedClient.getClientId();
        String transactionId = null;
        String accountId = null;
        double amount = 0;
        String description = null;
        return null;
    }
}
