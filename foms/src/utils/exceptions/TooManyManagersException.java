package utils.exceptions;

/**
 * The {@link TooManyManagersException} is thrown when there are too many managers in a branch.
 * It extends the {@link IllegalArgumentException} class.
 */
public class TooManyManagersException extends IllegalArgumentException {
    /**
     * Constructs a new TooManyManagersException with the specified detail message.
     *
     * @param message the detail message
     */
    public TooManyManagersException(String message) {
        super(message);
    }
}
