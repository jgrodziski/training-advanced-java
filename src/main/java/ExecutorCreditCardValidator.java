import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ExecutorCreditCardValidator {

    public static ResultatVI countValidInvalid(List<CreditCard> allCards) throws InterruptedException, ExecutionException {
        List<List<CreditCard>> partition = partition(allCards, 32);
        List<CountValidTask> tasks = new ArrayList<CountValidTask>(32);

        for (List<CreditCard> creditCards : partition) {
            tasks.add(new CountValidTask(creditCards));
        }

        ExecutorService executor = Executors.newFixedThreadPool(8);

        List<Future<ResultatVI>> resultats = executor.invokeAll(tasks);

        ResultatVI total = new ResultatVI();
        for (Future<ResultatVI> resultat : resultats) {
            ResultatVI resultatVI = resultat.get();
            total.invalid += resultatVI.invalid;
            total.valid += resultatVI.valid;
        }
        return total;
    }


    public static void validate(List<CreditCard> cards) throws InterruptedException, ExecutionException {
        List<ValidateAndSumTask> tasks = new ArrayList<ValidateAndSumTask>(cards.size());

        for (List<CreditCard> chunk : partition(cards, 10000)) {
            tasks.add(new ValidateAndSumTask(chunk));
        }

        ExecutorService executor = Executors.newFixedThreadPool(8);

        List<Future<Integer>> listSum = executor.invokeAll(tasks);
        //Les résultats sont récupérés dans la liste ci-dessus puis parcouru un par un pour constituer le reportingAdvisory
        int sum = 0;
        for(Future<Integer> future : listSum){
            Integer sumOfBalance = future.get();
            sum += sumOfBalance ;
        }

        System.out.println("List des soldes est de "+sum);


    }

    private static List<List<CreditCard>> partition(List<CreditCard> cards, int chunkSize){
        int chunks = cards.size() / chunkSize;
        List<List<CreditCard>> partition = new ArrayList<List<CreditCard>>();
        int lo = 0;
        int hi = chunkSize;
        for(int i = 0 ; i < chunks-1 ; i++){
            partition.add(cards.subList(lo, hi));
            lo += chunkSize;
            hi += chunkSize;
        }
        partition.add(cards.subList(lo, cards.size()));
        return partition;
    }






}
