package org.example.models.lists;

import org.example.interfaces.ICRUD;
import org.example.models.*;

import java.util.Scanner;

import static org.example.models.Client.getClientByID;
import static org.example.models.lists.Clients.readClientTotal;

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
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        if (admin == null) {
            System.out.println("Administrador no encontrado. Saliendo...");
            return;
        }
        adminReadMenu();
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println(admin.toString());
                break;
            case 2:
                System.out.print("Ingrese el ID del usuario: ");
                String clientId = scanner.nextLine();
                if(clientId.equals("")){
                    System.out.println("ID inválido. Saliendo...");
                    break;
                }
                readClientByID(clientId);
                break;
            case 3:
                readAllClients();
                break;
            case 4:
                readAllAccounts();
                break;
            case 5:
                readAllCards();
                break;
            case 6:
                readAllTransactions();
                break;
            case 0:
                System.out.println("Operación cancelada. Saliendo...");
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente nuevamente.");
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    private static void readClientByID(String clientId) {
        Client client = getClientByID(clientId);
        if (client != null) {
            readClientTotal(client);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void readAllClients() {
        System.out.println("Lista de todos los clientes del banco con sus respectivos datos: (Espero al administrador le guste leer...)");
        for (Client client : Bank.getInstance().getClients().getList()) {
            readClientTotal(client);
        }
    }

    private static void readAllAdmins() {
        System.out.println("Lista de todos los administradores del banco con sus respectivos datos: ");
        for (Admin admin : Bank.getInstance().getAdmins().getList()) {
            System.out.println(admin.toString());
        }
        System.out.println("Con estos debería ser suficiente...");
    }

    private static void readAllAccounts() {
        System.out.println("Lista de todas las cuentas del banco con sus respectivos datos: ");
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Cuentas del cliente: " + client.getClientId());
            for (Account account : client.getAccounts().getList()) {
                System.out.println(account.toString());
            }
        }
    }

    private static void readAllTransactions() {
        System.out.println("Lista de todas las transacciones del banco con sus respectivos datos: ");
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Transacciones del cliente: " + client.getClientId());
            for (Transaction transaction : client.getTransactions().getList()) {
                System.out.println(transaction.toString());
            }
        }
    }

    private static void readAllCards() {
        System.out.println("Lista de todas las tarjetas del banco con sus respectivos datos: ");
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Tarjetas del cliente: " + client.getClientId());
            for (Card card : client.getCards().getList()) {
                System.out.println(card.toString());
            }
        }
    }

    private static void adminReadMenu() {
        System.out.println("Seleccione la consulta que desea realizar:");
        System.out.println("1) Ver mis datos de administrador");
        System.out.println("2) Ver datos completos de un usuario por ID");
        System.out.println("3) Ver datos completos de todos los usuarios");
        System.out.println("4) Ver todas las cuentas del banco");
        System.out.println("5) Ver todas las tarjetas del banco");
        System.out.println("6) Ver todas las transacciones realizadas");
        System.out.println("0) Cancelar y salir");
    }
}
