package org.example.models.lists;

import org.example.exceptions.AlreadyRegisteredException;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.*;
import org.example.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.utils.ValidationUtils.*;

public class Clients extends GenericList<Client> implements ICRUD {

    public Clients() {
        super();
    }

    public Clients(int size) {
        super(size);
    }

    @Override
    public void create() {
        Client client = createClient();
        if(client != null){
            Bank.getInstance().getClients().add(client);
        }
    }

    @Override
    public void read() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        int option;
        while (true) {
            clientReadMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        readClient(client);
                        break;
                    case 2:
                        Accounts.readAccount(client);
                        break;
                    case 3:
                        Cards.readCards(client);
                        break;
                    case 4:
                        readClientTotal(client);
                        break;
                    case 5:
                        Transactions.readOutTransactions(client);
                        break;
                    case 6:
                        Transactions.readInTransactions(client);
                        break;
                    case 7:
                        Transactions allTransactions = new Transactions();
                        allTransactions.read();
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
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        int mainOption;
        while (true) {
            clientUpdateMenu();
            try {
                mainOption = scanner.nextInt();
                scanner.nextLine();
                switch (mainOption) {
                    case 1:
                        updatePersonalData();
                        break;
                    case 2:
                        Accounts accounts = new Accounts();
                        accounts.update();
                        break;
                    case 3:
                        delete();
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
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        int option;
        while (true) {
            clientDeleteMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        deleteClient();
                        if (Bank.getInstance().getLoggedInClient() == null) {
                            saveDataAndExit();
                        }
                        break;
                    case 2:
                        Accounts accounts = new Accounts();
                        accounts.delete();
                        break;
                    case 3:
                        Cards cards = new Cards();
                        cards.delete();
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

    private void clientDeleteMenu() {
        System.out.println("Seleccione la baja que desea realizar:");
        System.out.println("1) Dar de baja el usuario.");
        System.out.println("2) Dar de baja una cuenta.");
        System.out.println("3) Dar de baja una tarjeta.");
        System.out.println("0) Cancelar y salir.");
    }

    private void deleteClient() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = Bank.getInstance().getLoggedInClient();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese su contraseña para continuar: ");
                String password = scanner.nextLine();
                Client client = Client.getClientByEmailAndPassword(loggedClient.getEmail(), password);
                if (client == null) {
                    throw new InvalidFieldException("Contraseña incorrecta. Por favor, intente nuevamente.");
                }

                System.out.println("Está seguro que desea eliminar su cuenta?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    System.out.println("Muchas gracias por habernos dejado acompañarlo este tiempo. Esperamos volver a verlo.");
                    Thread.sleep(3000);
                    Bank.getInstance().getDeletedClientTransactions().put(loggedClient.getClientId(), loggedClient.getTransactions().getList());
                    Bank.getInstance().logout();
                    Bank.getInstance().getClients().remove(loggedClient);
                    System.out.println("Usuario eliminado exitosamente.");
                    return;
                } else {
                    System.out.println("¡Qué bueno que se queda con nosotros!");
                    return;
                }
            } catch (InvalidFieldException | InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    private static void clientUpdateMenu() {
        System.out.println("\nSeleccione la operación que desea realizar:");
        System.out.println("1) Actualizar datos personales.");
        System.out.println("2) Mover saldo de una cuenta a otra.");
        System.out.println("3) Solicitar una BAJA.");
        System.out.println("0) Cancelar y salir.");
    }

    private Client createClient() {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        String lastName = null;
        String email = null;
        String password = null;
        String dni = null;
        String phone = null;
        Address address = null;
        int aux = 0;

        while (true) {
            if (breakPoint(aux)) return null;
            try {
                if (name == null) {
                    System.out.println("Ingrese su nombre:");
                    System.out.println("Recuerde, solo se permiten letras y espacios.");
                    name = scanner.nextLine();
                    if (!validateField(name, NAME_OR_LAST_NAME_REGEX)) {
                        name = null;
                        throw new InvalidFieldException("Nombre inválido. Por favor, intente nuevamente.");
                    }
                }

                if (lastName == null) {
                    System.out.println("Ingrese su apellido:");
                    System.out.println("Recuerde, solo se permiten letras y espacios.");
                    lastName = scanner.nextLine();
                    if (!validateField(lastName, NAME_OR_LAST_NAME_REGEX)) {
                        lastName = null;
                        throw new InvalidFieldException("Apellido inválido. Por favor, intente nuevamente.");
                    }
                }

                if (email == null) {
                    System.out.println("Ingrese su email:");
                    System.out.println("Recuerda, debe tener el formato 'ejemplo.ejemplo@ejemplo.com'.");
                    email = scanner.nextLine();
                    if (!validateField(email, EMAIL_REGEX)) {
                        email = null;
                        throw new InvalidFieldException("Email inválido. Por favor, intente nuevamente.");
                    }
                    if(!isEmailUnique(email, Bank.getInstance().getClients().getList())) {
                        email = null;
                        throw new AlreadyRegisteredException("El email ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if(password == null) {
                    System.out.println("Ingrese una contraseña:");
                    System.out.println("Recuerde, debe tener al menos 12 caracteres, una mayúscula, una minúscula y un número.");
                    password = scanner.nextLine();
                    if (!validateField(password, PASSWORD_REGEX)) {
                        password = null;
                        throw new InvalidFieldException("Contraseña inválida. Por favor, intente nuevamente.");
                    }
                }

                if (dni == null) {
                    System.out.println("Ingrese su DNI:");
                    System.out.println("Recuerde, deben ser solo números (entre 7 y 9 digitos).");
                    dni = scanner.nextLine();
                    if (!validateField(dni, DNI_REGEX)) {
                        dni = null;
                        throw new InvalidFieldException("DNI inválido. Por favor, intente nuevamente.");
                    }
                    if(!isDniUnique(dni, Bank.getInstance().getClients().getList())) {
                        dni = null;
                        throw new AlreadyRegisteredException("El DNI ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (phone == null) {
                    System.out.println("Ingrese su telefono:");
                    System.out.println("Recuerde, deben ser solo números (10 digitos).");
                    phone = scanner.nextLine();
                    if (!validateField(phone, PHONE_REGEX)) {
                        phone = null;
                        throw new InvalidFieldException("Telefono inválido. Por favor, intente nuevamente.");
                    }
                    if(!isPhoneUnique(phone, Bank.getInstance().getClients().getList())) {
                        phone = null;
                        throw new AlreadyRegisteredException("El telefono ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (address == null) {
                    System.out.println("Ingrese su domicilio:");
                    address = new Address().createAddress();
                    if(address == null) {
                        throw new InvalidFieldException("Dirección inválida. Por favor, intente nuevamente.");
                    }
                }

                System.out.println("Cliente registrado exitosamente.");
                return new Client(name, lastName, email, password, dni, address, phone);
            } catch (InvalidFieldException | AlreadyRegisteredException e) {
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

    public static void readClient(Client client) {
        System.out.println("---------------------------Datos del usuario---------------------------");
        System.out.println("Nombre: " + client.getName());
        System.out.println("Apellido: " + client.getLastName());
        System.out.println("Email: " + client.getEmail());
        System.out.println("DNI: " + client.getDni());
        System.out.println("Telefono: " + client.getPhone());
        System.out.println(client.getAddress().toString());
    }

    public static void readClientTotal(Client client) {
        readClient(client);
        Accounts.readAccount(client);
        Cards.readCards(client);
    }

    private static void clientReadMenu() {
        System.out.println("\nSeleccione la consulta que desea realizar:");
        System.out.println("1) Datos de usuario.");
        System.out.println("2) Datos de cuentas.");
        System.out.println("3) Datos de tarjetas.");
        System.out.println("4) Todos los anteriores.");
        System.out.println("5) Datos de transacciones enviadas.");
        System.out.println("6) Datos de transacciones recibidas.");
        System.out.println("7) Datos de todas mis transacciones.");
        System.out.println("0) Cancelar y salir.");
    }

    private void updatePersonalData() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        int option;
        while (true) {
            clientPersonalUpdateMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        updateClientName();
                        break;
                    case 2:
                        updateClientLastName();
                        break;
                    case 3:
                        updateClientEmail();
                        break;
                    case 4:
                        updateClientPassword();
                        break;
                    case 5:
                        updateClientPhone();
                        break;
                    case 6:
                        updateClientDNI();
                        break;
                    case 7:
                        updateClientAddress();
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

    private void updateClientDNI() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el nuevo número de DNI: ");
                String newDNI = scanner.nextLine();
                if (!validateField(newDNI, DNI_REGEX)) {
                    throw new InvalidFieldException("DNI inválido. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su DNI a " + newDNI + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    client.setDni(newDNI);
                    System.out.println("DNI actualizado exitosamente.");
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

    private void updateClientAddress() {Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        System.out.println("Ingrese la nueva dirección:");
        Address address = new Address().createAddress();
        if(address != null){
            System.out.println("Está seguro que desea cambiar su domicilio a la siguiente dirección?");
            System.out.println(address.toString());
            System.out.println("1) Sí.");
            System.out.println("2) No.");
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option == 1) {
                client.setAddress(address);
                System.out.println("Dirección actualizada exitosamente.");
            } else {
                System.out.println("Operación cancelada.");
            }
        }else {
            System.out.println("Operación cancelada.");
        }
    }

    private void updateClientPhone() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.print("Ingrese el nuevo número telefonico: ");
                String newPhone = scanner.nextLine();
                if (!validateField(newPhone, PHONE_REGEX)) {
                    throw new InvalidFieldException("Número telefonico inválido. Por favor, intente nuevamente.");
                }
                System.out.println("Está seguro que desea cambiar su número telefonico a " + newPhone + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    client.setPhone(newPhone);
                    System.out.println("Número telefonico actualizado exitosamente.");
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

    private void updateClientPassword() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
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
                    client.setPassword(newPassword);
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

    private void updateClientEmail() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
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
                    client.setEmail(newEmail);
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

    private void updateClientLastName() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
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
                    client.setLastName(newLastName);
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

    private void updateClientName() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                System.out.println("Ingrese su nuevo nombre: ");
                String newName = scanner.nextLine();
                if (!validateField(newName, NAME_OR_LAST_NAME_REGEX)) {
                    throw new InvalidFieldException("Nombre inválido. Recuerde, solo se permiten letras y espacios.");
                }
                System.out.println("Está seguro que desea actualizar su nombre a " + newName + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    client.setName(newName);
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

    private void clientPersonalUpdateMenu() {
        System.out.println("\nSeleccione el campo que desea actualizar:");
        System.out.println("1) Nombre.");
        System.out.println("2) Apellido.");
        System.out.println("3) Email.");
        System.out.println("4) Contraseña.");
        System.out.println("5) Telefono.");
        System.out.println("6) DNI.");
        System.out.println("7) Dirección.");
        System.out.println("0) Cancelar y salir.");
    }

    private void saveDataAndExit() {
        try {
            JsonUtils.toJsonFile(new File("bank_data.json"), Bank.getInstance());
            System.out.println("Saliendo del sistema...");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
        System.exit(0);
    }

    public Account getAccountByCBU(String cbu) {
        return getList().stream().filter(client -> client.getAccounts().getList().stream().anyMatch(account -> account.getCbu().equals(cbu)))
                .findFirst().orElse(null).getAccounts().getList().stream().filter(account -> account.getCbu().equals(cbu)).findFirst().orElse(null);
    }

    public Account getAccountByAlias(String alias) {
        return getList().stream().filter(client -> client.getAccounts().getList().stream().anyMatch(account -> account.getAlias().equals(alias)))
                .findFirst().orElse(null).getAccounts().getList().stream().filter(account -> account.getAlias().equals(alias)).findFirst().orElse(null);
    }
}
