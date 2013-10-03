import org.junit.Assume;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 *
 */
public class CreditCardGeneratorTest {

    @Test
    public void testCreditCardNumberGenerator() throws IOException {
        Map<String ,Integer> size = new HashMap(4);
        size.put("Visa", 16);
        size.put("MasterCard", 16);
        size.put("American Express", 15);
        size.put("Diners Club", 14);

        Writer w = new BufferedWriter(new FileWriter("/tmp/numbers.csv"));

        for (String network : size.keySet()) {
            List<List<String>> rows = new ArrayList<List<String>>(500000);
            Random rnd = new Random();
            for (int i = 0 ;  i < 500000 ; i++) {
                List<String> row = new ArrayList<String>(2);
                CreditCardNumber n = CreditCardNumberGenerator.generate(network, size.get(network));
                row.add(n.getIssuerNetwork());
                row.add(n.getNumber());
                row.add(String.valueOf(rnd.nextInt(1000)));
                rows.add(row);
            }
            CSVWriter.format(w,rows);
        }
        w.close();

    }
}
