package org.example.models.lists;

import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Address;
import org.example.models.Client;

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
        add(client);
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

    private Client createClient() {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        String lastName = null;
        String email = null;
        String password = null;
        String dni = null;
        String phone = null;
        Address address = null;

        while (true) {
            try {
                if (name == null) {
                    System.out.println("Ingrese el nombre del cliente:");
                    name = scanner.nextLine();
                    if (!validateField(name, nameOrLastNameRegex)) {
                        name = null;
                        throw new InvalidFieldException("Nombre invalido. Por favor, intente nuevamente.");
                    }
                }

                if (lastName == null) {
                    System.out.println("Ingrese el apellido del cliente:");
                    lastName = scanner.nextLine();
                    if (!validateField(lastName, nameOrLastNameRegex)) {
                        lastName = null;
                        throw new InvalidFieldException("Apellido invalido. Por favor, intente nuevamente.");
                    }
                }

                if (email == null) {
                    System.out.println("Ingrese el email del cliente:");
                    email = scanner.nextLine();
                    if (!validateField(email, emailRegex)) {
                        email = null;
                        throw new InvalidFieldException("Email invalido. Por favor, intente nuevamente.");
                    }
                    if(!isEmailUnique(email, this.getList())) {
                        email = null;
                        throw new InvalidFieldException("El email ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if(password == null) {
                    System.out.println("Ingrese la contraseña del cliente:");
                    password = scanner.nextLine();
                    if (!validateField(password, passwordRegex)) {
                        password = null;
                        throw new InvalidFieldException("Contraseña invalida. Por favor, intente nuevamente.");
                    }
                }

                if (dni == null) {
                    System.out.println("Ingrese el DNI del cliente:");
                    dni = scanner.nextLine();
                    if (!validateField(dni, dniRegex)) {
                        dni = null;
                        throw new InvalidFieldException("DNI invalido. Por favor, intente nuevamente.");
                    }
                    if(!isDniUnique(dni, this.getList())) {
                        dni = null;
                        throw new InvalidFieldException("El DNI ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (phone == null) {
                    System.out.println("Ingrese el telefono del cliente:");
                    phone = scanner.nextLine();
                    if (!validateField(phone, phoneRegex)) {
                        phone = null;
                        throw new InvalidFieldException("Telefono invalido. Por favor, intente nuevamente.");
                    }
                    if(!isPhoneUnique(phone, this.getList())) {
                        phone = null;
                        throw new InvalidFieldException("El telefono ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (address == null) {
                    address = new Address().create();
                }

                return new Client(name, lastName, email, password, dni, address, phone);

            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
