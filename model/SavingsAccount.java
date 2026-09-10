package bankmanagement.model;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(int accountNumber, String accountHolderName,
                          double balance, double interestRate) {

        super(accountNumber, accountHolderName, balance);
        this.interestRate = interestRate;
    }

    public double calculateInterest() {

        return balance * interestRate / 100;
    }

    public void displayInterest() {

        double interest = calculateInterest();

        System.out.println("Interest Rate : " + interestRate + "%");
        System.out.println("Interest      : " + interest);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}