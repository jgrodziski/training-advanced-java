import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *
 */
public class ForkJoinCreditCardsValidatorTest {

    @Test
    public void forkJoinTest() throws IOException {
        List<List<String>> lines = CreditCardNumberFileTest.getrows();
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinCreditCardsValidator validator = new ForkJoinCreditCardsValidator(lines.subList(1, lines.size()));
        long tick = System.currentTimeMillis();
        List<CreditCardNumber> numbers = pool.invoke(validator);
        System.out.println("validate "+numbers.size()+" credit cards in "+(System.currentTimeMillis()-tick)+" ms");
    }


    @Test
    public void forkJoin2Test() throws IOException {
        List<List<String>> lines = CreditCardNumberFileTest.getrows();
        ForkJoinPool pool = new ForkJoinPool(8);
        List<CreditCardNumber> result = new ArrayList<CreditCardNumber>(lines.size());
        GlobalForkJoinCreditCardValidator validator = new GlobalForkJoinCreditCardValidator(lines, result, 0, 10000);
        long tick = System.currentTimeMillis();
        pool.invoke(validator);
        System.out.println("validate "+result.size()+" credit card in "+(System.currentTimeMillis()-tick)+" ms");
    }

}