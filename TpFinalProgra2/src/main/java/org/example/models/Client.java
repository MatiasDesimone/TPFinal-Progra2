package org.example.models;

import org.example.enums.EAccountType;
import org.example.models.lists.Accounts;
import org.example.models.lists.Cards;
import org.example.models.lists.Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client extends Person {
    private String clientId;
    private String dni;
    private Address address;
    private String phone;
    private boolean active;
    private Accounts accounts;
    private Cards cards;
    private Transactions transactions;
    private List<String> incomingTransactionsIDs;

    public Client() {
    }

    public Client(String name, String lastName, String email, String password, String dni, Address address, String phone) {
        super(name, lastName, email, password);
        this.clientId = UUID.randomUUID().toString();
        this.dni = dni;
        this.address = address;
        this.phone = phone;
        this.active = true;
        this.accounts = new Accounts();
        this.cards = new Cards();
        this.transactions = new Transactions();
        this.incomingTransactionsIDs = new ArrayList<>();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public Cards getCards() {
        return cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public List<String> getReceivedTransfersIDs() {
        return incomingTransactionsIDs;
    }

    public void setReceivedTransfersIDs(List<String> receivedTransfersIDs) {
        this.incomingTransactionsIDs = receivedTransfersIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;
        return dni.equals(client.dni) && phone.equals(client.phone) && getEmail().equals(client.getEmail());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dni.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }

    public Account getAccountByType(EAccountType accountType) {
        Client loggedClient = Bank.getInstance().getLoggedInClient();
        return loggedClient.getAccounts().getList().stream()
                .filter(account -> account.getAccountType() == accountType)
                .findFirst()
                .orElse(null);
    }

    public Account getAccountByAlias(String alias) {
        return accounts.getList().stream()
                .filter(account -> account.getAlias().equals(alias))
                .findFirst().orElse(null);
    }

    public Account getAccountByCBU(String cbu) {
        return accounts.getList().stream().filter(account -> account.getCbu().equals(cbu)).findFirst().orElse(null);
    }

    public static Client getClientByID(String id) {
        for (Client client : Bank.getInstance().getClients().getList()) {
            if (client.getClientId().equals(id)) {
                return client;
            }
        }
        return null;
    }

    public static Client getClientByEmail(String email) {
        for (Client client : Bank.getInstance().getClients().getList()) {
            if (client.getEmail().equals(email)) {
                return client;
            }
        }
        return null;
    }

    public static Client getClientByEmailAndPassword(String email, String password) {
        for (Client client : Bank.getInstance().getClients().getList()) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "\n------------------------Cliente------------------------" +
                super.toString() +
                address.toString() +
                "\nID de cliente: " + clientId +
                "\nDNI de cliente: " + dni +
                "\nTelefono: " + phone +
                "\nActivo: " + active +
                accounts.toString() +
                cards.toString() +
                "\n------------------------------------------------------";
    }
}
