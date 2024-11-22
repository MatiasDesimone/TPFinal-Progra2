package org.example.models.lists;

import org.example.enums.ECard;
import org.example.exceptions.InvalidFieldException;
import org.example.interfaces.ICRUD;
import org.example.models.Bank;
import org.example.models.Card;
import org.example.models.Client;

import java.util.Scanner;

import static org.example.utils.ValidationUtils.getLoggedInClient;
import static org.example.utils.ValidationUtils.uniqueCardVerifier;

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
        Bank.getInstance().getLoggedInClient().getCards().add(card);
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

    private Card createCard() {
        Scanner scanner = new Scanner(System.in);
        Client loggedClient = getLoggedInClient();
        String clientId = loggedClient.getClientId();
        ECard cardType = null;

        while(true){
            try{
                if(cardType == null) {
                    System.out.println("Ingrese el numero del tipo de tarjeta que desea crear:");
                    for (ECard type : ECard.values()) {
                        System.out.println(type.getTypeId() + ". " + type.getDescription());
                    }
                    int option = scanner.nextInt();
                    scanner.nextLine();
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
}
