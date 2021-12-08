package devonboot.poc.exception;

import devonframe.exception.BusinessException;

public class InsufficientPointBalanceException extends BusinessException {

    public InsufficientPointBalanceException() {
        super();
    }

    public InsufficientPointBalanceException(String message) {
        setMessage(message);
    }
}
