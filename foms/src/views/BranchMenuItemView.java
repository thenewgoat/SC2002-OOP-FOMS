package views;

import interfaces.IBranchMenuItemView;
import models.BranchMenuItem;

public class BranchMenuItemView implements IBranchMenuItemView{

    public BranchMenuItemView() {
    }
    
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
