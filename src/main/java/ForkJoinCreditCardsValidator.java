import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 */
public class ForkJoinCreditCardsValidator extends RecursiveTask<List<CreditCardNumber>> {

    public static int count = 0;
    public static final CSVMapper mapper = new CSVMapper();
    public static final int THRESHOLD = 10000;
    List<List<String>> rows;

    public ForkJoinCreditCardsValidator(List<List<String>> rows) {
        this.rows = rows;
    }

    @Override
    protected List<CreditCardNumber> compute() {
        if (rows.size() <= THRESHOLD) {
            List<CreditCardNumber> computedNumbers = new ArrayList<CreditCardNumber>(rows.size());
            for (List<String> row : rows) {
                CreditCardNumber number = mapper.map(row);
                number.validateChecksum();
                computedNumbers.add(number);
                //System.out.println("CreditCardNumber "+ count++ + " , number "+number+" validated ? " +number.isChecksumValid());
            }
            return computedNumbers;
        } else {
            ForkJoinCreditCardsValidator v1 = new ForkJoinCreditCardsValidator(rows.subList(0, rows.size() / 2));
            v1.fork();
            ForkJoinCreditCardsValidator v2 = new ForkJoinCreditCardsValidator(rows.subList(rows.size() / 2, rows.size()));
            v2.invoke().addAll(v1.join());
            return v2.getRawResult();
        }
    }


}
