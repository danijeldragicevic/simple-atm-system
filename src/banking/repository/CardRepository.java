package banking.repository;

import banking.model.Card;
import banking.utils.DBUtils;

import java.sql.*;

public class CardRepository {

    public CardRepository() {
        createTable();
    }

    private void createTable() {
        Connection connection = DBUtils.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS cards (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "number VARCHAR(16)," +
                    "pin VARCHAR(4)," +
                    "balance INTEGER DEFAULT 0" +
                    ")";

            statement.executeUpdate(query);
            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Card findByNumber(String number) {
        Card card = new Card();
        Connection connection = DBUtils.getConnection();
        try {
            String query = "SELECT * FROM cards WHERE number = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, number);

            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            card.setId(resultSet.getInt("id"));
            card.setNumber(resultSet.getString("number"));
            card.setPin(resultSet.getString("pin"));
            card.setBalance(resultSet.getInt("balance"));

            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public void save(Card card) {
        Connection connection = DBUtils.getConnection();
        try {
            String query = "INSERT INTO cards(number, pin, balance) VALUES (?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, card.getNumber());
            ps.setString(2, card.getPin());
            ps.setInt(3, card.getBalance());
            ps.executeUpdate();

            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIncome(int income, String number) {
        Connection connection = DBUtils.getConnection();
        try {
            String query = "UPDATE cards SET balance = balance + ? WHERE number = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, income);
            ps.setString(2, number);
            ps.executeUpdate();

            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getBalance(String number) {
        Connection connection = DBUtils.getConnection();
        int balance = 0;
        try {
            String query = "SELECT * FROM cards WHERE number = ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, number);

            ResultSet resultSet = ps.executeQuery();
            balance = resultSet.getInt("balance");

            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public void doTransfer(int amount, String fromCard, String toCard) {
        Connection connection = DBUtils.getConnection();
        try {
            connection.setAutoCommit(false);

            String subtractAmountSQL = "UPDATE cards SET balance = balance - ? WHERE number = ?";
            PreparedStatement subtractAmount = connection.prepareStatement(subtractAmountSQL);
            subtractAmount.setInt(1, amount);
            subtractAmount.setString(2, fromCard);
            subtractAmount.executeUpdate();

            String addAmountSQL = "UPDATE cards SET balance = balance + ? WHERE number = ?";
            PreparedStatement addAmount = connection.prepareStatement(addAmountSQL);
            addAmount.setInt(1, amount);
            addAmount.setString(2, toCard);
            addAmount.executeUpdate();

            connection.commit();
            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
    }

    public void deleteCard(String number) {
        Connection connection = DBUtils.getConnection();
        try {
            String query = "DELETE FROM cards WHERE number = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, number);
            ps.executeUpdate();

            DBUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
