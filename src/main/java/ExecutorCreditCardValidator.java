import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ExecutorCreditCardValidator {


    public static void validate(List<CreditCardNumber> numbers) throws InterruptedException, ExecutionException {
        List<ValidateTask> tasks = new ArrayList<ValidateTask>(numbers.size());
        for (CreditCardNumber number : numbers) {
            tasks.add(new ValidateTask(number));
        }

        ExecutorService executor = Executors.newFixedThreadPool(8);

        List<Future<CreditCardNumber>> numeroValides = executor.invokeAll(tasks);
        //Les résultats sont récupérés dans la liste ci-dessus puis parcouru un par un pour constituer le reportingAdvisory
        for(Future<CreditCardNumber> future : numeroValides){
            CreditCardNumber number = future.get();

        }


    }




}
