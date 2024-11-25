package org.example;

import org.example.models.Bank;
import org.example.models.Client;
import org.example.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "bank_data.json";

    public static void main(String[] args) {
        Bank bank = Bank.getInstance();

        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                Bank loadedBank = JsonUtils.fromJsonFile(file, Bank.class);
                Bank.setInstance(loadedBank);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                bank.setLoggedInClient(null);
                bank.setLoggedAdmin(null);
                JsonUtils.toJsonFile(new File(DATA_FILE), Bank.getInstance());
                System.out.println("Datos guardados exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al guardar los datos: " + e.getMessage());
            }
        }));

//              bank.getAdmins().create();  //crear administrador
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBienvenido al " + bank.getName());
            System.out.println("1) Login.");
            System.out.println("2) Registrarse.");
            System.out.println("3) Salir.");
            System.out.print("Seleccione una opción: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.print("Ingrese su email: ");
                        String email = scanner.nextLine();
                        System.out.print("Ingrese su contraseña: ");
                        String password = scanner.nextLine();
                        int loginResult = bank.login(email, password);
                        if (loginResult == 1) {
                            adminMenu(bank);
                        } else if (loginResult == 2) {
                            clientMenu(bank);
                        }
                        break;
                    case 2:
                        bank.getClients().create();
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Gracias por usar nuestro servicio. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }



        try {
            bank.setLoggedInClient(null);
            bank.setLoggedAdmin(null);
            JsonUtils.toJsonFile(new File(DATA_FILE), bank);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }


    }

    private static void clientMenu(Bank bank) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenú de Cliente");
            System.out.println("1) Acceder al menu de visualizacion.");
            System.out.println("2) Crear una cuenta.");
            System.out.println("3) Realizar una transferencia.");
            System.out.println("4) Crear una tarjeta.");
            System.out.println("5) Otras operaciones.");
            System.out.println("6) Logout");
            System.out.print("Seleccione una opción: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        bank.getClients().read();
                        break;
                    case 2:
                        Client loggedInClient = bank.getLoggedInClient();
                        if (loggedInClient != null) {
                            loggedInClient.getAccounts().create();
                        } else {
                            System.out.println("Debe iniciar sesión antes de crear una cuenta.");
                        }
                        break;
                    case 3:
                        bank.getLoggedInClient().getTransactions().create();
                        break;
                    case 4:
                        bank.getLoggedInClient().getCards().create();
                        break;
                    case 5:
                        bank.getClients().update();
                        break;
                    case 6:
                        bank.logout();
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private static void adminMenu(Bank bank) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenú de Administrador");
            System.out.println("1) Acceder al menu de visualizacion.");
            System.out.println("2) Acceder al menu de updates.");
            System.out.println("3) Acceder al menu de eliminación.");
            System.out.println("4) Logout");
            System.out.print("Seleccione una opción: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        bank.getAdmins().read();
                        break;
                    case 2:
                        bank.getAdmins().update();
                        break;
                    case 3:
                        bank.getAdmins().delete();
                        break;
                    case 4:
                        bank.logout();
                        exit = true;
                    default:
                        System.out.println("Opción inválida. Por favor, intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
            }
        }
    }
}

