package banking.manager;

import banking.data_models.Account;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DatabaseManager {

    private String databaseFileName;
    private SQLiteDataSource dataSource = new SQLiteDataSource();

    public DatabaseManager(String databaseUrl) {
        this.databaseFileName = databaseUrl;
        initializeDatabase();
    }

    public void initializeDatabase() {
        String url = "jdbc:sqlite:" + databaseFileName;
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER PRIMARY KEY, " +
                    "number TEXT NOT NULL, " +
                    "pin TEXT NOT NULL, " +
                    "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCard(String cardNumber, String pin) {
        // This sets placeholders when executing the sql statement
        String insertSql = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account findCard(String cardNumber, String pin) {
        String selectSql = "SELECT * FROM card WHERE number = ? AND pin = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("id"));
                    account.setCardNumber(resultSet.getString("number"));
                    account.setPin(resultSet.getString("pin"));
                    account.setBalance(resultSet.getInt("balance"));
                    return account;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findCard(String cardNumber) {
        String selectSql = "SELECT * FROM card WHERE number = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            preparedStatement.setString(1, cardNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("id"));
                    account.setCardNumber(resultSet.getString("number"));
                    account.setPin(resultSet.getString("pin"));
                    account.setBalance(resultSet.getInt("balance"));
                    return account;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBalance(String cardNumber, int amount) {
        try (Connection connection = dataSource.getConnection()) {
            String selectSql = "SELECT balance FROM card WHERE number = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, cardNumber);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    int currentBalance = resultSet.getInt("balance");
                    int newBalance = currentBalance + amount;
                    String updateSql = "UPDATE card SET balance = ? WHERE number = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                        preparedStatement.setInt(1, newBalance);
                        preparedStatement.setString(2, cardNumber);
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            Connection connection = dataSource.getConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(String cardNumber) {
        String deleteSql = "DELETE FROM card WHERE number = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
