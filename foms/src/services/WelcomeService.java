package services;

import interfaces.IWelcomeService;
import models.Branch;
import stores.*;

/**
 * Implements the IWelcomeService interface to provide functionalities related to
 * refreshing application data and retrieving branch information. This service handles
 * operations such as saving and loading data for various storage components used within
 * the application, which includes user, branch, and order data.
 */
public class WelcomeService implements IWelcomeService {

    /**
     * <p>Refreshes the application data by performing a save followed by a load operation
     * for each storage type. This ensures that all storage objects are up-to-date with the
     * latest changes and are synchronized with their respective storage mechanisms (e.g., database,
     * file system). The operations are performed in a specific order to maintain data integrity and
     * consistency across different storage objects.
     *
     * <p>Operations are performed as follows:
     * <p>1. Save operations for all storages to persist current state.
     * <p>2. Load operations for all storages to refresh in-memory data.
     */
    @Override
    public void refresh() {
        BranchUserStorage.save();
        BranchStorage.save();
        UserStorage.save();
        OrderStorage.save();
        PasswordStorage.save();
        PaymentMethodStorage.save();
        BranchMenuItemStorage.save();
        
        BranchUserStorage.load();
        BranchStorage.load();
        UserStorage.load();
        OrderStorage.load();
        PasswordStorage.load();
        PaymentMethodStorage.load();
        BranchMenuItemStorage.load();
    }

    /**
     * Retrieves a list of all branches managed within the application. This method
     * utilizes the BranchStorage to obtain an array of Branch objects, each representing
     * a different branch location. It provides an overview of the branches currently available
     * for operations or customer interaction within the application.
     *
     * @return an array of Branch objects, each representing a distinct branch.
     */
    @Override
    public Branch[] getBranchList() {
        return BranchStorage.getAll();
    }
}
