/**
 *
 */
public class CreditCard {
    private CreditCardNumber number;
    private Integer balance;

    public CreditCard(CreditCardNumber number, Integer balance) {
        this.number = number;
        this.balance = balance;
    }

    public CreditCardNumber getNumber() {
        return number;
    }

    public Integer getBalance() {
        return balance;
    }
}

