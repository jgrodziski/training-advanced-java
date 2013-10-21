import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 */
public class ValidateAndSumTask implements Callable<Integer> {
    List<CreditCard> cards = null;

    public ValidateAndSumTask(List<CreditCard> cards) {
        this.cards = cards;
    }

    public List<CreditCard> getCards() {
        return cards;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (CreditCard card : cards) {
            card.getNumber().validateChecksum();
            sum += card.getBalance();
        }
        return sum ;
    }
}