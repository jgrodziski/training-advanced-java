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
        List<List<String>> lines = CSVReader.parse(getFileReader("credit-card-numbers.csv"), ",");
        Assert.assertNotNull(lines);
    }

    public static List<List<String>> getrows(String filename) throws IOException {
        List<List<String>> lines = CSVReader.parse(getFileReader(filename), ",");
        lines.remove(0);
        return lines;
    }

    private static Reader getFileReader(String filename){
        InputStream stream = CreditCardNumberFileTest.class.getResourceAsStream(filename);
        return new InputStreamReader(stream, Charset.forName("utf-8"));
    }
}
