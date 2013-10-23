package creditcard;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 */
public class MaxTask implements Callable<Integer> {

    List<CreditCard> cards = null;
    List<Integer> balances = null;

    public MaxTask(List<CreditCard> cards) {
        this.cards = cards;
        this.balances = new ArrayList<Integer>(cards.size());
        for (CreditCard card : cards) {
            balances.add(card.getBalance());
        }
    }

    @Override
    public Integer call() throws Exception {
        return Collections.max(balances);
    }
}