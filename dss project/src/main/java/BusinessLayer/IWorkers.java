package BusinessLayer;

import BusinessLayer.Client.Client;

public interface IWorkers {

    Client getClient();
    boolean registerClient();
    boolean updateClient();
    boolean removeClient();


    boolean registerExpressService();
    boolean registerBudgetRequest();

    void consultExpressService();
    void consultExpressServices();

    boolean updateBudgetStatus();

    boolean checkTechAvailability();

    boolean login();
    void startSystem();
    void exitSystem();

}
