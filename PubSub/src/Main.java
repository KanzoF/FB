import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> bqueue = new LinkedBlockingQueue<>();
        CopyOnWriteArrayList<BlockingQueue<Integer>> consumerQueues = new CopyOnWriteArrayList<>();

        producer p1 = new producer(bqueue, consumerQueues);
        consumer c1 = new consumer(consumerQueues);
        consumer c2 = new consumer(consumerQueues);

        Thread pThread = new Thread(p1);
        Thread cThread1 = new Thread(c1);
        Thread cThread2 = new Thread(c2);

        pThread.start();
        cThread1.start();


        Thread.sleep(2000);
        cThread2.start();
    }
}

class producer implements Runnable {
    BlockingQueue<Integer> globalQueue;
    CopyOnWriteArrayList<BlockingQueue<Integer>> consumerQueues;

    public producer(BlockingQueue<Integer> globalQueue, CopyOnWriteArrayList<BlockingQueue<Integer>> consumerQueues) {
        this.globalQueue = globalQueue;
        this.consumerQueues = consumerQueues;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            try {
                globalQueue.put(i);
                for (BlockingQueue<Integer> queue : consumerQueues) {
                    queue.put(i);
                }
                System.out.println("Wyprodukowano " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class consumer implements Runnable {
    CopyOnWriteArrayList<BlockingQueue<Integer>> consumerQueues;
    BlockingQueue<Integer> myQueue = new LinkedBlockingQueue<>();

    public consumer(CopyOnWriteArrayList<BlockingQueue<Integer>> consumerQueues) {
        this.consumerQueues = consumerQueues;
        consumerQueues.add(myQueue);
    }

    @Override
    public void run() {
        System.out.println("Wątek konsumenta " + Thread.currentThread().getName() + " staje się aktywny");
        int taken = -1;
        while (taken != 4) {
            try {
                taken = myQueue.take();
                System.out.println("Skonsumowano przez " + Thread.currentThread().getName() + ": " + taken);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
Aplikacje mozesz odpalic jako PUBLIKATOR
albo jako CONSUMER

jesli odpalisz jako CONSUMER to jestes w trybie nasluchu i dostajesz wiadomosci publikowane przez PUBLIKATORA
jesli odpalisz jako PUBLIKATOR to jestes w stanie wyslac jedna wiadomosc do wszystkich CONSUMEROW (aktywnie polaczonych)

uwaga: gdy nie ma zadnego aktywnego consuemra, wiadomosc musi poczekac tak dlugo az sie jakis pojawi.
 */