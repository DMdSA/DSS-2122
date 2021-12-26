package BusinessLayer;

import BusinessLayer.Client.Client;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.Hierarchy;
import DataBase.ClientDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface IWorkers {

    /**
     * Clients ------------------
     */
    Client getClientByNif(String nif);
    Client getClientByPhone(String phone);
    boolean hasClient(String nif);
    boolean registerClient(Client c);
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
    boolean registerBudgetRequest(Service service);

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

    void save();
    WorkersFacade load() throws IOException, ClassNotFoundException;

}
