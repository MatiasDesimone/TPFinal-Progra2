package org.example.models.lists;

import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.NotFoundException;
import org.example.interfaces.ICRUD;
import org.example.models.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.example.models.Client.getClientByID;
import static org.example.models.lists.Clients.readClientTotal;
import static org.example.utils.ValidationUtils.*;

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
        System.out.println("Administrador creado exitosamente.");
        Bank.getInstance().getAdmins().add(admin);
    }

    @Override
    public void read() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        if (adminCheck(admin)) return;
        int option;
        while (true) {
            adminReadMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.println(admin.toString());
                        break;
                    case 2:
                        System.out.print("Ingrese el ID del usuario: ");
                        String clientId = scanner.nextLine();
                        if(clientId.isEmpty()){
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
                        getClientTransactions();
                        break;
                    case 7:
                        getDeletedClientTransactions();
                        break;
                    case 8:
                        readAllTransactions();
                        break;
                    case 0:
                        System.out.println("Operación cancelada. Saliendo...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        if (adminCheck(admin)) return;
        int mainOption;
        while (true) {
            adminUpdateMenu();
            try {
                mainOption = scanner.nextInt();
                scanner.nextLine();
                switch (mainOption) {
                    case 1:
                        updatePersonalData();
                        break;
                    case 2:
                        updateClientStatus();
                        break;
                    case 3:
                        updateAccountStatus();
                        break;
                    case 4:
                        updateCardStatus();
                        break;
                    case 0:
                        System.out.println("Operación cancelada. Saliendo...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        if (adminCheck(admin)) return;
        int option;
        while (true) {
            deleteMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("¡No puedes borrarte a ti mismo! ¿Quién hará el trabajo sucio?");
                        break;
                    case 2:
                        deleteClient();
                        break;
                    case 3:
                        deleteAccount();
                        break;
                    case 4:
                        deleteCard();
                        break;
                    case 0:
                        System.out.println("Operación cancelada. Saliendo...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private static void deleteMenu() {
        System.out.println("Seleccione la entidad que desea eliminar:");
        System.out.println("1) Borrarme a mí mismo.");
        System.out.println("2) Cliente.");
        System.out.println("3) Cuenta.");
        System.out.println("4) Tarjeta.");
        System.out.println("0) Cancelar y salir.");
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
        System.out.println("Lista de todos los clientes del banco con sus respectivos datos: ");
        if(Bank.getInstance().getClients().getList().isEmpty()){
            System.out.println("\nNo hay clientes registrados.\n\n");
            return;
        }
        for (Client client : Bank.getInstance().getClients().getList()) {
            readClientTotal(client);
        }
    }

    private static void readAllAccounts() {
        System.out.println("Lista de todas las cuentas del banco con sus respectivos datos: ");
        if(Bank.getInstance().getClients().getList().isEmpty()){
            System.out.println("\nNo hay cuentas registradas.\n\n");
            return;
        }
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Cuentas del cliente: " + client.getClientId());
            for (Account account : client.getAccounts().getList()) {
                System.out.println(account.toString());
            }
        }
    }

    private static void readAllTransactions() {
        System.out.println("Lista de todas las transacciones del banco con sus respectivos datos: ");
        if(Bank.getInstance().getClients().getList().isEmpty()){
            System.out.println("\nNo hay transacciones registradas.\n\n");
            return;
        }
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Transacciones del cliente: " + client.getClientId());
            for (Transaction transaction : client.getTransactions().getList()) {
                System.out.println(transaction.toString());
            }
        }
    }

    private static void readAllCards() {
        System.out.println("Lista de todas las tarjetas del banco con sus respectivos datos: ");
        if(Bank.getInstance().getClients().getList().isEmpty()){
            System.out.println("\nNo hay tarjetas registradas.\n\n");
            return;
        }
        for (Client client : Bank.getInstance().getClients().getList()) {
            System.out.println("\n- Tarjetas del cliente: " + client.getClientId());
            for (Card card : client.getCards().getList()) {
                System.out.println(card.toString());
            }
        }
    }

    private static void adminReadMenu() {
        System.out.println("\nSeleccione la consulta que desea realizar:");
        System.out.println("1) Ver mis datos de administrador.");
        System.out.println("2) Ver datos completos de un usuario por ID.");
        System.out.println("3) Ver datos completos de todos los usuarios.");
        System.out.println("4) Ver todas las cuentas del banco.");
        System.out.println("5) Ver todas las tarjetas del banco.");
        System.out.println("6) Ver transacciones de un cliente.");
        System.out.println("7) Ver transacciones de un cliente eliminado.");
        System.out.println("8) Ver todas las transacciones realizadas.");
        System.out.println("0) Cancelar y salir.");
    }

    private void updateAdminName() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el nuevo nombre del administrador: ");
                String newName = scanner.nextLine();
                if (!validateField(newName, NAME_OR_LAST_NAME_REGEX)) {
                    throw new InvalidFieldException("Nombre inválido. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su nombre a " + newName + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    admin.setName(newName);
                    System.out.println("Nombre actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void updateAdminLastName() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el nuevo apellido: ");
                String newLastName = scanner.nextLine();
                if (!validateField(newLastName, NAME_OR_LAST_NAME_REGEX)) {
                    throw new InvalidFieldException("Apellido inválido. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su apellido a " + newLastName + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    admin.setLastName(newLastName);
                    System.out.println("Apellido actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void updateAdminEmail() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el nuevo email: ");
                String newEmail = scanner.nextLine();
                if (!validateField(newEmail, EMAIL_REGEX)) {
                    throw new InvalidFieldException("Email inválido. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su email a " + newEmail + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    admin.setEmail(newEmail);
                    System.out.println("Email actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void updateAdminPassword() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese la nueva contraseña: ");
                String newPassword = scanner.nextLine();
                if (!validateField(newPassword, PASSWORD_REGEX)) {
                    throw new InvalidFieldException("Contraseña inválida. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su contraseña a " + newPassword + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    admin.setPassword(newPassword);
                    System.out.println("Contraseña actualizada exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void adminPersonalUpdateMenu() {
        System.out.println("\nSeleccione el campo que desea actualizar:");
        System.out.println("1) Nombre.");
        System.out.println("2) Apellido.");
        System.out.println("3) Email.");
        System.out.println("4) Contraseña.");
        System.out.println("0) Cancelar y salir.");
    }

    private static void adminUpdateMenu() {
        System.out.println("\nSeleccione la opción que desea realizar:");
        System.out.println("1) Actualizar datos personales.");
        System.out.println("2) Actualizar el estado de un cliente.");
        System.out.println("3) Actualizar el estado de una cuenta.");
        System.out.println("4) Actualizar el estado de una tarjeta.");
        System.out.println("0) Cancelar y salir.");
    }

    private void updatePersonalData() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = Bank.getInstance().getLoggedAdmin();
        if(adminCheck(admin)) return;
        int option;
        while (true) {
            adminPersonalUpdateMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        updateAdminName();
                        break;
                    case 2:
                        updateAdminLastName();
                        break;
                    case 3:
                        updateAdminEmail();
                        break;
                    case 4:
                        updateAdminPassword();
                        break;
                    case 0:
                        System.out.println("Operación cancelada. Saliendo...");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private void updateClientStatus() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID del cliente: ");
                String clientId = scanner.nextLine();
                if (!validateField(clientId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Client clientToUpdate = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    if (client.getClientId().equals(clientId)) {
                        clientToUpdate = client;
                        break;
                    }
                }
                if (clientToUpdate == null) {
                    throw new NotFoundException("Cliente no encontrado.");
                }
                System.out.println("Está seguro que desea actualizar el estado del cliente con ID " + clientId + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    clientToUpdate.setActive(!clientToUpdate.isActive());
                    System.out.println("Estado del cliente actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void updateAccountStatus() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID de la cuenta: ");
                String accountId = scanner.nextLine();
                if (!validateField(accountId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Account accountToUpdate = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    for (Account account : client.getAccounts().getList()) {
                        if (account.getAccountId().equals(accountId)) {
                            accountToUpdate = account;
                            break;
                        }
                    }
                    if (accountToUpdate != null) break;
                }
                if (accountToUpdate == null) {
                    throw new NotFoundException("Cuenta no encontrada.");
                }
                System.out.println("Está seguro que desea actualizar el estado de la cuenta con ID " + accountId + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    accountToUpdate.setActive(!accountToUpdate.isActive());
                    System.out.println("Estado de la cuenta actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void updateCardStatus(){
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID de la tarjeta: ");
                String cardId = scanner.nextLine();
                if (!validateField(cardId, ID_REGEX)) {
                    throw new InvalidFieldException("ID de tarjeta inválido. Por favor, intente nuevamente.");
                }
                Card cardToUpdate = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    for (Card card : client.getCards().getList()) {
                        if (card.getCardId().equals(cardId)) {
                            cardToUpdate = card;
                            break;
                        }
                    }
                    if (cardToUpdate != null) break;
                }
                if (cardToUpdate == null) {
                    throw new NotFoundException("Tarjeta no encontrada.");
                }
                System.out.println("Actualmente la tarjeta se encuentra " + (cardToUpdate.isActive() ? "Activa." : "Inactiva."));
                if (checkIntentionDisableCard(cardToUpdate)) {
                    cardToUpdate.setActive(!cardToUpdate.isActive());
                    System.out.println("Estado de la tarjeta actualizado exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private static boolean checkIntentionDisableCard(Card cardToUpdate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Está seguro que desea actualizar el estado de la tarjeta?");
        System.out.println("1) Sí.");
        System.out.println("2) No.");
        int option = scanner.nextInt();
        scanner.nextLine();
        if(option == 1){
            return true;
        }else{
            return false;
        }
    }

    private void deleteClient() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID del cliente que desea eliminar: ");
                String clientId = scanner.nextLine();
                if (!validateField(clientId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Client clientToDelete = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    if (client.getClientId().equals(clientId)) {
                        clientToDelete = client;
                        break;
                    }
                }

                if (clientToDelete == null) {
                    throw new NotFoundException("Cliente no encontrado.");
                }
                System.out.println("Está seguro que desea eliminar al cliente con ID " + clientId + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    Bank.getInstance().getDeletedClientTransactions().put(clientId, clientToDelete.getTransactions().getList());
                    clientToDelete.getAccounts().getList().clear();
                    clientToDelete.getCards().getList().clear();
                    Bank.getInstance().getClients().remove(clientToDelete);
                    System.out.println("El cliente y todas sus cuentas y tarjetas se han eliminado exitosamente. \nEl historial de transacciones se ha guardado para futuras referencias.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void getDeletedClientTransactions() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID del cliente para ver sus transacciones: ");
                String clientId = scanner.nextLine();
                if (!validateField(clientId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                List<Transaction> transactions = Bank.getInstance().getDeletedClientTransactions().get(clientId);
                if (transactions == null || transactions.isEmpty()) {
                    throw new NotFoundException("No hay transacciones para este cliente o el cliente no ha sido eliminado.");
                } else {
                    System.out.println("Transacciones del cliente con ID " + clientId + ":");
                    for (Transaction transaction : transactions) {
                        System.out.println(transaction.toString());
                    }
                }
                break;
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void getClientTransactions(){
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID del cliente para ver sus transacciones: ");
                String clientId = scanner.nextLine();
                if (!validateField(clientId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Client client = getClientByID(clientId);
                if (client == null) {
                    throw new NotFoundException("Cliente no encontrado.");
                }
                System.out.println("Transacciones del cliente con ID " + clientId + ":");
                for (Transaction transaction : client.getTransactions().getList()) {
                    System.out.println(transaction.toString());
                }
                break;
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID de la cuenta que desea eliminar: ");
                String accountId = scanner.nextLine();
                if (!validateField(accountId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Account accountToDelete = null;
                Client accountOwner = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    for (Account account : client.getAccounts().getList()) {
                        if (account.getAccountId().equals(accountId)) {
                            accountToDelete = account;
                            accountOwner = client;
                            break;
                        }
                    }
                    if (accountToDelete != null) break;
                }
                if (accountToDelete == null) {
                    throw new NotFoundException("Cuenta no encontrada.");
                }
                System.out.println("Está seguro que desea eliminar la cuenta con ID " + accountId + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    accountOwner.getAccounts().remove(accountToDelete);
                    System.out.println("Cuenta eliminada exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private void deleteCard() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el ID de la tarjeta que desea eliminar: ");
                String cardId = scanner.nextLine();
                if (!validateField(cardId, ID_REGEX)) {
                    throw new InvalidFieldException("ID inválido. Por favor, intente nuevamente.");
                }
                Card cardToDelete = null;
                Client cardOwner = null;
                for (Client client : Bank.getInstance().getClients().getList()) {
                    for (Card card : client.getCards().getList()) {
                        if (card.getCardId().equals(cardId)) {
                            cardToDelete = card;
                            cardOwner = client;
                            break;
                        }
                    }
                    if (cardToDelete != null) break;
                }
                if (cardToDelete == null) {
                    throw new NotFoundException("Tarjeta no encontrada.");
                }
                System.out.println("Está seguro que desea eliminar la tarjeta con ID " + cardId + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    cardOwner.getCards().remove(cardToDelete);
                    System.out.println("Tarjeta eliminada exitosamente.");
                    break;
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            } catch (InvalidFieldException | NotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }
}

