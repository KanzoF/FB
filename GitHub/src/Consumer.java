



import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    private final String name;
    private final BlockingQueue<String> queue;

    public Consumer(String name, BlockingQueue<String> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Main.activeConsumers++;  // Zwiększ liczbę aktywnych konsumentów
            while (true) {
                String message = queue.take();
                System.out.println(name + " received: " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Main.activeConsumers--;  // Zmniejsz liczbę aktywnych konsumentów
        }
    }
}
