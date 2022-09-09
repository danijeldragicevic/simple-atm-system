package atm;

import atm.controller.CardController;
import atm.utils.DBUtils;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        CardController cardController = new CardController();
        cardController.start();
    }
}