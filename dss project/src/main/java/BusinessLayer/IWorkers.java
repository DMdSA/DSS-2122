package BusinessLayer;

import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;

import java.io.IOException;

public interface IWorkers {

    /**
     * Clients ------------------
     */
    ClientDAO getClients();

    boolean registerClient(Client c);

    boolean removeClient();

    Client getClientByNif(String nif);

    Client getClientByPhone(String phone);

    boolean hasClient(String nif);

    boolean hasClients();

    boolean updateClient(Client c);

    boolean updateClientPhone(Client c, String phone);

    /**
     * WORKERS ------------------
     */

    WorkersDAO getWorkers();

    boolean registerWorker(Worker w);

    boolean updateDeliveryCounter(String username, String clientID);

    Worker hasWorker(String user, String pass);

    boolean hasWorker(Hierarchy h, String user);

    boolean hasWorkers();

    boolean hasWorkers(Hierarchy h);

    Worker getWorker(Hierarchy h, String user);

    boolean updateWorkerPhone(Hierarchy h, Worker w, String phone);

    public boolean updateClientMail(Client c, String mail);

    boolean updateWorkerNif(Hierarchy h, Worker w, String nif);

    boolean updateWorkerPass(Hierarchy h, Worker w, String pass);

    boolean updateWorkerName(Hierarchy h, Worker w, String name);

    boolean checkTechAvailability();

    boolean login();

    void startSystem();

    void exitSystem();

    void save();

    WorkersFacade load() throws IOException, ClassNotFoundException;

}
