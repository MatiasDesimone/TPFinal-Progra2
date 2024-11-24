package org.example.models.lists;

import org.example.enums.EAccountType;
import org.example.enums.EStatus;
import org.example.enums.ETransactionDescription;
import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.NotFoundException;
import org.example.interfaces.ICRUD;
import org.example.models.Account;
import org.example.models.Bank;
import org.example.models.Client;
import org.example.models.Transaction;

import java.util.Scanner;

import static org.example.models.Transaction.statusRandomGenerator;
import static org.example.utils.ValidationUtils.*;

public class Transactions extends GenericList<Transaction> implements ICRUD {

    public Transactions() {
        super();
    }

    public Transactions(int size) {
        super(size);
    }

    @Override
    public void create() {
        Transaction transaction = createTransaction();
        if(transaction != null){
            Bank.getInstance().getLoggedInClient().getTransactions().add(transaction);
            System.out.println("Transacción realizada exitosamente.");
        }
    }

    @Override
    public void read() {
        Client client = Bank.getInstance().getLoggedInClient();
        if(clientCheck(client)) return;
        readOutTransactions(client);
        readInTransactions(client);
    }

    @Override
    public void update() {
        //No supimos que podíamos updatear de las transacciones...
    }

    @Override
    public void delete() {
        //Tampoco está bueno borrar transacciones... Mejor guardarlas por si acaso.
    }

    private Transaction createTransaction(){
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String accountId = null;
        String ownerCBU = null;
        String recipientCBU = null;
        String recipientAlias = null;
        double amount = 0;
        ETransactionDescription description = null;
        EStatus status = null;
        int aux = 0;

        while (true) {
            if (breakPoint(aux)) return null;
            try {
                Account accountOwner = null;
                Account accountRecipient = null;
                if (accountId == null) {
                    System.out.println("Ingrese la cuenta desde la que desea trasnferir:");
                    for(Account account : loggedClient.getAccounts().getList()){
                        System.out.println(account.getAccountType().getTypeId()+") " + account.getAccountType().getDescription());
                    }
                    System.out.println("0) Cancelar.");
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    if (option == 0) {
                        System.out.println("Creación de tarjeta cancelada.");
                        return null;
                    }
                    if(option < 1 || option > EAccountType.values().length){
                        throw new InvalidFieldException ("Opción inválida. Intente nuevamente.");
                    }
                    EAccountType accountType = EAccountType.values()[option - 1];
                    accountOwner = loggedClient.getAccountByType(accountType);
                    if(accountOwner == null){
                        throw new NotFoundException("No se encontró la cuenta seleccionada.");
                    }
                    accountId = accountOwner.getAccountId();
                    ownerCBU = accountOwner.getCbu();
                }

                if (recipientCBU == null && recipientAlias == null) {
                    System.out.println("Ingrese el CBU o el alias del destinatario:");
                    String cbuOrAlias = scanner.nextLine();

                    if (validateField(cbuOrAlias, CBU_REGEX)) {
                        recipientCBU = cbuOrAlias;
                        recipientAlias = null;
                    } else if (validateField(cbuOrAlias, ALIAS_REGEX)) {
                        recipientAlias = cbuOrAlias;
                        recipientCBU = null;
                    } else {
                        recipientAlias = null;
                        recipientCBU = null;
                        throw new InvalidFieldException("CBU o alias inválido. Por favor, intente nuevamente.");
                    }
                    if (recipientCBU != null) {
                        if (recipientCBU.equals(ownerCBU)) {
                            recipientCBU = null;
                            throw new InvalidFieldException("No puede transferir a su propia cuenta.");
                        }
                    } else {
                        if (recipientAlias.equals(loggedClient.getAccountByCBU(ownerCBU).getAlias())) {
                            recipientAlias = null;
                            throw new InvalidFieldException("No puede transferir a su propia cuenta.");
                        }
                    }
                    if(recipientCBU != null){
                        accountRecipient = Bank.getInstance().getClients().getList().getFirst().getAccountByCBU(recipientCBU);
                    } else if(recipientAlias != null){
                        accountRecipient = Bank.getInstance().getClients().getList().getFirst().getAccountByAlias(recipientAlias);
                        recipientCBU = accountRecipient.getCbu();
                    }else {
                        throw new NotFoundException("No se encontró la cuenta del destinatario.");
                    }
                }

                if(description == null){
                    System.out.println("Seleccione la opción que describa la operación:");
                    for(ETransactionDescription transactionDescription : ETransactionDescription.values()){
                        System.out.println(transactionDescription.getTypeId() + ") " + transactionDescription.getDescription());
                    }
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    if(option < 1 || option > ETransactionDescription.values().length){
                        throw new InvalidFieldException("Opción inválida. Por favor, intente nuevamente.");
                    }
                    description = ETransactionDescription.values()[option - 1];
                }

                if(amount == 0){
                    System.out.println("Ingrese el monto a transferir:");
                    String amountInput = scanner.nextLine();
                    if(amount <= 0 || !amountInput.matches(AMOUNT_REGEX)){
                        throw new InvalidFieldException("Monto inválido. Recuerde, solo se permiten números.");
                    }
                    if(Double.parseDouble(amountInput) > accountOwner.getBalance()){
                        throw new InvalidFieldException("Saldo insuficiente.");
                    }
                    amount = Double.parseDouble(amountInput);
                    accountRecipient.setBalance(accountRecipient.getBalance() + amount);
                    accountOwner.setBalance(accountOwner.getBalance() - amount);
                }

                if(status == null){
                    status = statusRandomGenerator();
                    if(status == EStatus.REJECTED){
                        System.out.println("La transacción fue rechazada.");
                        for (int i = 5; i > 0; i--) {
                            System.out.println("Reintentando en " + i + " segundos...");
                            Thread.sleep(1000);
                        }
                        status = EStatus.APPROVED;
                        System.out.println("La transacción fue aprobada.");
                    }else {
                        System.out.println("La transacción fue aprobada.");
                    }
                }

                return new Transaction(accountId, ownerCBU, recipientCBU, amount, description, status);

            } catch (InvalidFieldException | NotFoundException | InterruptedException e) {
                System.out.println(e.getMessage());
            }finally {
                aux++;
            }
        }
    }

