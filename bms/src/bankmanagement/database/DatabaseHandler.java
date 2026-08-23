package bankmanagement.database;

import java.util.List;

import bankmanagement.model.Account;

public interface DatabaseHandler {

    // CREATE
    void saveAccount(Account account);

    // READ
    Account findAccount(int accountNumber);

    List<Account> getAllAccounts();

    // UPDATE
    boolean updateAccount(Account account);

    // DELETE
    boolean deleteAccount(int accountNumber);

    // Transaction
    void saveTransaction(
            int accountNumber,
            String transactionType,
            double amount
    );
    void getTransactionHistory(
            int accountNumber
    );
}