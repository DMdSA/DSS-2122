package BusinessLayer;
import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.ProcessingCenterDAO;
import DataBase.WorkersDAO;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkersFacade implements IWorkers, Serializable {

    /**
     * Instance Variables
     */
    private ClientDAO clients_dao;
    private WorkersDAO workers_dao;
    private ProcessingCenterDAO processingCenter_dao;
    private static final String path = "C:\\Users\\diogo\\Ambiente de Trabalho\\UNIVERSIDADE MINHO\\3ano\\1semestre\\Desenvolvimento Sistemas Software\\Trabalho Pratico\\dss project\\src\\main\\java\\DataBase\\workers_facade";


    public WorkersFacade() throws IOException, ClassNotFoundException {
        this.load();
    }

    public WorkersFacade(ClientDAO clientdao, WorkersDAO workersdao, ProcessingCenterDAO processingCenterdao){
        this.clients_dao = clientdao;
        this.workers_dao = workersdao;
        this.processingCenter_dao = processingCenterdao;
    }

    public WorkersDAO getWorkers(){

        return this.workers_dao;    //todo clone ??
    }


    /**
     * CLIENTS ------------------
     */

    /**
     * Get a client by his NIF/ID
     * @param nif
     * @return
     */
    @Override
    public Client getClientByNif(String nif) {
        return clients_dao.getByNif(nif);
    }

    /**
     * Get a client by his phone number
     * @param phone
     * @return
     */
    @Override
    public Client getClientByPhone(String phone){
        return clients_dao.getByPhone(phone);
    }

    /**
     * Register a new client
     * @param c
     * @return
     */
    @Override
    public boolean registerClient(Client c) {

        if((this.clients_dao.getByNif(c.getNIF()) != null)) {
            System.out.println("error: Client already exists");
            return false;
        }
        this.clients_dao.add(c.clone());
        return true;
    }

    /**
     * Verifies if a client exists, by his nif/id
     * @param nif
     * @return
     */
    @Override
    public boolean hasClient(String nif){
        return this.clients_dao.hasClient(nif);
    }

    /**
     * Updates a client
     * @return
     */
    @Override
    public boolean updateClient() {
        return false;
    }

    /**
     * removes a client
     * @return
     */
    @Override
    public boolean removeClient() {
        return false;
    }

    /**
     * Verifies if the system has any clients
     * @return
     */
    @Override
    public boolean hasClients(){
        return this.clients_dao.getSize() > 0;
    }

    /**
     * Gets the client's database
     * @return
     */
    @Override
    public ClientDAO getClients(){
        return this.clients_dao.clone();     // clone? todo
    }

    /**
     * COUNTER ------------------
     */


    public Worker hasWorker(String user, String pass){

        return this.workers_dao.confirmWorker(user, pass);
    }


    /**
     * Verifies if the system has a specific worker
     * @param h
     * @param user
     * @return
     */
    public boolean hasWorker(Hierarchy h, String user){
        return this.workers_dao.hasWorker(h, user);
    }

    /**
     * Get a worker by his hierarchy and user
     * @param h
     * @param user
     * @return
     */
    public Worker getWorker(Hierarchy h, String user){
        return this.workers_dao.get(h, user);
    }

    /**
     * Verifies if the system has any workers
     * @return
     */
    @Override
    public boolean hasWorkers(){
        return (this.workers_dao.getSize() > 0);
    }

    /**
     * Verifies if the system has any workers, by their hierarchy
     * @param h
     * @return
     */
    @Override
    public boolean hasWorkers(Hierarchy h){

        return (this.workers_dao.get(h).size() > 0);
    }

    /**
     * Registers a new express service
     * @return
     */
    @Override
    public boolean registerExpressService() {
        return false;
    }


    /**
     * Registers a new budget request / normal service
     * @param service
     * @return
     */
    @Override
    public boolean registerBudgetRequest(Service service) {

        boolean flag = this.processingCenter_dao.add(service.getBudget());
        if(flag) {
            if (this.workers_dao.get(Hierarchy.COUNTER).containsKey(service.getClientId())) {
                Counter c = (Counter) this.workers_dao.get(Hierarchy.COUNTER, service.getEquipment().counterResponsible());
                c.updateRececoes(this.clients_dao.getByNif(service.getClientId()));
                this.workers_dao.update(c);
            }
            return true;
        }
        return false;
    }

    /**
     * Get a budget by it's client's ID/NIF
     * @param nif
     * @return
     */
    public List<Budget> getBudgetbyNif(String nif){
        return this.processingCenter_dao.get(nif);
    }

    /**
     *
     */
    @Override
    public void consultExpressServices() {
    }

    @Override
    public boolean hasExpressServices(){
        return false;
        // TODO TODO TODO
    }

    @Override
    public boolean consultBudgetRequests(){
        return false;
        // TODO TODO TODO
        // um COUNTER_WORKER só deve atualizar de "sem budget" -> aceite / não aceite
        //                                     de "à espera de entrega" -> entregue
    }

    /**
     * Verifies if the system has any budgets registered
     * @return
     */
    @Override
    public boolean hasBudgetRequests(){

        return (this.processingCenter_dao.globalSize() > 0);
    }


    /**
     * Gets all budgets associated with a specific budget status
     * @param bs
     * @return
     */
    public Map<UUID, Budget> getBudgetRequestsbyStatus(BudgetStatus bs){

        Map<UUID,Budget> status = this.processingCenter_dao.get_by_state(bs);
        Map<UUID, Budget> answer = new HashMap<>();
        for(Map.Entry<UUID, Budget> e : status.entrySet())
            answer.put(e.getKey(), e.getValue().clone());

        return answer;
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

    /**
     * MANAGER -------
     */

    public boolean registerWorker(Worker w){

        return this.workers_dao.add(w);
    }


    /**
     * Saves the system's state in an object file
     */
    @Override
    public void save(){

        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the system's state from an object file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public WorkersFacade load() throws IOException, ClassNotFoundException {

        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);

        Object o = input.readObject();

        if(o instanceof WorkersFacade){
            return (WorkersFacade) o;
        }
        return null;
    }
}