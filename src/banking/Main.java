package banking;

import banking.controller.CardController;
import banking.utils.DBUtils;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && Objects.equals(args[0], "-fileName")) {
            DBUtils.filename = args[1];
        }

        CardController cardController = new CardController();
        cardController.start();
    }
}