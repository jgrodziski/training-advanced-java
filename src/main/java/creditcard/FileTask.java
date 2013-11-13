package creditcard;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class FileTask implements Runnable {

    private final BlockingQueue<String> queue;

    public FileTask(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File("src/main/resources/credit-card-numbers.csv")));
            String line;
            br.readLine();//consume first header line
            while ((line = br.readLine()) != null) {
                // block if the queue is full
                queue.offer(line, 365, TimeUnit.DAYS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
