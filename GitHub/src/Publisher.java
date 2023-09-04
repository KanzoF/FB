import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Publisher implements Runnable {
    private final List<BlockingQueue<String>> queues;

    public Publisher(List<BlockingQueue<String>> queues) {
        this.queues = queues;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (Main.activeConsumers == 0) {
                    // Czekaj na aktywnego konsumenta
                    Thread.sleep(1000);
                }
                String message = "Message";
                for (BlockingQueue<String> queue : queues) {
                    queue.put(message);
                }
                System.out.println("Sent message to all Consumers.");
                Thread.sleep(5000);  // Symulacja opóźnienia
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

