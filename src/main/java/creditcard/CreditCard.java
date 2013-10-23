package creditcard;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public class CreditCard {
    private String issuerNetwork;
    private CreditCardNumber number;
    private Integer balance;

    public CreditCard(String issuerNetwork, CreditCardNumber number, Integer balance) {
        checkNotNull("","", issuerNetwork,number,balance);
        this.issuerNetwork = issuerNetwork;
        this.number = number;
        this.balance = balance;
    }

    public String getIssuerNetwork() {
        return issuerNetwork;
    }

    public CreditCardNumber getNumber() {
        return number;
    }

    public Integer getBalance() {
        return balance;
    }
}

