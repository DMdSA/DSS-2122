package BusinessLayer;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Technician;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;
import org.javatuples.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.Map;


public class WorkersFacade implements IWorkers, Serializable {

    /**
     * Instance Variables
     */
    private ClientDAO clients_dao;
    private WorkersDAO workers_dao;

    private static final String path = new String(Paths.get("").toAbsolutePath().toString()
            +File.separator +"src" + File.separator + "main" +File.separator
            + "java" + File.separator + "DataBase" + File.separator
            + "WORKERS_DB");


    public WorkersFacade() throws IOException, ClassNotFoundException {
        this.load();
    }

    public WorkersFacade(ClientDAO clientdao, WorkersDAO workersdao){
        this.clients_dao = clientdao;
        this.workers_dao = workersdao;
    }

    @Override
    public WorkersDAO getWorkers(){

        return this.workers_dao;    //todo clone ??
    }

    @Override
    public boolean registerWorker(Worker w){

        return this.workers_dao.add(w);
    }

    /**
     * Atualiza as estatísticas do counter que efetuar uma entrega
     * @param username
     * @param clientID
     * @return
     */
    @Override
    public boolean updateDeliveryCounter(String username, String clientID){
        Counter c = (Counter) this.workers_dao.get(Hierarchy.COUNTER, username);
        c.updateEntregas(clientID);
        return this.workers_dao.update(c);
    }

    /**
     * Verifies if the system has a specific Worker, by their user and password
     * @param user
     * @param pass
     * @return
     */
    @Override
    public Worker hasWorker(String user, String pass){

        return this.workers_dao.confirmWorker(user, pass);
    }

    /**
     * Verifies if the system has a specific worker
     * @param h
     * @param user
     * @return
     */
    @Override
    public boolean hasWorker(Hierarchy h, String user){
        return this.workers_dao.hasWorker(h, user);
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
     * Get a worker by his hierarchy and user
     * @param h
     * @param user
     * @return
     */
    @Override
    public Worker getWorker(Hierarchy h, String user){
        return this.workers_dao.get(h, user);
    }


    public Pair<Integer,Integer> getCounterStatistics(Counter c){

        int rececao = 0;
        int entrega = 0;

        Map<String, Pair<Integer,Integer>> stats = c.getStatistics();

        for(Map.Entry<String, Pair<Integer,Integer>> e : stats.entrySet()){

            rececao += e.getValue().getValue0();
            entrega += e.getValue().getValue1();
        }
     return new Pair<>(rececao, entrega);
    }


    @Override
    public boolean updateWorkerPhone(Hierarchy h, Worker w, String phone){
        w.setPhone(phone);
        return this.workers_dao.update(w);
    }

    @Override
    public boolean updateWorkerNif(Hierarchy h, Worker w, String nif){
        w.setNif(nif);
        return this.workers_dao.update(w);
    }

    @Override
    public boolean updateWorkerPass(Hierarchy h, Worker w, String pass){
        w.setPass(pass);
        return this.workers_dao.update(w);
    }

    @Override
    public boolean updateWorkerName(Hierarchy h, Worker w, String name){
        w.setName(name);
        return this.workers_dao.update(w);
    }
    /**
     * Verifies if the system has any technician available at the moment
     * @return
     */
    @Override
    public boolean checkTechAvailability() {

        return this.workers_dao.hasTechAvailable();
    }

    public void update_worker_availability(String username, boolean flag){
        Technician aux = (Technician) this.workers_dao.get(Hierarchy.TECHNICIAN, username);
        workers_dao.updateAvaibility(aux,flag);
    }




    /**
     * CLIENTS ------------------
     */

    /**
     * Gets the client's database
     * @return
     */
    @Override
    public ClientDAO getClients(){
        return this.clients_dao.clone();     // clone? todo
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
     * removes a client
     * @return
     */
    @Override
    public boolean removeClient() {
        return false;
    }


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
     * Verifies if a client exists, by his nif/id
     * @param nif
     * @return
     */
    @Override
    public boolean hasClient(String nif){
        return this.clients_dao.hasClient(nif);
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
     * Updates a client
     * @return
     */
    @Override
    public boolean updateClient(Client c) {

        String nif = c.getNIF();
        return this.clients_dao.update(c);
    }

    @Override
    public boolean updateClientPhone(Client c, String phone){
        c.setPhone(phone);
        return this.clients_dao.update(c);
    }

    @Override
    public boolean updateClientMail(Client c, String mail){
        c.setEmail(mail);
        return this.clients_dao.update(c);
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