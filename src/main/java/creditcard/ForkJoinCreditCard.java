package creditcard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 */
public class ForkJoinCreditCard extends RecursiveTask<List<CreditCardNumber>> {

    public static final int THRESHOLD = 10000;
    List<CreditCard> creditCards;

    public ForkJoinCreditCard(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    protected List<CreditCardNumber> compute() {
        if (creditCards.size() <= THRESHOLD) {
            List<CreditCardNumber> computedNumbers = new ArrayList<CreditCardNumber>(creditCards.size());
            for (CreditCard card : creditCards) {
                CreditCardNumber number = card.getNumber();
                number.validateChecksum();
                computedNumbers.add(number);
                //System.out.println("CreditCardNumber "+ count++ + " , number "+number+" validated ? " +number.isChecksumValid());
            }
            return computedNumbers;
        } else {
            ForkJoinCreditCard v1 = new ForkJoinCreditCard(creditCards.subList(0, creditCards.size() / 2));
            v1.fork();
            ForkJoinCreditCard v2 = new ForkJoinCreditCard(creditCards.subList(creditCards.size() / 2, creditCards.size()));
            v2.invoke().addAll(v1.join());
            return v2.getRawResult();
        }
    }


}