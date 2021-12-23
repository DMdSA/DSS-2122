package BusinessLayer;

import BusinessLayer.Client.Client;

public class WorkersFacade implements IWorkers{


    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public boolean registerClient() {
        return false;
    }

    @Override
    public boolean updateClient() {
        return false;
    }

    @Override
    public boolean removeClient() {
        return false;
    }

    @Override
    public boolean registerExpressService() {
        return false;
    }

    @Override
    public boolean registerBudgetRequest() {
        return false;
    }

    @Override
    public void consultExpressService() {

    }

    @Override
    public void consultExpressServices() {

    }

    @Override
    public boolean updateBudgetStatus() {
        return false;
    }

    @Override
    public boolean checkTechAvailability() {
        return false;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public void startSystem() {

    }

    @Override
    public void exitSystem() {

    }
}
