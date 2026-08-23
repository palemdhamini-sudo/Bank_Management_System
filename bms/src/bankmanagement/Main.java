package bankmanagement;

import java.util.List;
import java.util.Scanner;

import bankmanagement.database.DatabaseHandler;
import bankmanagement.database.MySQLDatabase;
import bankmanagement.model.Account;
import bankmanagement.model.CurrentAccount;
import bankmanagement.model.SavingsAccount;
import bankmanagement.service.BankTransactionService;
import bankmanagement.service.TransactionService;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static DatabaseHandler database =
            new MySQLDatabase();

    private static TransactionService transactionService =
            new BankTransactionService(database);

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            System.out.println();

            switch (choice) {

                case 1:
                    createSavingsAccount();
                    break;

                case 2:
                    createCurrentAccount();
                    break;

                case 3:
                    depositMoney();
                    break;

                case 4:
                    withdrawMoney();
                    break;

                case 5:
                    transferMoney();
                    break;

                case 6:
                    checkBalance();
                    break;

                case 7:
                    displayAccountDetails();
                    break;

                case 8:
                    calculateInterest();
                    break;

                case 9:
                    deleteAccount();
                    break;

                case 10:
                    displayAllAccounts();
                    break;

                case 11:
                    viewTransactionHistory();
                    break;

                case 12:
                    System.out.println(
                            "Thank you for using "
                            + "Bank Management System."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. "
                            + "Please try again."
                    );
            }

        } while (choice != 12);

        scanner.close();
    }

    // ==========================================
    // DISPLAY MENU
    // ==========================================

    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "       BANK MANAGEMENT SYSTEM"
        );
        System.out.println(
                "========================================"
        );

        System.out.println(
                "1. Create Savings Account"
        );

        System.out.println(
                "2. Create Current Account"
        );

        System.out.println(
                "3. Deposit Money"
        );

        System.out.println(
                "4. Withdraw Money"
        );

        System.out.println(
                "5. Transfer Money"
        );

        System.out.println(
                "6. Check Balance"
        );

        System.out.println(
                "7. Display Account Details"
        );

        System.out.println(
                "8. Calculate Interest"
        );

        System.out.println(
                "9. Delete Account"
        );

        System.out.println(
                "10. Display All Accounts"
        );

        System.out.println(
                "11. View Transaction History"
        );

        System.out.println(
                "12. Exit"
        );

        System.out.println(
                "========================================"
        );
    }

    // ==========================================
    // CREATE SAVINGS ACCOUNT
    // ==========================================

    private static void createSavingsAccount() {

        System.out.println(
                "----- Create Savings Account -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        scanner.nextLine();

        System.out.print(
                "Enter Account Holder Name: "
        );

        String accountHolderName =
                scanner.nextLine();

        System.out.print(
                "Enter Initial Deposit: "
        );

        double balance =
                scanner.nextDouble();

        System.out.print(
                "Enter Interest Rate (%): "
        );

        double interestRate =
                scanner.nextDouble();

        // Check whether account already exists
        Account existingAccount =
                database.findAccount(accountNumber);

        if (existingAccount != null) {

            System.out.println(
                    "Account number already exists."
            );

            return;
        }

        SavingsAccount account =
                new SavingsAccount(
                        accountNumber,
                        accountHolderName,
                        balance,
                        interestRate
                );

        database.saveAccount(account);

        System.out.println(
                "Savings Account created successfully!"
        );

        System.out.println(
                "Account Number: "
                        + accountNumber
        );
    }

    // ==========================================
    // CREATE CURRENT ACCOUNT
    // ==========================================

    private static void createCurrentAccount() {

        System.out.println(
                "----- Create Current Account -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        scanner.nextLine();

        System.out.print(
                "Enter Account Holder Name: "
        );

        String accountHolderName =
                scanner.nextLine();

        System.out.print(
                "Enter Initial Deposit: "
        );

        double balance =
                scanner.nextDouble();

        System.out.print(
                "Enter Minimum Balance: "
        );

        double minimumBalance =
                scanner.nextDouble();

        // Check whether account already exists
        Account existingAccount =
                database.findAccount(accountNumber);

        if (existingAccount != null) {

            System.out.println(
                    "Account number already exists."
            );

            return;
        }

        CurrentAccount account =
                new CurrentAccount(
                        accountNumber,
                        accountHolderName,
                        balance,
                        minimumBalance
                );

        database.saveAccount(account);

        System.out.println(
                "Current Account created successfully!"
        );

        System.out.println(
                "Account Number: "
                        + accountNumber
        );
    }

    // ==========================================
    // DEPOSIT MONEY
    // ==========================================

    private static void depositMoney() {

        System.out.println(
                "----- Deposit Money -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        System.out.print(
                "Enter Deposit Amount: "
        );

        double amount =
                scanner.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Amount must be greater than zero."
            );

            return;
        }

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        transactionService.deposit(
                accountNumber,
                amount
        );
    }

    // ==========================================
    // WITHDRAW MONEY
    // ==========================================

    private static void withdrawMoney() {

        System.out.println(
                "----- Withdraw Money -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        System.out.print(
                "Enter Withdrawal Amount: "
        );

        double amount =
                scanner.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Amount must be greater than zero."
            );

            return;
        }

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        transactionService.withdraw(
                accountNumber,
                amount
        );
    }

    // ==========================================
    // TRANSFER MONEY
    // ==========================================

    private static void transferMoney() {

        System.out.println(
                "----- Transfer Money -----"
        );

        System.out.print(
                "Enter Sender Account Number: "
        );

        int fromAccount =
                scanner.nextInt();

        System.out.print(
                "Enter Receiver Account Number: "
        );

        int toAccount =
                scanner.nextInt();

        System.out.print(
                "Enter Transfer Amount: "
        );

        double amount =
                scanner.nextDouble();

        if (amount <= 0) {

            System.out.println(
                    "Amount must be greater than zero."
            );

            return;
        }

        if (fromAccount == toAccount) {

            System.out.println(
                    "Sender and receiver "
                    + "accounts cannot be the same."
            );

            return;
        }

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

        transactionService.transfer(
                fromAccount,
                toAccount,
                amount
        );
    }

    // ==========================================
    // CHECK BALANCE
    // ==========================================

    private static void checkBalance() {

        System.out.println(
                "----- Check Balance -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );

        System.out.println(
                "Account Holder: "
                        + account.getAccountHolderName()
        );

        System.out.println(
                "Current Balance: ₹"
                        + account.getBalance()
        );
    }

    // ==========================================
    // DISPLAY ACCOUNT DETAILS
    // ==========================================

    private static void displayAccountDetails() {

        System.out.println(
                "----- Account Details -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        account.displayAccountDetails();
    }

    // ==========================================
    // CALCULATE INTEREST
    // ==========================================

    private static void calculateInterest() {

        System.out.println(
                "----- Calculate Interest -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        if (account instanceof SavingsAccount) {

            SavingsAccount savingsAccount =
                    (SavingsAccount) account;

            double interest =
                    savingsAccount.calculateInterest();

            System.out.println(
                    "Account Number: "
                            + accountNumber
            );

            System.out.println(
                    "Current Balance: ₹"
                            + account.getBalance()
            );

            System.out.println(
                    "Interest Rate: "
                            + savingsAccount.getInterestRate()
                            + "%"
            );

            System.out.println(
                    "Calculated Interest: ₹"
                            + interest
            );

        } else {

            System.out.println(
                    "Interest calculation is "
                    + "available only for "
                    + "Savings Accounts."
            );
        }
    }

    // ==========================================
    // DELETE ACCOUNT
    // ==========================================

    private static void deleteAccount() {

        System.out.println(
                "----- Delete Account -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        System.out.println(
                "Account Holder: "
                        + account.getAccountHolderName()
        );

        System.out.println(
                "Current Balance: ₹"
                        + account.getBalance()
        );

        System.out.print(
                "Are you sure you want to "
                + "delete this account? (yes/no): "
        );

        scanner.nextLine();

        String confirmation =
                scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            boolean deleted =
                    database.deleteAccount(
                            accountNumber
                    );

            if (!deleted) {

                System.out.println(
                        "Account could not be deleted."
                );
            }

        } else {

            System.out.println(
                    "Account deletion cancelled."
            );
        }
    }

    // ==========================================
    // DISPLAY ALL ACCOUNTS
    // ==========================================

    private static void displayAllAccounts() {

        System.out.println(
                "----- All Accounts -----"
        );

        List<Account> accounts =
                database.getAllAccounts();

        if (accounts.isEmpty()) {

            System.out.println(
                    "No accounts found."
            );

            return;
        }

        for (Account account : accounts) {

            System.out.println(
                    "--------------------------------"
            );

            System.out.println(
                    "Account Number: "
                            + account.getAccountNumber()
            );

            System.out.println(
                    "Account Holder: "
                            + account.getAccountHolderName()
            );

            System.out.println(
                    "Account Type: "
                            + account.getAccountType()
            );

            System.out.println(
                    "Balance: ₹"
                            + account.getBalance()
            );
        }

        System.out.println(
                "--------------------------------"
        );
    }

    // ==========================================
    // VIEW TRANSACTION HISTORY
    // ==========================================

    private static void viewTransactionHistory() {

        System.out.println(
                "----- Transaction History -----"
        );

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                scanner.nextInt();

        Account account =
                database.findAccount(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return;
        }

        database.getTransactionHistory(
                accountNumber
        );
    }
}