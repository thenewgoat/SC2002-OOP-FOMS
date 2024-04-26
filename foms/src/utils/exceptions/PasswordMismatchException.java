package utils.exceptions;

/**
 * The {@link PasswordMismatchException} is thrown when the provided passwords do not match.
 * It extends the {@link Exception} class.
 */
public class PasswordMismatchException extends Exception {
    /**
     * Constructs a new PasswordMismatchException with the specified detail message.
     *
     * @param message the detail message
     */
    public PasswordMismatchException(String message) {
        super(message);
    }
}