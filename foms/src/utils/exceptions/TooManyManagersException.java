package utils;

public class TooManyManagersException extends IllegalArgumentException {
    public TooManyManagersException(String message) {
        super(message);
    }
}
