package creditcard;

import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 */
public class CountValidTask implements Callable<ResultatVI> {

    private List<CreditCard> cards = null;

    public CountValidTask(List<CreditCard> cards) {
        this.cards = cards;
    }

    public List<CreditCard> getCards() {
        return cards;
    }

    @Override
    public ResultatVI call() throws Exception {
        ResultatVI resultat = new ResultatVI();

        resultat.invalid = 0;
        resultat.valid = 0;

        for (CreditCard card : cards) {
            if (card.getNumber().validateChecksum()){
                resultat.valid++;
            } else {
                resultat.invalid++;
            }
        }
        return resultat;
    }
}
