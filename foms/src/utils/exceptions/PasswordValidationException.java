package utils.exceptions;

/**
 * The {@link PasswordValidationException} is thrown when the provided password does not meet the requirements.
 * It extends the {@link Exception} class.
 */
public class PasswordValidationException extends Exception {
    /**
     * Constructs a new PasswordValidationException with the specified detail message.
     *
     * @param message the detail message
     */
    public PasswordValidationException(String message) {
        super(message);
    }
}
