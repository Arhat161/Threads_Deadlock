// this is the most important class in this project

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private final Account account1 = new Account(); // first account
    private final Account account2 = new Account(); // second account

    private Lock lock1 = new ReentrantLock(); // first lock
    private Lock lock2 = new ReentrantLock(); // second lock

    // in this method we show logic of taken locks and how we can not allow deadlock
    private void takeLocks(Lock lock1, Lock lock2) {

        boolean firstLockTaken = false; // the fist lock is taken ???
        boolean secondLockTaken = false; // the second lock is taken ???

        // in endless cycle we check locking, and we will exit from cycle only if lock1 taken and lock2 taken
        while (true) {
            // first we try to take two locks
            try {
                firstLockTaken = lock1.tryLock();
                secondLockTaken = lock2.tryLock();
            } finally {
                // if we take lock1, and we take lock2, then all ok, and we exit from method
                if (firstLockTaken && secondLockTaken) {
                    return;
                }
                // but if lock1 taken before us, we unlock lock1
                if (firstLockTaken) {
                    lock1.unlock();
                }
                // and if lock2 taken before us, we unlock lock2
                if (secondLockTaken) {
                    lock2.unlock();
                }
            }
            try {
                // we sleep just 1 milliseconds
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void firstThread() {

        Random random = new Random(); // for random money amount
        for (int i = 0; i < 10000; i++) {
            // before transfer money, we use takeLocks for take lock1 for account1 and take lock2 for account2
            // the method itself will determine which locks are taken and which are free
            takeLocks(lock1, lock2);
            // when locks taken, we make transfer
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock(); // unlock lock1
                lock2.unlock(); // unlock lock2
            }
        }
    }

    public void secondThread() {
        Random random = new Random(); // for random money amount
        for (int i = 0; i < 10000; i++) {
            // in second thread we take locks in other order - first take lock2, second take lock 1 -
            // the method itself will determine which locks are taken and which are free
            takeLocks(lock2, lock1);
            // when locks taken, we make transfer
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock(); // unlock lock1
                lock2.unlock(); // unlock lock2
            }
        }
    }

    // in this method we just show balance of account1 and balance of account 2, and sum of this two balance
    // we should get the sum of 20000 (balance account1 = 10000, and balance account2 = 10000),
    // and the amount of transfers can be any, and after ten thousand transfers of random amounts,
    // the balance of account1 and the balance of account2 may differ (10404 + 9596 = 20000)
    public void finished() {
        int account1Balance = account1.getBalance();
        int account2Balance = account2.getBalance();
        System.out.println(account1Balance);
        System.out.println(account2Balance);
        System.out.println("Total balance " + (account1Balance + account2Balance));
    }
}
