package utils;

public class TooFewManagersException extends IllegalArgumentException {
    public TooFewManagersException(String message) {
        super(message);
    }
}