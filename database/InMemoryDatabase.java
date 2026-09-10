package bankmanagement.database;

import java.util.ArrayList;
import java.util.List;

import bankmanagement.model.Account;

public class InMemoryDatabase implements DatabaseHandler {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public void saveAccount(Account account) {

        accounts.add(account);
    }

    @Override
    public Account findAccount(int accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }

    @Override
    public void updateAccount(Account account) {

        // Since objects are stored by reference,
        // changes are automatically reflected.
    }

    @Override
    public boolean deleteAccount(int accountNumber) {

        Account account = findAccount(accountNumber);

        if (account != null) {
            accounts.remove(account);
            return true;
        }

        return false;
    }
}