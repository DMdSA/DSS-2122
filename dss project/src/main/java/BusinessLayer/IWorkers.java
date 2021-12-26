package BusinessLayer;

import BusinessLayer.Client.Client;
import BusinessLayer.Workers.Hierarchy;
import DataBase.ClientDAO;

import java.util.Map;

public interface IWorkers {

    /**
     * Clients ------------------
     */
    Client getClientByNif(String nif);
    Client getClientByPhone(String phone);

    boolean registerClient();
    boolean updateClient();
    boolean removeClient();
    boolean hasClients();
    public ClientDAO getClients();

    /**
     * COUNTER ------------------
     */

    boolean hasWorkers();
    boolean hasWorkers(Hierarchy h);

    boolean registerExpressService();
    boolean registerBudgetRequest();

    //void consultExpressService();
    void consultExpressServices();
    boolean hasExpressServices();

    boolean consultBudgetRequests();
    boolean hasBudgetRequests();
    boolean updateBudgetStatus();

    boolean checkTechAvailability();

    boolean login();
    void startSystem();
    void exitSystem();

}
