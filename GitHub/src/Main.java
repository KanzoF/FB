
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static int activeConsumers = 0;  // Zmienna do przechowywania liczby aktywnych konsumentów

    public static void main(String[] args) {
        List<BlockingQueue<String>> queues = new ArrayList<>();
        int numberOfConsumers = 4;

        // Create queues for each consumer
        for (int i = 0; i < numberOfConsumers; i++) {
            queues.add(new LinkedBlockingQueue<>());
        }

        // Create and start Publisher
        Publisher publisher = new Publisher(queues);
        new Thread(publisher).start();

        // Create and start Consumers
        for (int i = 0; i < numberOfConsumers; i++) {
            Consumer consumer = new Consumer("Consumer-" + (i + 1), queues.get(i));
            new Thread(consumer).start();
        }
    }
}
/*Zadanie03:
        Aplikacje mozesz odpalic jako PUBLIKATOR
        albo jako CONSUMER

        jesli odpalisz jako CONSUMER to jestes w trybie nasluchu i dostajesz wiadomosci publikowane przez PUBLIKATORA
        jesli odpalisz jako PUBLIKATOR to jestes w stanie wyslac jedna wiadomosc do wszystkich CONSUMEROW (aktywnie polaczonych)

        uwaga: gdy nie ma zadnego aktywnego consuemra, wiadomosc musi poczekac tak dlugo az sie jakis pojawi.

 */