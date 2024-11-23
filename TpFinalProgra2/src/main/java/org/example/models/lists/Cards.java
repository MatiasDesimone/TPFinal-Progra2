package org.example.models.lists;

import org.example.enums.ECard;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Bank;
import org.example.models.Card;
import org.example.models.Client;

import java.util.Scanner;

import static org.example.utils.ValidationUtils.*;

public class Cards extends GenericList<Card> implements ICRUD {

    public Cards() {
        super();
    }

    public Cards(int size) {
        super(size);
    }

    @Override
    public void create() {
        Card card = createCard();
        if(card != null){
            Bank.getInstance().getLoggedInClient().getCards().add(card);
            System.out.println("Tarjeta creada exitosamente.");
        }
    }

    @Override
    public void read() {
        Client client = Bank.getInstance().getLoggedInClient();
        if(clientCheck(client)) return;
        readCards(client);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    private Card createCard() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String clientId = loggedClient.getClientId();
        ECard cardType = null;

        while(true){
            try{
                if(cardType == null) {
                    System.out.println("Seleccione la opción de la tarjeta que desea crear:");
                    for (ECard type : ECard.values()) {
                        System.out.println(type.getTypeId() + ") " + type.getDescription());
                    }
                    System.out.println("0) Cancelar.");
                    int option = scanner.nextInt();
                    scanner.nextLine();
                    if (option == 0) {
                        System.out.println("Creación de tarjeta cancelada.");
                        return null;
                    }
                    if(option < 1 || option > ECard.values().length){
                        throw new InvalidFieldException ("Opción inválida. Intente nuevamente.");
                    }
                    cardType = ECard.values()[option - 1];
                    if(!uniqueCardVerifier(loggedClient, cardType)){
                        cardType = null;
                        throw new InvalidFieldException("Ya posee una tarjeta de este tipo.");
                    }

                    return new Card(clientId, cardType);

                }
            }catch (InvalidFieldException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void readCards(Client client){
        System.out.println("\n------------------------Tarjetas------------------------");
        for(Card card : client.getCards().getList()){
            System.out.println("Tipo de tarjeta: " + card.getType().getDescription());
            System.out.println("Número de tarjeta: " + card.getNumber());
            System.out.println("Código de seguridad: " + card.getCvv());
            System.out.println("Fecha de vencimiento: " + card.getExpirationDate());
            System.out.println("-----------------------------------\n");
        }
    }

}
