package org.example.utils;

import org.example.enums.EAccountType;
import org.example.enums.ECard;
import org.example.exceptions.InvalidFieldException;
import org.example.models.Account;
import org.example.models.Bank;
import org.example.models.Card;
import org.example.models.Client;

import java.util.List;

public class ValidationUtils {
    public static final String NAME_OR_LAST_NAME_REGEX = "^[A-Za-z]+(\\s[A-Za-z]+)?$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{12,}$";
    public static final String DNI_REGEX = "^[0-9]{8}$";
    public static final String PHONE_REGEX = "^[0-9]{10}$";
    public static final String STREET_REGEX = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$";
    public static final String NUMBER_REGEX = "^[0-9]{1,5}$";
    public static final String ADDRESS_REGEX = "^[A-Za-z]+(\\s[A-Za-z]+)*$";
    public static final String POSTAL_CODE_REGEX = "^[0-9]{1,5}$";
    public static final String BALANCE_REGEX = "^[0-9]\\d{5,}$";
    public static final String BANK_IDENTIFIER = "01500013";

    public static boolean validateField(String field, String regex) {
        return field != null && !field.isEmpty() && !field.isBlank() && field.matches(regex);
    }

    public static boolean isEmailUnique(String email, List<Client> clients) {
        return clients.stream().noneMatch(client -> client.getEmail().equals(email));
    }

    public static boolean isDniUnique(String dni, List<Client> clients) {
        return clients.stream().noneMatch(client -> client.getDni().equals(dni));
    }

    public static boolean isPhoneUnique(String phone, List<Client> clients) {
        return clients.stream().noneMatch(client -> client.getPhone().equals(phone));
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

}
