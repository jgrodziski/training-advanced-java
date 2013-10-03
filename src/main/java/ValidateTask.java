import java.util.concurrent.Callable;

/**
 *
 */
public class ValidateTask implements Callable<CreditCardNumber> {
    CreditCardNumber number = null;

    ValidateTask(CreditCardNumber number) {
        this.number = number;
    }

    CreditCardNumber getNumber() {
        return number;
    }

    @Override
    public CreditCardNumber call() throws Exception {
        this.number.validateChecksum();
        return number;
    }
}