package interfaces;

/**
 * The IAuthorisationService interface provides methods for user authentication and authorization.
 */
public interface IAuthorisationService {

    /**
     * Authenticates a user with the provided login ID and password.
     *
     * @param staffLoginID The login ID of the staff member.
     * @param password The password of the staff member.
     * @return true if the login is successful, false otherwise.
     */
    public boolean login(String staffLoginID, String password);

}
