import java.util.List;

/**
 *
 */
public class CSVMapper {

    public CreditCardNumber map(List<String> row){
        String issuerNetwork = row.get(0);
        String cardNumber = row.get(1);
        return new CreditCardNumber(issuerNetwork, cardNumber);
    }
}
