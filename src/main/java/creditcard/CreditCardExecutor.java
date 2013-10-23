package creditcard;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Collections.*;

/**
 *
 */
public class CreditCardExecutor {
    public static final int NB_PARTITIONS = 8;

    public static final Integer findMax(List<CreditCard> creditCards) throws InterruptedException, ExecutionException {
        List<List<CreditCard>> partitions = Lists.partition(creditCards, NB_PARTITIONS);
        List<Callable<Integer>> tasks = getTasks(partitions);
        ExecutorService executor = Executors.newFixedThreadPool(NB_PARTITIONS);

        List<Future<Integer>> resultats = executor.invokeAll(tasks);
        System.out.println("après l'invocation, mais avant le get sur le future");

        List<Integer> maxDesPartitions = new ArrayList<Integer>(NB_PARTITIONS);
        for (Future<Integer> resultat : resultats) {
            maxDesPartitions.add(resultat.get());
        }
        System.out.println("après le get du résultat");
        return max(maxDesPartitions);
    }

    private static List<Callable<Integer>> getTasks(List<List<CreditCard>> partitions){
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>(NB_PARTITIONS);
        for (List<CreditCard> partition : partitions) {
            tasks.add(new MaxTask(partition));
        }
        return tasks;
    }
}
