package views;

import interfaces.IBranchMenuItemView;
import models.BranchMenuItem;

/**
 * The {@link BranchMenuItemView} class implements the IBranchMenuItemView interface and is responsible for displaying branch menu item details.
 */
public class BranchMenuItemView implements IBranchMenuItemView{

    /**
     * Displays the details of the given branch menu item.
     *
     * @param item a BranchMenuItem object
     * @param index the index of the item in the list
     */
    @Override
    public void displayBranchMenuItem(BranchMenuItem item, int index) {
        System.out.println("--------------------------------------------------");
        System.out.println("Item Number: " + index);
        System.out.println("Item Name: " + item.getName());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Price: " + item.getPrice());
        System.out.println("Availability: " + item.getAvailability());
        System.out.println("Description: " + item.getDescription());
        System.out.println("--------------------------------------------------");
    }
}
