package org.example.models.lists;

import org.example.exceptions.AlreadyRegisteredException;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.*;

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
            System.out.println("Usuario creado exitosamente.");
        }
    }

    @Override
    public void read() {
        Scanner scanner = new Scanner(System.in);
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        int option;
        do {
            clientReadMenu();
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
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente nuevamente.");
            }
        } while (option != 0);
    }

    @Override
    public void update() {
        //todo: menu para elegir que campo actualizar.
    }

    @Override
    public void delete() {
        //todo: menu para elegir que campo dar de baja.
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
                    name = scanner.nextLine();
                    if (!validateField(name, NAME_OR_LAST_NAME_REGEX)) {
                        name = null;
                        throw new InvalidFieldException("Nombre inválido. Recuerde, solo se permiten letras y espacios.");
                    }
                }

                if (lastName == null) {
                    System.out.println("Ingrese su apellido:");
                    lastName = scanner.nextLine();
                    if (!validateField(lastName, NAME_OR_LAST_NAME_REGEX)) {
                        lastName = null;
                        throw new InvalidFieldException("Apellido inválido. Recuerde, solo se permiten letras y espacios.");
                    }
                }

                if (email == null) {
                    System.out.println("Ingrese su email:");
                    email = scanner.nextLine();
                    if (!validateField(email, EMAIL_REGEX)) {
                        email = null;
                        throw new InvalidFieldException("Email inválido. Recuerde, debe tener el formato 'email123@email.com'.");
                    }
                    if(!isEmailUnique(email, this.getList())) {
                        email = null;
                        throw new AlreadyRegisteredException("El email ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if(password == null) {
                    System.out.println("Ingrese una contraseña:");
                    password = scanner.nextLine();
                    if (!validateField(password, PASSWORD_REGEX)) {
                        password = null;
                        throw new InvalidFieldException("Contraseña inválida. Recuerde, debe tener al menos 12 caracteres, una mayúscula, una minúscula y un número.");
                    }
                }

                if (dni == null) {
                    System.out.println("Ingrese su DNI:");
                    dni = scanner.nextLine();
                    if (!validateField(dni, DNI_REGEX)) {
                        dni = null;
                        throw new InvalidFieldException("DNI inválido. Recuerde, debe tener 8 dígitos.");
                    }
                    if(!isDniUnique(dni, this.getList())) {
                        dni = null;
                        throw new AlreadyRegisteredException("El DNI ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (phone == null) {
                    System.out.println("Ingrese su telefono:");
                    phone = scanner.nextLine();
                    if (!validateField(phone, PHONE_REGEX)) {
                        phone = null;
                        throw new InvalidFieldException("Telefono inválido. Recuerde, debe tener 10 dígitos.");
                    }
                    if(!isPhoneUnique(phone, this.getList())) {
                        phone = null;
                        throw new AlreadyRegisteredException("El telefono ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (address == null) {
                    address = new Address().createAddress();
                    if(address == null) {
                        throw new InvalidFieldException("Dirección inválida. Por favor, intente nuevamente.");
                    }
                }

                System.out.println("Usuario creado exitosamente.");
                return new Client(name, lastName, email, password, dni, address, phone);

            } catch (InvalidFieldException | AlreadyRegisteredException e) {
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

    public static void readClient(Client client) {
        System.out.println("--------------Datos del usuario--------------");
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
        System.out.println("Seleccione la consulta que desea realizar:");
        System.out.println("1) Datos de usuario.");
        System.out.println("2) Datos de cuentas.");
        System.out.println("3) Datos de tarjetas.");
        System.out.println("4) Todos los anteriores.");
        System.out.println("5) Datos de transacciones enviadas.");
        System.out.println("6) Datos de transacciones recibidas.");
        System.out.println("7) Datos de todas mis transacciones.");
        System.out.println("0) Cancelar y salir.");
    }

}
