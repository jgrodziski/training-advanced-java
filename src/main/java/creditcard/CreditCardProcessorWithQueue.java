package creditcard;

import java.util.concurrent.*;

/**
 *
 */
public class CreditCardProcessorWithQueue {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        final int threadCount = 16;

        // BlockingQueue with a capacity of 200
        BlockingQueue<String> lineQueue = new ArrayBlockingQueue<String>(400);
        BlockingQueue<CreditCard> creditCardqueue = new ArrayBlockingQueue<CreditCard>(400);

        // create thread pool with given size
        ExecutorService service = Executors.newFixedThreadPool(threadCount);

        long before = System.currentTimeMillis();
        for (int i = 0; i < (threadCount/2 - 1); i++) {
            service.submit(new ConvertTask(lineQueue,creditCardqueue));
        }

        // Wait til FileTask completes

        for (int i = 0; i < (threadCount/2 - 1); i++) {
            service.submit(new CreditCardTask(creditCardqueue));
        }
        service.submit(new FileTask(lineQueue)).get();
        System.out.println("no more lines !");
        service.shutdownNow();

        // Wait til Tasks terminate
        service.awaitTermination(365, TimeUnit.DAYS);
        System.out.println("Max balance is "+CreditCardTask.max);
        System.out.println("It took "+((System.currentTimeMillis() - before) / 1000.0)+" s to process 2M lines");
    }
}
