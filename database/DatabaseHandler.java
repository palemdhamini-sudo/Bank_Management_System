package bankmanagement.database;

import bankmanagement.model.Account;

public interface DatabaseHandler {

    void saveAccount(Account account);

    Account findAccount(int accountNumber);

    void updateAccount(Account account);

    boolean deleteAccount(int accountNumber);
}