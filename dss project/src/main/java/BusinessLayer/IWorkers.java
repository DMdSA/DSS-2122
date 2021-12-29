package BusinessLayer;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.IOException;
import java.util.List;

public interface IWorkers {

    /**
    * -----------------------------------------------
    *          WORKERS
    * -----------------------------------------------
    */

    /**
     * Gets the workers database
     */
    WorkersDAO getWorkers();

    /**
     * Registers a new worker to database
     */
    boolean registerWorker(Worker w);

    /**
     * Atualiza as estatísticas do counter que efetuar uma entrega
     */
    boolean updateDeliveryCounter(String username, String clientID);

    /**
     * Verifies if the system has a specific Worker, by their user and password
     */
    Worker hasWorker(String user, String pass);

    /**
     * Verifies if the system has a specific worker
     */
    boolean hasWorker(Hierarchy h, String user);

    /**
     * Verifies if the system has any workers
     */
    boolean hasWorkers();

    /**
     * Verifies if the system has any workers, by their hierarchy
     */
    boolean hasWorkers(Hierarchy h);

    /**
     * Get a worker by his hierarchy and user
     */
    Worker getWorker(Hierarchy h, String user);

    /**
     * Gets a specific counter's statistics
     */
    Pair<Integer,Integer> getCounterStatistics(Counter c);

    /**
     * Updates a worker's phone number
     */
    boolean updateWorkerPhone(Hierarchy h, Worker w, String phone);

    /**
     * Updates a worker's nif
     */
    boolean updateWorkerNif(Hierarchy h, Worker w, String nif);

    /**
     * Updates a worker's password
     */
    boolean updateWorkerPass(Hierarchy h, Worker w, String pass);

    /**
     * Updates a worker's name
     */
    boolean updateWorkerName(Hierarchy h, Worker w, String name);

    /**
     * Verifies if the system has any technician available at the moment
     */
    boolean checkTechAvailability();

    /**
     * Updates a technician's availability
     */
    void update_worker_availability(String username, boolean flag);

    /**
     * Calculates a list of all counter workers with their number of receptions
     * and deliveries made to clients
     */
    List<Triplet<String, Integer, Integer>> getCountersStats();

    /**
     * Auxiliar Funcion to get the List of all tech's usernames
     */
    List<String> getListTechs();

   /**
    * -----------------------------------------------
    *          CLIENTS
    * -----------------------------------------------
   */

    /**
     * Gets the client's database
     */
   ClientDAO getClients();

    /**
     * Register a new client
     */
   boolean registerClient(Client c);

    /**
     * Get a client by his NIF/ID
     */
   Client getClientByNif(String nif);

    /**
     * Get a client by his phone number
     */
   Client getClientByPhone(String phone);

    /**
     * Verifies if a client exists, by his nif/id
     */
   boolean hasClient(String nif);

    /**
     * Verifies if the system has any clients
     */
   boolean hasClients();

    /**
     * Updates a client
     */
   boolean updateClient(Client c);

    /**
     * Updates a client's phone
     */
   boolean updateClientPhone(Client c, String phone);

    /**
     * Updates a client's mail
     */
   boolean updateClientMail(Client c, String mail);

   /**
    * -----------------------------------------------
    *          LOAD AND SAVE
    * -----------------------------------------------
    */

    /**
     * Saves the system's state in an object file
     */
   void save();

    /**
     * Loads the system's state from an object file
     */
   WorkersFacade load() throws IOException, ClassNotFoundException;
}
