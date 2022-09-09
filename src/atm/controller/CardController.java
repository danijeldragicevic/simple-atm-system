package atm.controller;

import atm.exception.CardNotExistsException;
import atm.exception.NoTransferToSameAccountException;
import atm.exception.NotEnoughMoneyException;
import atm.exception.NotValidLuhnAlgorithmException;
import atm.model.Card;
import atm.service.CardService;
import atm.utils.InputUtil;

public class CardController {
    private static final int CREATE_ACCOUNT = 1;
    private static final int LOG_IN = 2;

    private static final int BALANCE = 1;
    private static final int ADD_INCOME = 2;
    private static final int DO_TRANSFER = 3;
    private static final int CLOSE_ACCOUNT = 4;
    private static final int LOG_OUT = 5;

    private static final int EXIT = 0;

    private final CardService cardService;

    public CardController() {
        cardService = new CardService();
    }

    public void start() {
        showStartMenu();
    }

    private void showStartMenu() {
        int option;

        while (true) {
            System.out.println(
                "1. Create account\n" +
                "2. Log into account\n" +
                "0. Exit"
            );

            option = InputUtil.getIntegerInput();

            switch (option) {
                case CREATE_ACCOUNT:
                    processCreateAcc();
                    break;
                case LOG_IN:
                    processLogin();
                    break;
                case EXIT:
                    System.out.println("\nBye!");
                    return;
            }
        }
    }

    private void processCreateAcc() {
        Card card = cardService.createAcc();
        showCardInfo(card);
    }

    private void processLogin() {
        System.out.println("\nEnter your card number:");
        String number = InputUtil.getStringInput();
        System.out.println("Enter your PIN:");
        String pin = InputUtil.getStringInput();

        if (cardService.isExisting(number, pin)) {
            showLoginMenu(cardService.findByNumber(number));
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    private void showCardInfo(Card card) {
        System.out.println(
            "\nYour card has been created\n" +
            "Your card number:\n" +
            card.getNumber() + "\n" +
            "Your card PIN:\n" +
            card.getPin() + "\n");
    }

    private void showLoginMenu(Card card) {
        int option;

        System.out.println("\nYou have successfully logged in!\n");

        while (true) {
            System.out.println("" +
                    "1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");

            option = InputUtil.getIntegerInput();

            switch (option) {
                case BALANCE:
                    System.out.println("\nBalance: " + cardService.getBalance(card.getNumber()) + "\n");
                    break;
                case ADD_INCOME:
                    System.out.println("\nEnter income:");
                    cardService.addIncome(InputUtil.getIntegerInput(), card.getNumber());
                    System.out.println("Income was added!\n");
                    break;
                case DO_TRANSFER:
                    System.out.println("\nTransfer");
                    System.out.println("Enter card number:");
                    String number = InputUtil.getStringInput();
                    try {
                        cardService.doTransfer(card.getNumber(), number);
                        System.out.println("Success!\n");
                    } catch (NotValidLuhnAlgorithmException e) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!\n");
                    } catch (NoTransferToSameAccountException e) {
                        System.out.println("You can't transfer money to the same account!\n");
                    } catch (CardNotExistsException e) {
                        System.out.println("Such a card does not exist.\n");
                    } catch (NotEnoughMoneyException e) {
                        System.out.println("Not enough money!\n");
                    }
                    break;
                case CLOSE_ACCOUNT:
                    cardService.closeAccount(card.getNumber());
                    System.out.println("The account has been closed!\n");
                case LOG_OUT:
                    System.out.println("\nYou have successfully logged out!\n");
                    return;
                case EXIT:
                    System.out.println("\nBye!");
                    System.exit(0);
            }
        }
    }
}
