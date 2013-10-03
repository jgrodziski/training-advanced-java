import java.util.Random;

/**
 *
 */
public class CreditCardNumberGenerator {

    public static CreditCardNumber generate(String issuerNetwork, int size) {
        String number = generate(size-1);
        int checksum = CreditCardNumber.calculateChecksum(number);
        return new CreditCardNumber(issuerNetwork, number + checksum);
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
}
