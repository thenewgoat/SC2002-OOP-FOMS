package utils.exceptions;

/**
 * Exception thrown when a password does not match the expected value.
 */
public class PasswordIncorrectException extends Exception {

    /**
     * Constructs a new PasswordIncorrectException with the specified detail message.
     * @param message the detail message.
     */
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
