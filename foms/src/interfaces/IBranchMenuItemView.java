package interfaces;

import models.BranchMenuItem;

/**
 * The IBranchMenuItemView interface provides methods to display branch menu items.
 */
public interface IBranchMenuItemView {

    /**
     * Displays the given branch menu item at the specified index.
     * 
     * @param item The branch menu item to be displayed.
     * @param index The index at which the branch menu item should be displayed.
     */
    public void displayBranchMenuItem(BranchMenuItem item, int index);
}
