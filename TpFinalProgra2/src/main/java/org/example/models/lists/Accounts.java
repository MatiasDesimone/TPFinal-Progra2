package org.example.models.lists;

import org.example.enums.EAccountType;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Account;
import org.example.models.Bank;
import org.example.models.Client;
import org.example.utils.ValidationUtils;

import java.util.Scanner;

import static org.example.utils.ValidationUtils.getLoggedInClient;
import static org.example.utils.ValidationUtils.uniqueAccountVerifier;

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
        Bank.getInstance().getLoggedInClient().getAccounts().add(account);
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

    private Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String clientId = loggedClient.getClientId();
        EAccountType accountType = null;
        double balance = 0;

        while(true){
            try{
                if(accountType == null) {
                    System.out.println("Ingrese el numero del tipo de cuenta que desea crear:");
                    for (EAccountType type : EAccountType.values()) {
                        System.out.println(type.getTypeId() + ". " + type.getDescription());
                    }
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    if (option == 1) {
                        accountType = EAccountType.CAJA_AHORRO;
                    }
                    if (option == 2) {
                        accountType = EAccountType.CUENTA_CORRIENTE;
                    }
                    if (option == 3){
                        accountType = EAccountType.CUENTA_DOLARES;
                    } else {
                        throw new InvalidFieldException("Opcion inválida. Por favor, intente nuevamente.");
                    }
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
            }
        }
    }


}
