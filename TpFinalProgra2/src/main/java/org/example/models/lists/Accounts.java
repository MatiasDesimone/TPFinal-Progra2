package org.example.models.lists;

import org.example.enums.EAccountType;
import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.NotFoundException;
import org.example.interfaces.ICRUD;
import org.example.models.Account;
import org.example.models.Bank;
import org.example.models.Client;
import org.example.utils.ValidationUtils;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.utils.ValidationUtils.*;

public class Accounts extends GenericList<Account> implements ICRUD {

    public Accounts() {
        super();
    }

    public Accounts(int size) {
        super(size);
    }

    @Override
    public void create() {
        Account account = createAccount();
        if (account != null) {
            Bank.getInstance().getLoggedInClient().getAccounts().add(account);
            System.out.println("Cuenta creada exitosamente.");
        }
    }

    @Override
    public void read() {
        Client client = Bank.getInstance().getLoggedInClient();
        if (clientCheck(client)) return;
        readAccount(client);
    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        Account checkingAccount = Bank.getInstance().getLoggedInClient().getAccountByType(EAccountType.CUENTA_CORRIENTE);
        Account savingsAccount = Bank.getInstance().getLoggedInClient().getAccountByType(EAccountType.CAJA_AHORRO);
        if (accountsChecking(loggedClient, checkingAccount, savingsAccount)) return;
        int option;
        double amount;
        while (true) {
            updateAccountMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        amount = amountChecking();
                        if (amount == 0) {
                            System.out.println("Transferencia cancelada.");
                            return;
                        }
                        checkingToSavings(amount, checkingAccount, savingsAccount);
                        break;
                    case 2:
                        amount = amountChecking();
                        if (amount == 0) {
                            System.out.println("Transferencia cancelada.");
                            return;
                        }
                        savingsToChecking(amount, checkingAccount, savingsAccount);
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
        Client loggedClient = Bank.getInstance().getLoggedInClient();
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                readAccount(loggedClient);
                System.out.print("Ingrese el alias de la cuenta que desea dar de baja: ");
                System.out.println("IMPORTANTE: recuerda introducirlo en mayúsculas.");
                String accountAlias = scanner.nextLine();
                if (!validateField(accountAlias, ALIAS_REGEX)) {
                    throw new InvalidFieldException("Alias de cuenta inválido. Por favor, intente nuevamente.");
                }
                Account accountToDelete = loggedClient.getAccountByAlias(accountAlias);
                doesAccountExist(accountToDelete);
                System.out.println("Está seguro que desea dar de baja la cuenta con alias " + accountAlias + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    loggedClient.getAccounts().remove(accountToDelete);
                    System.out.println("Cuenta dada de baja exitosamente.");
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

    private static void doesAccountExist(Account accountToDelete) throws NotFoundException {
        if (accountToDelete == null) {
            throw new NotFoundException("No se encontró ninguna cuenta con el alias ingresado.");
        }
    }

    private Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = Bank.getInstance().getLoggedInClient();
        String clientId = loggedClient.getClientId();
        EAccountType accountType = null;
        double balance = 0;
        int aux = 0;

        while (true) {
            if (breakPoint(aux)) return null;
            try {
                if (accountType == null) {
                    System.out.println("Ingrese el numero del tipo de cuenta que desea crear:");
                    for (EAccountType type : EAccountType.values()) {
                        System.out.println(type.getTypeId() + ") " + type.getDescription());
                    }
                    System.out.println("0) Cancelar.");
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    if (option == 0) {
                        System.out.println("Creación de cuenta cancelada.");
                        return null;
                    }
                    if (option < 1 || option > EAccountType.values().length) {
                        throw new InvalidFieldException("Opción inválida. Por favor, intente nuevamente.");
                    }
                    accountType = EAccountType.values()[option - 1];
                }
                if (!uniqueAccountVerifier(loggedClient, accountType)) {
                    accountType = null;
                    throw new InvalidFieldException("Ya posee una cuenta de este tipo. No puede tener más de una.");
                }

                if (balance == 0) {
                    System.out.println("Ingrese el saldo inicial de la cuenta:");
                    String balanceInput = scanner.nextLine();
                    if (!balanceInput.matches(ValidationUtils.BALANCE_REGEX)) {
                        balance = 0;
                        throw new InvalidFieldException("El saldo inicial debe ser un monto de al menos 4 dígitos, mayor a 0. Por favor, intente nuevamente.");
                    }
                    balance = Double.parseDouble(balanceInput);
                }

                return new Account(clientId, accountType, balance);

            } catch (InvalidFieldException e) {
                System.out.println(e.getMessage());
            } finally {
                aux++;
            }
        }
    }

    public static void readAccount(Client client) {
        System.out.println("--------------------------------Cuentas--------------------------------");
        if (client.getAccounts().getList().isEmpty()) {
            System.out.println("\nNo posee cuentas.\n\n");
            return;
        }
        for (Account account : client.getAccounts().getList()) {
            if (account.isActive()) {
                System.out.println("Tipo de cuenta: " + account.getAccountType().getDescription());
                System.out.println("CBU: " + account.getCbu());
                System.out.println("Alias: " + account.getAlias());
                System.out.println("Saldo: " + account.getBalance());
                System.out.println("Fecha de creación: " + account.getCreatedAt());
                System.out.println("-----------------------------------------------------------------------\n");
            }
        }
    }


    private static void checkingToSavings(double amount, Account checkingAccount, Account savingsAccount) {
        try {
            if (checkingAccount.getBalance() < amount) {
                throw new InvalidFieldException("Saldo insuficiente en la cuenta corriente.");
            }
            checkingAccount.setBalance(checkingAccount.getBalance() - amount);
            savingsAccount.setBalance(savingsAccount.getBalance() + amount);
            System.out.println("Transferencia realizada exitosamente de cuenta corriente a caja de ahorro.");
        } catch (InvalidFieldException e) {
            System.out.println(e.getMessage());
            System.out.println("Transferencia cancelada.");
        }
    }

    private static void savingsToChecking(double amount, Account checkingAccount, Account savingsAccount) {
        try {
            if (savingsAccount.getBalance() < amount) {
                throw new InvalidFieldException("Saldo insuficiente en la caja de ahorro.");
            }
            savingsAccount.setBalance(savingsAccount.getBalance() - amount);
            checkingAccount.setBalance(checkingAccount.getBalance() + amount);
            System.out.println("Transferencia realizada exitosamente de caja de ahorro a cuenta corriente.");
        } catch (InvalidFieldException e) {
            System.out.println(e.getMessage());
            System.out.println("Transferencia cancelada.");
        }
    }

    private static void updateAccountMenu() {
        System.out.println("Seleccione la opción de transferencia:");
        System.out.println("1) De cuenta corriente a caja de ahorro.");
        System.out.println("2) De caja de ahorro a cuenta corriente.");
        System.out.println("0) Cancelar.");
    }

    private static boolean accountsChecking(Client loggedClient, Account checkingAccount, Account savingsAccount) {
        if (checkingAccount == null || savingsAccount == null) {
            System.out.println("Debe tener tanto una cuenta corriente como una caja de ahorro para realizar transferencias entre ellas.");
            return true;
        }
        return false;
    }

    private static double amountChecking() {
        Scanner scanner = new Scanner(System.in);
        double amount = 0;
        int aux = 0;
        while(true){
            if (breakPoint(aux)) return 0;
            try{
                if (amount == 0) {
                    System.out.println("Ingrese el monto a transferir de la cuenta corriente a la caja de ahorro:");
                    System.out.println("El monto mínimo a transferir es $1000.");
                    String amountInput = scanner.nextLine();
                    if (!amountInput.matches(AMOUNT_REGEX) || Double.parseDouble(amountInput) <= 0) {
                        throw new InvalidFieldException("Monto inválido. Recuerde, el monto mínimo es $1000.");
                    }
                    amount = Double.parseDouble(amountInput);
                    return amount;
                }
            }catch(InvalidFieldException e){
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

}
