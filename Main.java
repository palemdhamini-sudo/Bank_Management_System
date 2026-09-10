package bankmanagement;

import java.util.Scanner;

import bankmanagement.database.DatabaseHandler;
import bankmanagement.database.InMemoryDatabase;
import bankmanagement.model.Account;
import bankmanagement.model.CurrentAccount;
import bankmanagement.model.SavingsAccount;
import bankmanagement.service.BankTransactionService;
import bankmanagement.service.TransactionService;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static DatabaseHandler database = new InMemoryDatabase();

    private static TransactionService transactionService =
            new BankTransactionService(database);

    private static int nextAccountNumber = 1001;

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            try {

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

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
                        System.out.println("\nThank you for using Bank Management System.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (Exception e) {

                System.out.println("Invalid input. Please enter a valid value.");
                scanner.nextLine();
                choice = 0;
            }

        } while (choice != 10);

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println("\n=================================");
        System.out.println("     BANK MANAGEMENT SYSTEM");
        System.out.println("=================================");

        System.out.println("1. Create Savings Account");
        System.out.println("2. Create Current Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. Check Balance");
        System.out.println("7. Display Account Details");
        System.out.println("8. Calculate Interest");
        System.out.println("9. Delete Account");
        System.out.println("10. Exit");

        System.out.println("=================================");
    }

    private static void createSavingsAccount() {

        scanner.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Initial Deposit: ");
        double balance = scanner.nextDouble();

        if (balance < 0) {
            System.out.println("Initial deposit cannot be negative.");
            return;
        }

        double interestRate = 4.0;

        SavingsAccount account = new SavingsAccount(
                nextAccountNumber,
                name,
                balance,
                interestRate
        );

        database.saveAccount(account);

        System.out.println("\nSavings Account created successfully!");
        System.out.println("Account Number: " + nextAccountNumber);

        nextAccountNumber++;
    }

    private static void createCurrentAccount() {

        scanner.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Initial Deposit: ");
        double balance = scanner.nextDouble();

        double minimumBalance = 1000;

        if (balance < minimumBalance) {

            System.out.println(
                    "Initial deposit must be at least "
                    + minimumBalance
            );

            return;
        }

        CurrentAccount account = new CurrentAccount(
                nextAccountNumber,
                name,
                balance,
                minimumBalance
        );

        database.saveAccount(account);

        System.out.println("\nCurrent Account created successfully!");
        System.out.println("Account Number: " + nextAccountNumber);

        nextAccountNumber++;
    }

    private static void depositMoney() {

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Enter Deposit Amount: ");
        double amount = scanner.nextDouble();

        transactionService.deposit(accountNumber, amount);
    }

    private static void withdrawMoney() {

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Enter Withdrawal Amount: ");
        double amount = scanner.nextDouble();

        transactionService.withdraw(accountNumber, amount);
    }

    private static void transferMoney() {

        System.out.print("Enter Sender Account Number: ");
        int sender = scanner.nextInt();

        System.out.print("Enter Receiver Account Number: ");
        int receiver = scanner.nextInt();

        System.out.print("Enter Transfer Amount: ");
        double amount = scanner.nextDouble();

        transactionService.transfer(sender, receiver, amount);
    }

    private static void checkBalance() {

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        transactionService.checkBalance(accountNumber);
    }

    private static void displayAccountDetails() {

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        Account account = database.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.displayAccountDetails();

        if (account instanceof CurrentAccount) {

            CurrentAccount currentAccount =
                    (CurrentAccount) account;

            System.out.println(
                    "Minimum Balance: "
                    + currentAccount.getMinimumBalance()
            );
        }

        if (account instanceof SavingsAccount) {

            SavingsAccount savingsAccount =
                    (SavingsAccount) account;

            System.out.println(
                    "Calculated Interest: "
                    + savingsAccount.calculateInterest()
            );
        }
    }

    private static void calculateInterest() {

        System.out.print("Enter Savings Account Number: ");
        int accountNumber = scanner.nextInt();

        Account account = database.findAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (account instanceof SavingsAccount) {

            SavingsAccount savingsAccount =
                    (SavingsAccount) account;

            savingsAccount.displayInterest();

        } else {

            System.out.println(
                    "Interest calculation is only available "
                    + "for Savings Accounts."
            );
        }
    }

    private static void deleteAccount() {

        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();

        boolean deleted = database.deleteAccount(accountNumber);

        if (deleted) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found.");
        }
    }
}