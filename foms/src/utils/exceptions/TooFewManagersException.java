package utils.exceptions;

public class TooFewManagersException extends IllegalArgumentException {
    public TooFewManagersException(String message) {
        super(message);
    }
}