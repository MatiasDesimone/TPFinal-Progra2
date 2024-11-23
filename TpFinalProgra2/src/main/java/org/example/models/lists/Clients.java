package org.example.models.lists;

import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Address;
import org.example.models.Bank;
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
        Bank.getInstance().getClients().add(client);
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
                    System.out.println("Ingrese su nombre:");
                    name = scanner.nextLine();
                    if (!validateField(name, NAME_OR_LAST_NAME_REGEX)) {
                        name = null;
                        throw new InvalidFieldException("Nombre invalido. Recuerde, solo se permiten letras y espacios.");
                    }
                }

                if (lastName == null) {
                    System.out.println("Ingrese su apellido:");
                    lastName = scanner.nextLine();
                    if (!validateField(lastName, NAME_OR_LAST_NAME_REGEX)) {
                        lastName = null;
                        throw new InvalidFieldException("Apellido invalido. Recuerde, solo se permiten letras y espacios.");
                    }
                }

                if (email == null) {
                    System.out.println("Ingrese su email:");
                    email = scanner.nextLine();
                    if (!validateField(email, EMAIL_REGEX)) {
                        email = null;
                        throw new InvalidFieldException("Email invalido. Recuerde, debe tener el formato 'email123@email.com'.");
                    }
                    if(!isEmailUnique(email, this.getList())) {
                        email = null;
                        throw new InvalidFieldException("El email ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if(password == null) {
                    System.out.println("Ingrese una contraseña:");
                    password = scanner.nextLine();
                    if (!validateField(password, PASSWORD_REGEX)) {
                        password = null;
                        throw new InvalidFieldException("Contraseña invalida. Recuerde, debe tener al menos 12 caracteres, una mayúscula, una minúscula y un número.");
                    }
                }

                if (dni == null) {
                    System.out.println("Ingrese su DNI:");
                    dni = scanner.nextLine();
                    if (!validateField(dni, DNI_REGEX)) {
                        dni = null;
                        throw new InvalidFieldException("DNI invalido. Recuerde, debe tener 8 dígitos.");
                    }
                    if(!isDniUnique(dni, this.getList())) {
                        dni = null;
                        throw new InvalidFieldException("El DNI ingresado ya se encuentra registrado. Por favor, intente nuevamente.");
                    }
                }

                if (phone == null) {
                    System.out.println("Ingrese su telefono:");
                    phone = scanner.nextLine();
                    if (!validateField(phone, PHONE_REGEX)) {
                        phone = null;
                        throw new InvalidFieldException("Telefono invalido. Recuerde, debe tener 10 dígitos.");
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
