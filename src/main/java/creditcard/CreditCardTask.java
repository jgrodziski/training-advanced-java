package creditcard;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class CreditCardTask implements Callable<Integer> {

    private BlockingQueue<CreditCard> queue = null;
    public static AtomicReference<Integer> max = new AtomicReference<Integer>(0);

    public CreditCardTask(BlockingQueue<CreditCard> queue) {
        this.queue = queue;
    }

    @Override
    public Integer call() throws Exception {
        CreditCard card;
        while (true) {
            try {
                // block if the queue is empty
                card = queue.take();
                //System.out.println("evaluate max balance "+max + " card queue"+queue.remainingCapacity());
                Integer currentMax = max.get();
                while ((card.getBalance() > currentMax) &&
                        ! max.compareAndSet(currentMax,card.getBalance())){
                    currentMax = max.get();
                }

            } catch (InterruptedException ex) {
                break;
            }
        }
        // poll() returns null if the queue is empty
        while ((card = queue.poll()) != null) {
            Integer currentMax = max.get();
            while ((card.getBalance() > currentMax) &&
                    ! max.compareAndSet(currentMax,card.getBalance())){
                currentMax = max.get();
            }
        }
        return max.get();
    }
}
