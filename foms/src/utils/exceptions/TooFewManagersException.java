package utils.exceptions;

/**
 * The {@link TooFewManagersException} is thrown when there are too few managers in a branch.
 * It extends the {@link IllegalArgumentException} class.
 */
public class TooFewManagersException extends IllegalArgumentException {
    /**
     * Constructs a new TooFewManagersException with the specified detail message.
     *
     * @param message the detail message
     */
    public TooFewManagersException(String message) {
        super(message);
    }
}