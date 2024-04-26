package utils.exceptions;

/**
 * The {@link PasswordIncorrectException} is thrown when the provided password is incorrect.
 * It extends the {@link Exception} class.
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
