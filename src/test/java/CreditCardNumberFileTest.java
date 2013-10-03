import junit.framework.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 *
 */
public class CreditCardNumberFileTest {

    @Test
    public void givenAFileWithNumbersShouldReturnACollection() throws IOException {
        List<List<String>> lines = CSVReader.parse(getFileReader(), ",");
        Assert.assertNotNull(lines);
    }

    public static List<List<String>> getrows() throws IOException {
        List<List<String>> lines = CSVReader.parse(getFileReader(), ",");
        lines.remove(0);
        return lines;
    }

    private static Reader getFileReader(){
        InputStream stream = CreditCardNumberFileTest.class.getResourceAsStream("credit-card-numbers.csv");
        return new InputStreamReader(stream, Charset.forName("utf-8"));
    }
}
