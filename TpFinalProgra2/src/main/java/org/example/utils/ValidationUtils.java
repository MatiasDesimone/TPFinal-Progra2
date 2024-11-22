package org.example.utils;

import org.example.models.Client;

import java.util.List;

public class ValidationUtils {
    public static final String nameOrLastNameRegex = "^[A-Za-z]+(\\s[A-Za-z]+)?$";
    public static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{12,}$";
    public static final String dniRegex = "^[0-9]{8}$";
    public static final String phoneRegex = "^[0-9]{10}$";
    public static final String streetRegex = "^[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*$";
    public static final String numberRegex = "^[0-9]{1,5}$";
    public static final String addressRegex = "^[A-Za-z]+(\\s[A-Za-z]+)*$";
    public static final String postalCodeRegex = "^[0-9]{1,5}$";

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

}
