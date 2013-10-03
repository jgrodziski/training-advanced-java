import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 *
 */
public class GlobalForkJoinCreditCardValidator extends RecursiveAction {

    private final List<List<String>> rows;
    private final List<CreditCardNumber> allResults;
    public static final CSVMapper mapper = new CSVMapper();
    public static final int THRESHOLD = 10000;
    private final int hi;
    private final int lo;

    public GlobalForkJoinCreditCardValidator(List<List<String>> rows, List<CreditCardNumber> allResults, int lo, int hi) {
        this.rows = rows;
        this.allResults = allResults;
        this.hi = hi;
        this.lo = lo;
    }

    @Override
    protected void compute() {
        if (hi+THRESHOLD+1 <= rows.size()){
            GlobalForkJoinCreditCardValidator v1 = new GlobalForkJoinCreditCardValidator(rows, allResults, hi+1, hi+1+THRESHOLD);
            invokeAll(v1);
        }
        List<List<String>> chunk = rows.subList(lo,hi);
        for (List<String> row : chunk) {
            CreditCardNumber number = mapper.map(row);
            number.validateChecksum();
            allResults.add(number);
            //System.out.println("CreditCardNumber "+ count++ + " , number "+number+" validated ? " +number.isChecksumValid());
        }

    }
}
