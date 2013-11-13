import creditcard.CSVMapper;
import creditcard.CreditCard;
import creditcard.CreditCardNumber;
import creditcard.ForkJoinCreditCard;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *
 */
public class ForkJoinTest {

    @Test
    public void forkJoinTest() throws IOException {
        List<CreditCard> cards = getCreditCards(CreditCardNumberFileTest.getrows("credit-card-numbers.csv"));
        ForkJoinPool pool = new ForkJoinPool(4);
        ForkJoinCreditCard validator = new ForkJoinCreditCard(cards);
        long tick = System.currentTimeMillis();
        List<CreditCardNumber> numbers = pool.invoke(validator);
        System.out.println("validate "+numbers.size()+" credit cards in "+(System.currentTimeMillis()-tick)+" ms");
    }

    private List<CreditCard> getCreditCards(List<List<String>> lines){
        List<CreditCard> cards = new ArrayList<CreditCard>(lines.size());
        for (List<String> line : lines) {
            cards.add(CSVMapper.map(line));
        }
        return cards;
    }
}
