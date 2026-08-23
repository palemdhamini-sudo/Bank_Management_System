package bankmanagement.service;

public interface TransactionService {

    void deposit(int accountNumber, double amount);

    void withdraw(int accountNumber, double amount);

    void transfer(int fromAccount, int toAccount, double amount);

    void checkBalance(int accountNumber);
}