import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public class CreditCardNumber {

    private String issuerNetwork;
    private String number;
    private static Pattern DIGIT_PATTERN = Pattern.compile("([0-9])*");
    private boolean isChecksumValid;

    public CreditCardNumber(String issuerNetwork, String number) {
        checkNotNull(number, issuerNetwork);
        checkSizeBetween(number, 13, 16);
        checkOnlyDigits(number);
        this.number = number;
        this.issuerNetwork = issuerNetwork;
    }

    public String getIssuerNetwork() {
        return issuerNetwork;
    }

    public String getNumber() {
        return number;
    }

    public boolean validateChecksum() {
        return isChecksumValid(number);
    }

    public boolean isChecksumValid() {
        return isChecksumValid(number);
    }

    public static void checkOnlyDigits(String number){
        if ( ! DIGIT_PATTERN.matcher(number).matches() ){
            throw new IllegalArgumentException("number "+number+" must contain only digits");
        }
    }

    public static void checkSizeBetween(String number, int min, int max){
        if (number.length() < min || number.length() > max){
            throw new IllegalArgumentException("credit card number must contains between "+min+" and "+max+" digits, the number "+number+" contains "+number.length()+" digits");
        }
    }

    public static int calculateChecksum(String number){
        int oddSum = 0;
        int evenSum = 0;
        for (int i = 0; i < number.length() ; i++ ) {
            int b = digitAt(number, i);
            if ((i & 1) == 0) { //odd
                oddSum += b;
            } else { //even
                String result = Integer.toString(b * 2);
                int resultSumOfDigits = sumDigits(result);
                evenSum += resultSumOfDigits;
            }
        }
        return ((oddSum + evenSum)*9) % 10;
    }

    private static int digitAt(String string, int index){
        return Integer.valueOf(new String(new char[]{string.charAt(index)}));
    }

    public static boolean isChecksumValid(String number, int checksum){
        return digitAt(number, number.length()-1) == checksum;
    }

    public static boolean isChecksumValid(String number){
        return isChecksumValid(number, calculateChecksum(number.substring(0, number.length() - 1)));
    }

    private static int sumDigits(String number){
        checkOnlyDigits(number);
        int sum = 0;
        for(int i = 0; i< number.length(); i++){
            sum += Byte.valueOf(new String(new char[]{number.charAt(i)}));
        }
        return sum;
    }

    @Override
    public String toString() {
        return number;
    }
}
