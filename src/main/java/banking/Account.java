package banking;

/**
 * Abstract bank account.
 */
public abstract class Account implements AccountInterface {
    private final AccountHolder accountHolder;
    private final Long accountNumber;
    private final int pin;
    private double balance;

    protected Account(AccountHolder accountHolder, Long accountNumber, int pin, double startingDeposit) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = startingDeposit;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public boolean validatePin(int attemptedPin) {
        return pin == attemptedPin;
    }

    public double getBalance() {
        return balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void creditAccount(double amount) {
        balance = balance + amount;
    }

    public boolean debitAccount(double amount) {

        if (amount > balance)
            return false;

        balance = balance - amount;

        return true;
    }
}
