package bankmanagement.service;

import bankmanagement.database.DatabaseHandler;
import bankmanagement.model.Account;

public class BankTransactionService implements TransactionService {

    private DatabaseHandler database;

    public BankTransactionService(DatabaseHandler database) {
        this.database = database;
    }

    @Override
    public void deposit(int accountNumber, double amount) {

        Account account = database.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.deposit(amount);
        database.updateAccount(account);
    }

    @Override
    public void withdraw(int accountNumber, double amount) {

        Account account = database.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        boolean success = account.withdraw(amount);

        if (success) {
            database.updateAccount(account);
        }
    }

    @Override
    public void transfer(int fromAccount, int toAccount, double amount) {

        Account sender = database.findAccount(fromAccount);
        Account receiver = database.findAccount(toAccount);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (fromAccount == toAccount) {
            System.out.println("Cannot transfer money to the same account.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than 0.");
            return;
        }

        // First withdraw from sender
        boolean success = sender.withdraw(amount);

        if (success) {
            receiver.deposit(amount);

            database.updateAccount(sender);
            database.updateAccount(receiver);

            System.out.println("Transfer successful.");
        }
    }

    @Override
    public void checkBalance(int accountNumber) {

        Account account = database.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Number : " + account.getAccountNumber());
        System.out.println("Current Balance: " + account.getBalance());
    }
}