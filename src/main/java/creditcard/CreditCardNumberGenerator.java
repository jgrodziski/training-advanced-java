package creditcard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 *
 */
public class CreditCardNumberGenerator {

     public static CreditCardNumber generate(String issuerNetwork, int size) {
        String number = generate(size-1);
        int checksum = CreditCardNumber.calculateChecksum(number);
        return new CreditCardNumber(number + String.valueOf(checksum));
    }

    private static String generate(int size){
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < size ; i++) {
                int n = rnd.nextInt(10);
                sb.append(n);
            }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Map<String ,Integer> size = new HashMap(4);
        size.put("Visa", 16);
        size.put("MasterCard", 16);
        size.put("American Express", 14);
        size.put("Diners Club", 14);
        Map<String , Integer> balanceRanges = new HashMap(4);
        balanceRanges.put("Visa", 1500);
        balanceRanges.put("MasterCard", 1000);
        balanceRanges.put("American Express", 5000);
        balanceRanges.put("Diners Club", 1000);

        Writer w = new BufferedWriter(new FileWriter("/tmp/numbers.csv"));
        Random rnd = new Random();

        for (String network : size.keySet()) {
            List<List<String>> rows = new ArrayList<List<String>>(500000);
            for (int i = 0 ;  i < 500000 ; i++) {
                List<String> row = new ArrayList<String>(2);
                CreditCardNumber n = CreditCardNumberGenerator.generate(network, size.get(network));

                row.add(n.getNumber());
                Integer balance = rnd.nextInt(balanceRanges.get(network));
                row.add(balance.toString());
                rows.add(row);
            }
            CSVWriter.format(w,rows);
        }
        w.close();
    }
}
