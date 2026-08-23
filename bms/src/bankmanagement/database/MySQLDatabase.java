package bankmanagement.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankmanagement.model.Account;
import bankmanagement.model.CurrentAccount;
import bankmanagement.model.SavingsAccount;

public class MySQLDatabase implements DatabaseHandler {

    // ==========================================
    // CREATE - Save Account
    // ==========================================

    @Override
    public void saveAccount(Account account) {

        String sql =
                "INSERT INTO accounts "
                + "(account_number, account_holder_name, "
                + "account_type, balance, interest_rate, "
                + "minimum_balance) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    account.getAccountNumber()
            );

            statement.setString(
                    2,
                    account.getAccountHolderName()
            );

            statement.setString(
                    3,
                    account.getAccountType()
            );

            statement.setDouble(
                    4,
                    account.getBalance()
            );

            // Savings Account
            if (account instanceof SavingsAccount) {

                SavingsAccount savingsAccount =
                        (SavingsAccount) account;

                statement.setDouble(
                        5,
                        savingsAccount.getInterestRate()
                );

                statement.setNull(
                        6,
                        java.sql.Types.DOUBLE
                );
            }

            // Current Account
            else if (account instanceof CurrentAccount) {

                CurrentAccount currentAccount =
                        (CurrentAccount) account;

                statement.setNull(
                        5,
                        java.sql.Types.DOUBLE
                );

                statement.setDouble(
                        6,
                        currentAccount.getMinimumBalance()
                );
            }

            statement.executeUpdate();

            System.out.println(
                    "Account saved successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error saving account: "
                    + e.getMessage()
            );
        }
    }

    // ==========================================
    // READ - Find Account
    // ==========================================

    @Override
    public Account findAccount(int accountNumber) {

        String sql =
                "SELECT * FROM accounts "
                + "WHERE account_number = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    accountNumber
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return createAccountFromResultSet(
                        resultSet
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding account: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // ==========================================
    // READ - Get All Accounts
    // ==========================================

    @Override
    public List<Account> getAllAccounts() {

        List<Account> accounts =
                new ArrayList<>();

        String sql =
                "SELECT * FROM accounts";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Account account =
                        createAccountFromResultSet(
                                resultSet
                        );

                accounts.add(account);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving accounts: "
                    + e.getMessage()
            );
        }

        return accounts;
    }

    // ==========================================
    // UPDATE - Update Account Balance
    // ==========================================

    @Override
    public boolean updateAccount(Account account) {

        String sql =
                "UPDATE accounts "
                + "SET balance = ? "
                + "WHERE account_number = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setDouble(
                    1,
                    account.getBalance()
            );

            statement.setInt(
                    2,
                    account.getAccountNumber()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Account updated successfully."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating account: "
                    + e.getMessage()
            );
        }

        return false;
    }

    // ==========================================
    // DELETE - Delete Account
    // ==========================================

    @Override
    public boolean deleteAccount(int accountNumber) {

        String sql =
                "DELETE FROM accounts "
                + "WHERE account_number = ?";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    accountNumber
            );

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Account deleted successfully."
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting account: "
                    + e.getMessage()
            );
        }

        return false;
    }

    // ==========================================
    // Convert Database Record to Java Object
    // ==========================================

    private Account createAccountFromResultSet(
            ResultSet resultSet
    ) throws SQLException {

        int accountNumber =
                resultSet.getInt(
                        "account_number"
                );

        String accountHolderName =
                resultSet.getString(
                        "account_holder_name"
                );

        String accountType =
                resultSet.getString(
                        "account_type"
                );

        double balance =
                resultSet.getDouble(
                        "balance"
                );

        // Create Savings Account
        if (accountType.equals(
                "Savings Account")) {

            double interestRate =
                    resultSet.getDouble(
                            "interest_rate"
                    );

            return new SavingsAccount(
                    accountNumber,
                    accountHolderName,
                    balance,
                    interestRate
            );
        }

        // Create Current Account
        else {

            double minimumBalance =
                    resultSet.getDouble(
                            "minimum_balance"
                    );

            return new CurrentAccount(
                    accountNumber,
                    accountHolderName,
                    balance,
                    minimumBalance
            );
        }
    }

    // ==========================================
    // Save Transaction
    // ==========================================

    @Override
    public void saveTransaction(
            int accountNumber,
            String transactionType,
            double amount
    ) {

        String sql =
                "INSERT INTO transactions "
                + "(account_number, transaction_type, amount) "
                + "VALUES (?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    accountNumber
            );

            statement.setString(
                    2,
                    transactionType
            );

            statement.setDouble(
                    3,
                    amount
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error saving transaction: "
                    + e.getMessage()
            );
        }
    }
    @Override
    public void getTransactionHistory(int accountNumber) {

        String sql =
                "SELECT transaction_id, "
                + "transaction_type, "
                + "amount, "
                + "transaction_date "
                + "FROM transactions "
                + "WHERE account_number = ? "
                + "ORDER BY transaction_date DESC";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    accountNumber
            );

            ResultSet resultSet =
                    statement.executeQuery();

            boolean found = false;

            System.out.println(
                    "\n========== TRANSACTION HISTORY =========="
            );

            System.out.println(
                    "Account Number: " + accountNumber
            );

            System.out.println(
                    "-----------------------------------------"
            );

            while (resultSet.next()) {

                found = true;

                int transactionId =
                        resultSet.getInt(
                                "transaction_id"
                        );

                String transactionType =
                        resultSet.getString(
                                "transaction_type"
                        );

                double amount =
                        resultSet.getDouble(
                                "amount"
                        );

                String transactionDate =
                        resultSet.getTimestamp(
                                "transaction_date"
                        ).toString();

                System.out.println(
                        "Transaction ID : "
                        + transactionId
                );

                System.out.println(
                        "Type           : "
                        + transactionType
                );

                System.out.println(
                        "Amount         : "
                        + amount
                );

                System.out.println(
                        "Date           : "
                        + transactionDate
                );

                System.out.println(
                        "-----------------------------------------"
                );
            }

            if (!found) {

                System.out.println(
                        "No transactions found."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving transaction history: "
                    + e.getMessage()
            );
        }
    }
}