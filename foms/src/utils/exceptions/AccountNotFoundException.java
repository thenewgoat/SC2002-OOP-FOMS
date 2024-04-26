package utils.exceptions;

/**
 * The {@link AccountNotFoundException} class is a custom exception that is thrown when an account is not found.
 * It extends the {@link Exception} class.
 */
public class AccountNotFoundException extends Exception {
    /**
     * Constructs a new AccountNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
