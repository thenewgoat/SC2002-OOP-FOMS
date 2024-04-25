package interfaces;

import models.Branch;

public interface IWelcomeService {

    public void refresh();

    public Branch[] getBranchList();
}
