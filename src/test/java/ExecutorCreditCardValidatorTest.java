import creditcard.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class ExecutorCreditCardValidatorTest {

    @Test
    public void validateInParallel() throws IOException, ExecutionException, InterruptedException {
        List<List<String>> lines = CreditCardNumberFileTest.getrows("credit-card-numbers.csv");
        long tick = System.currentTimeMillis();
        ResultatVI resultat = ExecutorCreditCardValidator.countValidInvalid(getCreditCards(lines));
        System.out.println("resultat : invalid = "+resultat.invalid+" valid = "+resultat.valid);
        System.out.println("executed in "+(System.currentTimeMillis()-tick)+" ms");
    }

    @Test
    public void findMax() throws IOException, ExecutionException, InterruptedException {
        List<List<String>> lines = CreditCardNumberFileTest.getrows("credit-card-numbers.csv");
        List<CreditCard> creditCards = getCreditCards(lines);
        long tick = System.currentTimeMillis();
        Integer max = CreditCardExecutor.findMax(creditCards);
        System.out.println("Le max est de " +max);
    }

    private List<CreditCard> getCreditCards(List<List<String>> lines){
        List<CreditCard> cards = new ArrayList<CreditCard>(lines.size());
        for (List<String> line : lines) {
            cards.add(CSVMapper.map(line));
        }
        return cards;
    }
}
