package bankmanagement.model;

public class CurrentAccount extends Account {

    private double minimumBalance;

    public CurrentAccount(int accountNumber, String accountHolderName,
                          double balance, double minimumBalance) {

        super(accountNumber, accountHolderName, balance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0.");
            return false;
        }

        if (balance - amount < minimumBalance) {
            System.out.println("Withdrawal denied.");
            System.out.println("Minimum balance of " + minimumBalance
                    + " must be maintained.");
            return false;
        }

        balance -= amount;
        System.out.println("Withdrawal successful.");
        return true;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}