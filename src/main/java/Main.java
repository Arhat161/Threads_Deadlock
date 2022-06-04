// In this example, we are transferring money between two accounts using two streams
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Runner runner = new Runner(); // object 'runner', where we job our work

        // create first thread
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.firstThread(); // first thread
            }
        });
        // create second thread
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.secondThread(); // second thread
            }
        });

        thread1.start(); // start first thread
        thread2.start(); // start second thread

        thread1.join(); // join thread1 to main thread
        thread2.join(); // join thread2 to main thread

        runner.finished(); // job some finish work (show balance)
    }
}
