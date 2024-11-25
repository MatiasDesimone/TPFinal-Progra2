package org.example.utils;

import org.example.enums.EAccountType;
import org.example.enums.ECard;
import org.example.exceptions.InvalidFieldException;
import org.example.models.*;

import java.util.List;
import java.util.Scanner;

public class ValidationUtils {
    public static final String NAME_OR_LAST_NAME_REGEX = "^[A-Za-z]{3,}(\\s[A-Za-z]+)?$";
    public static final String EMAIL_REGEX = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{12,}$";
    public static final String DNI_REGEX = "^[0-9]{7,9}$";
    public static final String PHONE_REGEX = "^[0-9]{10}$";
    public static final String STREET_REGEX = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$";
    public static final String NUMBER_REGEX = "^[0-9]{1,5}$";
    public static final String ADDRESS_REGEX = "^[A-Za-z]+(\\s[A-Za-z]+)*$";
    public static final String POSTAL_CODE_REGEX = "^[0-9]{1,5}$";
    public static final String BALANCE_REGEX = "^[0-9]\\d{3,}$";
    public static final String BANK_IDENTIFIER = "01500013";
    public static final String CBU_REGEX = "^[0-9]{22}$";
    public static final String ALIAS_REGEX = "^[A-Z]+\\.[A-Z]+\\.[A-Z]+$";
    public static final String AMOUNT_REGEX = "^[0-9]{3,9}$";
    public static final String ID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static final String CARD_NUMBER_REGEX = "^\\d{16}$";


    public static boolean validateField(String field, String regex) {
        return field != null && !field.isEmpty() && !field.isBlank() && field.matches(regex);
    }

    public static boolean isEmailUnique(String email, List<Client> clients) {
        for (Client client : clients) {
            if (client.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDniUnique(String dni, List<Client> clients) {
        for (Client client : clients) {
            if (client.getDni().equals(dni)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPhoneUnique(String phone, List<Client> clients) {
        for (Client client : clients) {
            if (client.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }

    public static Client getLoggedInClient(){
        return Bank.getInstance().getLoggedInClient();
    }

    public static boolean uniqueAccountVerifier(Client loggedClient, EAccountType accountType) throws InvalidFieldException {
        for (Account account : loggedClient.getAccounts().getList()) {
            if (account.getAccountType() == accountType) {
                return false;
            }
        }
        return true;
    }

    public static boolean uniqueCardVerifier(Client loggedClient, ECard cardType) throws InvalidFieldException {
        for (Card card : loggedClient.getCards().getList()) {
            if (card.getType() == cardType) {
                return false;
            }
        }
        return true;
    }

    public static boolean clientCheck(Client client) {
        if (client == null) {
            System.out.println("Cliente ingresado inválido.");
            return true;
        }
        return false;
    }

    public static boolean adminCheck(Admin admin) {
        if (admin == null) {
            System.out.println("Administrador no encontrado. Saliendo...");
            return true;
        }
        return false;
    }

    public static boolean breakPoint(int aux) {
        Scanner scanner = new Scanner(System.in);
        if(aux > 0){
            System.out.println("Desea continuar con la operación?");
            System.out.println("1) Sí.");
            System.out.println("2) No.");
            int option = scanner.nextInt();
            scanner.nextLine();
            if(option == 2){
                System.out.println("Operación cancelada.");
                return true;
            }
        }
        return false;
    }

}
