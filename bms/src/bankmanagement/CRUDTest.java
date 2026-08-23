package bankmanagement;

import bankmanagement.database.DatabaseHandler;
import bankmanagement.database.MySQLDatabase;
import bankmanagement.model.SavingsAccount;

public class CRUDTest {

    public static void main(String[] args) {

        DatabaseHandler database =
                new MySQLDatabase();

        // ==========================
        // CREATE
        // ==========================

        SavingsAccount account =
                new SavingsAccount(
                        1001,
                        "Dhamini",
                        5000,
                        4.0
                );

        database.saveAccount(account);

        // ==========================
        // READ
        // ==========================

        System.out.println(
                "\nReading account..."
        );

        var foundAccount =
                database.findAccount(1001);

        if (foundAccount != null) {

            foundAccount.displayAccountDetails();

        } else {

            System.out.println(
                    "Account not found."
            );
        }

        // ==========================
        // UPDATE
        // ==========================

        System.out.println(
                "\nUpdating account..."
        );

        account.deposit(2000);

        database.updateAccount(account);

        // ==========================
        // READ AGAIN
        // ==========================

        System.out.println(
                "\nChecking updated balance..."
        );

        var updatedAccount =
                database.findAccount(1001);

        if (updatedAccount != null) {

            updatedAccount.displayAccountDetails();
        }

        // ==========================
        // DELETE
        // ==========================

        System.out.println(
                "\nDeleting account..."
        );

        database.deleteAccount(1001);

        // ==========================
        // CHECK AFTER DELETE
        // ==========================

        System.out.println(
                "\nChecking account after deletion..."
        );

        var deletedAccount =
                database.findAccount(1001);

        if (deletedAccount == null) {

            System.out.println(
                    "Account no longer exists."
            );
        }
    }
}