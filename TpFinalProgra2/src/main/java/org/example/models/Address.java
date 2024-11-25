package org.example.models;

import org.example.exceptions.InvalidFieldException;

import java.util.Scanner;

import static org.example.utils.ValidationUtils.*;

public class Address implements Comparable<Address> {
    private String street;
    private String number;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    public Address() {
    }

    public Address(String street, String number, String city, String state, String country, String postalCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public int compareTo(Address other) {
        int result = this.street.compareTo(other.street);
        if (result != 0) return result;

        result = this.number.compareTo(other.number);
        if (result != 0) return result;

        result = this.city.compareTo(other.city);
        if (result != 0) return result;

        result = this.state.compareTo(other.state);
        if (result != 0) return result;

        result = this.country.compareTo(other.country);
        if (result != 0) return result;

        return this.postalCode.compareTo(other.postalCode);
    }

    public Address createAddress() {
        Scanner scanner = new Scanner(System.in);
        String street = null;
        String number = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        int aux = 0;

        while (true) {
            if(breakPoint(aux)) return null;
            try {
                if (street == null) {
                    System.out.println("Ingrese el nombre de la calle:");
                    System.out.println("Ejemplo: Av. Siempre Viva");
                    street = scanner.nextLine();
                    if (!validateField(street, STREET_REGEX)) {
                        street = null;
                        throw new InvalidFieldException("Calle inválida. Recuerde que solo se permiten letras y números. Por favor, intente nuevamente.");
                    }

                    if(number == null) {
                        System.out.println("Ingrese el número:");
                        System.out.println("Ejemplo: 1234");
                        number = scanner.nextLine();
                        if (!validateField(number, NUMBER_REGEX)) {
                            number = null;
                            throw new InvalidFieldException("Número inválido. Recuerde que solo se permiten números. Por favor, intente nuevamente.");
                        }
                    }

                    if(city == null) {
                        System.out.println("Ingrese la ciudad:");
                        System.out.println("Ejemplo: Springfield");
                        city = scanner.nextLine();
                        if (!validateField(city, ADDRESS_REGEX)) {
                            city = null;
                            throw new InvalidFieldException("Ciudad inválida. Recuerde que solo se permiten letras y números. Por favor, intente nuevamente.");
                        }
                    }

                    if(state == null) {
                        System.out.println("Ingrese la provincia:");
                        System.out.println("Ejemplo: Buenos Aires");
                        state = scanner.nextLine();
                        if (!validateField(state, ADDRESS_REGEX)) {
                            state = null;
                            throw new InvalidFieldException("Provincia inválida. Recuerde que solo se permiten letras y números. Por favor, intente nuevamente.");
                        }
                    }

                    if(country == null) {
                        System.out.println("Ingrese el país:");
                        System.out.println("Ejemplo: Argentina");
                        country = scanner.nextLine();
                        if (!validateField(country, ADDRESS_REGEX)) {
                            country = null;
                            throw new InvalidFieldException("País inválido. Recuerde que solo se permiten letras y números. Por favor, intente nuevamente.");
                        }
                    }

                    if(postalCode == null) {
                        System.out.println("Ingrese el código postal:");
                        System.out.println("Ejemplo: 1234");
                        postalCode = scanner.nextLine();
                        if (!validateField(postalCode, POSTAL_CODE_REGEX)) {
                            postalCode = null;
                            throw new InvalidFieldException("Código postal inválido. Recuerde que solo se permiten números. Por favor, intente nuevamente.");
                        }
                    }

                    return new Address(street, number, city, state, country, postalCode);
                }
            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

    @Override
    public String toString() {
        return  "\n-------------------------Domicilio--------------------------" +
                "\nCalle: " + street +
                "\nNumero: " + number +
                "\nCiudad: " + city +
                "\nProvincia: " + state +
                "\nPaís: " + country +
                "\nCódigo postal: " + postalCode +
                "\n------------------------------------------------------------";
    }
}
