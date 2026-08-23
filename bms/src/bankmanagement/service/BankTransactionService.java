package bankmanagement.service;

import bankmanagement.database.DatabaseHandler;
import bankmanagement.model.Account;

public class BankTransactionService
        implements TransactionService {

    private DatabaseHandler database;

    public BankTransactionService(
            DatabaseHandler database) {

        this.database = database;
    }

    @Override
    public void deposit(int accountNumber, double amount) {

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");
            return;
        }

        if (amount <= 0) {

            System.out.println(
                    "Deposit amount must be greater than 0."
            );

            return;
        }

        account.deposit(amount);

        boolean updated =
                database.updateAccount(account);

        if (updated) {

            database.saveTransaction(
                    accountNumber,
                    "DEPOSIT",
                    amount
            );

            System.out.println(
                    "Database updated successfully."
            );
        }
    }

    @Override
    public void withdraw(int accountNumber,
                         double amount) {

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");
            return;
        }

        boolean success =
                account.withdraw(amount);

        if (success) {

            boolean updated =
                    database.updateAccount(account);

            if (updated) {

                database.saveTransaction(
                        accountNumber,
                        "WITHDRAW",
                        amount
                );

                System.out.println(
                        "Database updated successfully."
                );
            }
        }
    }

    @Override
    public void transfer(int fromAccount,
                         int toAccount,
                         double amount) {

        Account sender =
                database.findAccount(fromAccount);

        Account receiver =
                database.findAccount(toAccount);

        if (sender == null) {

            System.out.println(
                    "Sender account not found."
            );

            return;
        }

        if (receiver == null) {

            System.out.println(
                    "Receiver account not found."
            );

            return;
        }

        if (fromAccount == toAccount) {

            System.out.println(
                    "Cannot transfer money to the same account."
            );

            return;
        }

        if (amount <= 0) {

            System.out.println(
                    "Transfer amount must be greater than 0."
            );

            return;
        }

        boolean withdrawn =
                sender.withdraw(amount);

        if (!withdrawn) {

            return;
        }

        receiver.deposit(amount);

        boolean senderUpdated =
                database.updateAccount(sender);

        boolean receiverUpdated =
                database.updateAccount(receiver);

        if (senderUpdated && receiverUpdated) {

            database.saveTransaction(
                    fromAccount,
                    "TRANSFER_SENT",
                    amount
            );

            database.saveTransaction(
                    toAccount,
                    "TRANSFER_RECEIVED",
                    amount
            );

            System.out.println(
                    "Transfer successful."
            );

        } else {

            System.out.println(
                    "Transfer failed while updating database."
            );
        }
    }

    @Override
    public void checkBalance(int accountNumber) {

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        System.out.println("\n----- Balance -----");

        System.out.println(
                "Account Number : "
                + account.getAccountNumber()
        );

        System.out.println(
                "Account Holder : "
                + account.getAccountHolderName()
        );

        System.out.println(
                "Balance        : "
                + account.getBalance()
        );
    }
}