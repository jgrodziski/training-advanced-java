package creditcard;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public class CreditCardNumber {

    private String number;
    private static Pattern DIGIT_AND_SIZE_PATTERN = Pattern.compile("([0-9])*");
    private boolean isChecksumValid;

    public CreditCardNumber(String number) {
        checkNotNull(number);
        checkOnlyDigits(number);
        assertChecksumIsValid(number);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public boolean validateChecksum() {
        return assertChecksumIsValid(number);
    }

    public static void checkOnlyDigits(String number){
        if ( ! DIGIT_AND_SIZE_PATTERN.matcher(number).matches() ){
            throw new IllegalArgumentException("number "+number+" must contain only digits and size between 13 and 16 digits");
        }
    }

    public static void checkSizeBetween(String number, int min, int max){
        if (number.length() < min || number.length() > max){
            throw new IllegalArgumentException("credit card number must contains between "+min+" and "+max+" digits, the number "+number+" contains "+number.length()+" digits");
        }
    }

    private static int[] getChecksums(String number){
        int oddSum  = 0;
        int evenSum = 0;
        for (int i = 0; i < number.length() ; i++ ) {
            int b = digitAt(number, i);
            if ((i & 1) == 0) { //even pair
                String result = Integer.toString(b * 2);
                int resultSumOfDigits = sumDigits(result);
                evenSum += resultSumOfDigits;
            } else { //odd
                oddSum += b;
            }
        }
        return new int[]{oddSum,evenSum};
    }

    public static boolean validateChecksum(String number){
        int[] checksums = getChecksums(number);
        int oddSum  = checksums[0];
        int evenSum = checksums[1];
        return (oddSum + evenSum) % 10 == 0;
    }

    public static int calculateChecksum(String number){
        int[] checksums = getChecksums(number);
        int oddSum  = checksums[0];
        int evenSum = checksums[1];
        return ((oddSum + evenSum)*9) % 10;
    }

    private static int digitAt(String string, int index){
        return Integer.valueOf(new String(new char[]{string.charAt(index)}));
    }

    public static boolean assertChecksumIsValid(String number){
        int calculatedChecksum = calculateChecksum(number.substring(0, number.length() - 1));
        boolean resultChecksumEquality = digitAt(number, number.length()-1) == calculatedChecksum;
        boolean resultRemainderIsZero = validateChecksum(number);
        return resultChecksumEquality && resultRemainderIsZero;
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
