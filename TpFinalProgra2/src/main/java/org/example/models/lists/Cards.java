package org.example.models.lists;

import org.example.enums.ECard;
import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.NotFoundException;
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
        System.out.println("UNA VEZ QUE SE CREA UNA TARJETA NO SE PUEDE MODIFICAR. Solo pedir la baja de la tarjeta.");
    }

    @Override
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        int aux = 0;
        while (true) {
            if (breakPoint(aux)) return;
            try {
                Client loggedClient = getLoggedInClient();
                readCards(loggedClient);
                System.out.print("Ingrese el número de la tarjeta que desea dar de baja: ");
                String cardNumber = scanner.nextLine();
                if (!validateField(cardNumber, CARD_NUMBER_REGEX)) {
                    throw new InvalidFieldException("Número de tarjeta inválido. Por favor, intente nuevamente.");
                }
                Card cardToUpdate = getCardByNumber(cardNumber);
                doesCardExist(cardToUpdate);
                System.out.println("Está seguro que desea dar de baja la tarjeta con número " + cardNumber + "?");
                System.out.println("1) Sí.");
                System.out.println("2) No.");
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option == 1) {
                    loggedClient.getCards().remove(cardToUpdate);
                    System.out.println("Tarjeta dada de baja exitosamente.");
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

    public static Card getCardByNumber(String cardNumber) {
        Card cardToUpdate = null;
        for (Card card : Bank.getInstance().getLoggedInClient().getCards().getList()) {
            if (card.getNumber().equals(cardNumber)) {
                cardToUpdate = card;
                break;
            }
        }
        return cardToUpdate;
    }

    private static void doesCardExist(Card cardToUpdate) throws NotFoundException {
        if (cardToUpdate == null) {
            throw new NotFoundException("No se encontró la tarjeta con el número ingresado.");
        }
    }

}
