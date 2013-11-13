package creditcard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ConvertTask implements Runnable {

    private final BlockingQueue<String> lineQueue;
    private final BlockingQueue<CreditCard> creditCardQueue;
    public static long index = 0;

    public ConvertTask(BlockingQueue<String> lineQueue, BlockingQueue<CreditCard> creditCardQueue) {
        this.lineQueue = lineQueue;
        this.creditCardQueue = creditCardQueue;
    }

    @Override
    public void run() {
        String line;
        while (true) {
            try {
                // block if the queue is empty
                line = lineQueue.take();
                CreditCard card = CSVMapper.map(CSVReader.tokenize(line,","));
                //System.out.println("convert card "+index++ +" credit card queue "+creditCardQueue.remainingCapacity() + " line queue " + lineQueue.remainingCapacity());

                creditCardQueue.offer(card, 365, TimeUnit.DAYS);
            } catch (InterruptedException ex) {
                break;
            }
        }
        // poll() returns null if the queue is empty
        while ((line = lineQueue.poll()) != null) {
            CreditCard card = CSVMapper.map(CSVReader.tokenize(line,","));
            try {
                creditCardQueue.offer(card,365, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