    public static void readOutTransactions(Client client){
        System.out.println("\n------------------------Transacciones salientes------------------------");
        for(Transaction transaction : client.getTransactions().getList()){
            System.out.println("ID de transacción: " + transaction.getTransactionId());
            System.out.println("CBU del propietario: " + transaction.getOwnerCBU());
            System.out.println("CBU del destinatario: " + transaction.getRecipientCBU());
            System.out.println("Monto: " + transaction.getAmount());
            System.out.println("Descripción: " + transaction.getDescription().getDescription());
            System.out.println("Fecha: " + transaction.getDate());
            System.out.println("Estado: " + transaction.getStatus().getDescription());
            System.out.println("------------------------------------------------------------");
        }
    }

    public static void readInTransactions(Client client){
        System.out.println("\n------------------------Transacciones entrantes------------------------");
        for(Client client1 : Bank.getInstance().getClients().getList()){
            for(Transaction transaction : client1.getTransactions().getList()){
                if(transaction.getRecipientCBU().equals(client.getAccountByType(EAccountType.CUENTA_CORRIENTE).getCbu())){
                    System.out.println("ID de transacción: " + transaction.getTransactionId());
                    System.out.println("CBU del propietario: " + transaction.getOwnerCBU());
                    System.out.println("CBU del destinatario: " + transaction.getRecipientCBU());
                    System.out.println("Monto: " + transaction.getAmount());
                    System.out.println("Descripción: " + transaction.getDescription().getDescription());
                    System.out.println("Fecha: " + transaction.getDate());
                    System.out.println("Estado: " + transaction.getStatus().getDescription());
                    System.out.println("------------------------------------------------------------");
                }
            }
        }
    }

}
