package BusinessLayer;

import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.Hierarchy;
import DataBase.ClientDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IWorkers {

    /**
     * Clients ------------------
     */
    Client getClientByNif(String nif);
    Client getClientByPhone(String phone);
    boolean hasClient(String nif);
    boolean registerClient(Client c);
    boolean updateClient(Client c);
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
    List<Object> consultExpressServices();
    boolean hasExpressServices();

    boolean consultBudgetRequests();
    boolean hasBudgetRequests();
    boolean updateBudgetStatus(Budget b, BudgetStatus bs);

    boolean checkTechAvailability();

    boolean login();
    void startSystem();
    void exitSystem();

    void save();
    WorkersFacade load() throws IOException, ClassNotFoundException;

}
