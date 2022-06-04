// In this class we describe what can be done with our bank accounts
public class Account {

    private int balance = 10000; // example balance of each account

    // we can add money to account
    public void deposit(int amount) {
        balance += amount;
    }

    // we can take mone from account
    public void withdraw(int amount) {
        balance -= amount;
    }

    // we can get balance of account
    public int getBalance() {
        return balance;
    }

    // we can transfer money from account to account
    public static void transfer(Account account1, Account account2, int amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}
