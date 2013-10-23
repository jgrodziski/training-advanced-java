import creditcard.CreditCardNumber;
import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 *
 */
public class CreditCardNumberTest {


    @Test
    public void givenAValidNumberShouldReturnAValidChecksum(){
        try {
            CreditCardNumber number = new CreditCardNumber("4973029706404906");
        } catch(Exception e){
            fail();
        }
    }


    @Test
    public void givenAnInvalidNumberShouldReturnAValidChecksum(){
        String number = "30062718604329";
        Assert.assertTrue("Checksum for number " + number + " is not valid", CreditCardNumber.assertChecksumIsValid(number));
    }

    @Test
    public void givenNumbersShouldReturnAValidChecksum() throws IOException, InterruptedException {
        long tick = System.currentTimeMillis();
        List<List<String>> lines = CreditCardNumberFileTest.getrows("credit-card-numbers.csv");
        System.out.println("read "+lines.size()+" lines in "+(System.currentTimeMillis()-tick)+" ms");
        tick = System.currentTimeMillis();
        List<CreditCardNumber> result = new ArrayList<CreditCardNumber>(lines.size());
        for (List<String> line : lines) {
            CreditCardNumber number = new CreditCardNumber( line.get(1));
            number.validateChecksum();
            //System.out.println("number "+number+" is validated? "+number.validateChecksum());
            result.add(number);

        }
        System.out.println("validate "+lines.size()+" lines in "+(System.currentTimeMillis()-tick)+" ms");

        //Assert.assertTrue("Checksum for number "+number+" is not valid", creditcard.CreditCardNumber.assertChecksumIsValid(number));
    }
}
