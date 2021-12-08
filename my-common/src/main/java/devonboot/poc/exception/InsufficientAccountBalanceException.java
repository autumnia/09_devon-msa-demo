package devonboot.poc.exception;

import devonframe.exception.BusinessException;

public class InsufficientAccountBalanceException extends BusinessException {

    public InsufficientAccountBalanceException() {
        super();
    }

    public InsufficientAccountBalanceException(String message) {
        setMessage(message);
    }
}
