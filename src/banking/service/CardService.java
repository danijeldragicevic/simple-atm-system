package banking.service;

import banking.exception.CardNotExistsException;
import banking.exception.NoTransferToSameAccountException;
import banking.exception.NotEnoughMoneyException;
import banking.exception.NotValidLuhnAlgorithmException;
import banking.model.Card;
import banking.repository.CardRepository;
import banking.utils.InputUtil;

import java.util.Objects;
import java.util.Random;

public class CardService {
    private static final int BIN = 400000;
    private static final int MAX_ACC_ID = 999999999;
    private static final int MAX_PIN = 9999;
    private static final int RADIX = 10;

    private final CardRepository cardRepository;

    public CardService() {
        cardRepository = new CardRepository();
    }

    public Card createAcc() {
        Card card = new Card();
        Random random = new Random();
        String accId = String.format("%09d", random.nextInt(MAX_ACC_ID));
        String checksum = String.valueOf(generateChecksum(BIN + accId));
        String pin = String.format("%04d", random.nextInt(MAX_PIN));

        card.setNumber(BIN + accId + checksum);
        card.setPin(pin);
        card.setBalance(0);

        cardRepository.save(card);

        return card;
    }

    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    public int getBalance(String cardNumber) {
        return cardRepository.getBalance(cardNumber);
    }

    public void addIncome(int income, String cardNumber) {
        cardRepository.addIncome(income, cardNumber);
    }

    public boolean isExisting(String number, String pin) {
        Card card = cardRepository.findByNumber(number);
        if (card == null) {
            return false;
        }

        return Objects.equals(card.getPin(), pin);
    }

    public void doTransfer(String fromCard, String toCard) throws
            NotEnoughMoneyException, NoTransferToSameAccountException,
            NotValidLuhnAlgorithmException, CardNotExistsException {

        if (!isValidLuhnAlgorithm(toCard)) {
            throw new NotValidLuhnAlgorithmException();
        }
        if (fromCard.equals(toCard)) {
            throw new NoTransferToSameAccountException();
        }
        if (cardRepository.findByNumber(toCard) == null) {
            throw new CardNotExistsException();
        }

        System.out.println("Enter how much money you want to transfer:");
        int amount = InputUtil.getIntegerInput();
        if (amount > cardRepository.getBalance(fromCard)) {
            throw new NotEnoughMoneyException();
        } else {
            cardRepository.doTransfer(amount, fromCard, toCard);
        }
    }

    public void closeAccount(String number) {
        cardRepository.deleteCard(number);
    }

    private int generateChecksum(String num) {
        char[] binAccId = num.toCharArray();
        int sum = 0;
        for (int i = 0; i < binAccId.length; i++) {
            int no = Character.getNumericValue(binAccId[i]);
            if (i % 2 == 0) {
                binAccId[i] = Character.forDigit(no * 2 > 9 ? no * 2 - 9 : no * 2, RADIX);
                sum += Character.getNumericValue(binAccId[i]);
                continue;
            }
            sum += no;
        }

        return 10 - (sum % 10) == 10 ? 0 : 10 - (sum % 10);
     }

    private boolean isValidLuhnAlgorithm(String str) {
        String accNumber = str.substring(0, str.length() - 1);
        String lastDigit = str.substring(str.length() - 1);

        int result = 0;
        for (int i = 0; i < accNumber.length(); i++) {
            int digit = Character.getNumericValue(accNumber.charAt(i));
            if (i % 2 == 0) {
                int doubleDigit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
                result += doubleDigit;
                continue;
            }
            result += digit;
        }
        int allDigits = result + Integer.parseInt(lastDigit);

        return allDigits % 10 == 0;
    }

}
