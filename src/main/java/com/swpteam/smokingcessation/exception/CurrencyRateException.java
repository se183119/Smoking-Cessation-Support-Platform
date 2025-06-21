package com.swpteam.smokingcessation.exception;

public class CurrencyRateException extends RuntimeException {

    public CurrencyRateException(String message) {
        super(message);
    }

    public CurrencyRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
