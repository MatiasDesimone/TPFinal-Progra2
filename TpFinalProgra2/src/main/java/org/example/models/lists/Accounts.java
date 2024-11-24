package org.example.models.lists;

import org.example.enums.EAccountType;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Account;
import org.example.models.Bank;
import org.example.models.Client;
import org.example.utils.ValidationUtils;

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
        if(account != null){
            Bank.getInstance().getLoggedInClient().getAccounts().add(account);
            System.out.println("Cuenta creada exitosamente.");
        }
    }

    @Override
    public void read() {
        Client client = Bank.getInstance().getLoggedInClient();
        if(clientCheck(client)) return;
        readAccount(client);
    }

    @Override
    public void update() {
        //todo: implementar aumentar saldo.
    }

    @Override
    public void delete() {
        //todo: dar de baja una cuenta.
    }

    private Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String clientId = loggedClient.getClientId();
        EAccountType accountType = null;
        double balance = 0;
        int aux = 0;

        while(true){
            if (breakPoint(aux)) return null;
            try{
                if(accountType == null) {
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
                if(!uniqueAccountVerifier(loggedClient, accountType)) {
                    accountType = null;
                    throw new InvalidFieldException("Ya posee una cuenta de este tipo. No puede tener más de una.");
                }

                if(balance == 0){
                    System.out.println("Ingrese el saldo inicial de la cuenta:");
                    String balanceInput = scanner.nextLine();
                    if(balance <= 0 || !balanceInput.matches(ValidationUtils.BALANCE_REGEX)){
                        balance = 0;
                        throw new InvalidFieldException("El saldo inicial debe ser un monto de al menos 6 dígitos, mayor a 0." +
                                " Por favor, intente nuevamente.");
                    }
                    balance = Double.parseDouble(balanceInput);
                }

                return new Account(clientId, accountType, balance);

            }catch(InvalidFieldException e){
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

    public static void readAccount(Client client) {
        System.out.println("--------------Cuentas--------------\n");
        for(Account account : client.getAccounts().getList()){
            System.out.println("Tipo de cuenta: " + account.getAccountType().getDescription());
            System.out.println("CBU: " + account.getCbu());
            System.out.println("Alias: " + account.getAlias());
            System.out.println("Saldo: " + account.getBalance());
            System.out.println("Fecha de creación: " + account.getCreatedAt());
            System.out.println("Costo de mantenimiento: " + account.getMaintenanceFee());
            System.out.println("-----------------------------------\n");
        }
    }
}
