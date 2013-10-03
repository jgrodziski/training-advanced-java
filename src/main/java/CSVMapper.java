import java.util.List;

/**
 *
 */
public class CSVMapper {

    public CreditCard map(List<String> row){
        String issuerNetwork = row.get(0);
        String cardNumber = row.get(1);
        Integer balance = Integer.valueOf(row.get(2));
        return new CreditCard(new CreditCardNumber(issuerNetwork, cardNumber), balance);
    }
}
