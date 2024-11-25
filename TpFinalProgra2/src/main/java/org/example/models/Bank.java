package org.example.models;

import org.example.models.lists.Admins;
import org.example.models.lists.Clients;

import java.util.*;

import static org.example.models.Admin.getAdminByEmailAndPassword;
import static org.example.models.Client.getClientByEmailAndPassword;

public class Bank {
    private String name;
    private Address address;
    private String phone;
    private String email;
    private Clients clients;
    private Admins admins;
    private static Bank instance;
    private Client loggedInClient;
    private Admin loggedAdmin;
    private Set<String> existingCardNumbers = new HashSet<>();
    private Set<String> existingAlias = new HashSet<>();
    private Set<String> existingCBU = new HashSet<>();
    private Map<String, List<Transaction>> deletedClientTransactions = new HashMap<>();


    private Bank() {
        this.name = "Industrial and Commercial Bank of Java";
        this.address = new Address("Av. Rivadavia", "1234", "CABA", "CABA", "Argentina", "C1405DJR");
        this.phone = "0800-333-1234";
        this.email = "icbj@bank.com";
        clients = new Clients();
        admins = new Admins();
    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void setDeletedClientTransactions(Map<String, List<Transaction>> deletedClientTransactions) {
        this.deletedClientTransactions = deletedClientTransactions;
    }

    public Map<String, List<Transaction>> getDeletedClientTransactions() {
        return deletedClientTransactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Admins getAdmins() {
        return admins;
    }

    public void setAdmins(Admins admins) {
        this.admins = admins;
    }

    public static void setInstance(Bank instance) {
        Bank.instance = instance;
    }

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public void setLoggedInClient(Client loggedInClient) {
        this.loggedInClient = loggedInClient;
    }

    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    public Set<String> getExistingCardNumbers() {
        return existingCardNumbers;
    }

    public void setExistingCardNumbers(Set<String> existingCardNumbers) {
        this.existingCardNumbers = existingCardNumbers;
    }

    public Set<String> getExistingAlias() {
        return existingAlias;
    }

    public void setExistingAlias(Set<String> existingAlias) {
        this.existingAlias = existingAlias;
    }

    public Set<String> getExistingCBU() {
        return existingCBU;
    }

    public void setExistingCBU(Set<String> existingCBU) {
        this.existingCBU = existingCBU;
    }


    public int login(String email, String password) {
        Admin admin = getAdminByEmailAndPassword(email, password);
        if (admin != null) {
            instance.loggedAdmin = admin;
            loggedAdmin = instance.loggedAdmin;
            System.out.println("Administrador logueado exitosamente.");
            return 1;
        }

        Client client = getClientByEmailAndPassword(email, password);
        if (client != null) {
            if (!client.isActive()) {
                System.out.println("El cliente no está autorizado para loguearse. Por favor, acerquese al banco.");
                return 0;
            }
            instance.loggedInClient = client;
            loggedInClient = instance.loggedInClient;
            System.out.println("Cliente logueado exitosamente.");
            return 2;
        } else {
            System.out.println("Usuario no encontrado o contraseña incorrecta.");
        }
        return 0;
    }

    public void logout() {
        if (this.loggedInClient != null) {
            this.loggedInClient = null;
            instance.loggedInClient = null;
            System.out.println("Cliente deslogueado exitosamente.");
        } else if (loggedAdmin != null) {
            this.loggedAdmin = null;
            instance.loggedAdmin = null;
            System.out.println("Administrador deslogueado exitosamente.");
        } else {
            System.out.println("No hay ningún usuario logueado.");
        }
    }
}
