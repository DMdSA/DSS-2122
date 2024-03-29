package BusinessLayer;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Technician;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WorkersFacade implements IWorkers, Serializable {

    /**
     * Instance Variables
     */
    private ClientDAO clients_dao;                          // clients database
    private WorkersDAO workers_dao;                         // workers database

    // auxiliar path to save/load the system's state
    private static final String path = new String(Paths.get("").toAbsolutePath().toString()
            +File.separator +"src" + File.separator + "main" +File.separator
            + "java" + File.separator + "DataBase" + File.separator
            + "WORKERS_DB");


    /**
     * Constructors
     */
    public WorkersFacade() throws IOException, ClassNotFoundException {
        this.load();
    }

    /**
     * Constructor with specified database
     */
    public WorkersFacade(ClientDAO clientdao, WorkersDAO workersdao){
        this.clients_dao = clientdao;
        this.workers_dao = workersdao;
    }

    /**
     * -----------------------------------------------
     *          WORKERS
     * -----------------------------------------------
     */


    /**
     * Gets the workers database
     * @return
     */
    public WorkersDAO getWorkers(){

        return this.workers_dao.clone();
    }

    /**
     * Registers a new worker to database
     * @param w
     * @return
     */
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

    /**
     * Gets a specific counter's statistics
     * @param c
     * @return
     */
    public Pair<Integer,Integer> getCounterStatistics(Counter c){

        int rececao = 0;
        int entrega = 0;

        // map<username, Pair< nrececoes, nentregas>>
        Map<String, Pair<Integer,Integer>> stats = c.getStatistics();

        // somatório efetuado para cada cliente que possa ter atendido
        for(Map.Entry<String, Pair<Integer,Integer>> e : stats.entrySet()){

            rececao += e.getValue().getValue0();
            entrega += e.getValue().getValue1();
        }
     return new Pair<>(rececao, entrega);
    }

    /**
     * Updates a worker's phone number
     * @param h
     * @param w
     * @param phone
     * @return
     */
    public boolean updateWorkerPhone(Hierarchy h, Worker w, String phone){
        w.setPhone(phone);
        return this.workers_dao.update(w);
    }

    /**
     * Updates a worker's nif
     * @param h
     * @param w
     * @param nif
     * @return
     */
    public boolean updateWorkerNif(Hierarchy h, Worker w, String nif){
        w.setNif(nif);
        return this.workers_dao.update(w);
    }

    /**
     * Updates a worker's password
     * @param h
     * @param w
     * @param pass
     * @return
     */
    public boolean updateWorkerPass(Hierarchy h, Worker w, String pass){
        w.setPass(pass);
        return this.workers_dao.update(w);
    }

    /**
     * Updates a worker's name
     * @param h
     * @param w
     * @param name
     * @return
     */
    public boolean updateWorkerName(Hierarchy h, Worker w, String name){

        // change their name
        w.setName(name);
        // update the new worker
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

    /**
     * Updates a technician's availability
     * @param username
     * @param flag
     */
    public void update_worker_availability(String username, boolean flag){
        Technician aux = (Technician) this.workers_dao.get(Hierarchy.TECHNICIAN, username);
        workers_dao.updateAvaibility(aux,flag);
    }

    /**
     * Calculates a list of all counter workers with their number of receptions
     * and deliveries made to clients
     * @return
     */
    public List<Triplet<String, Integer, Integer>> getCountersStats() {

        // get todos os counters registados
        Map<String, Worker> workers = this.getWorkers().get(Hierarchy.COUNTER);

        // lista onde ficarão os stats de todos os workers
        List<Triplet<String, Integer, Integer>> each_stats = new ArrayList<>();

        for (Map.Entry<String, Worker> e : workers.entrySet()) {

            // cada counter
            Counter c = (Counter) e.getValue();
            // receber o seu username
            String user = c.getUser();
            // calcular o total de rececoes e entregas
            int rececao = 0;
            int entrega = 0;

            Map<String, Pair<Integer, Integer>> rececao_entrega = c.getStatistics();
            // para cada cliente que já tenha atentido, calcula a soma das rececoes e entregas
            for (Map.Entry<String, Pair<Integer, Integer>> ee : rececao_entrega.entrySet()) {
                rececao += ee.getValue().getValue0();
                entrega += ee.getValue().getValue1();
            }
            each_stats.add(new Triplet<>(user, rececao, entrega));
        }

        return each_stats;
    }

    /**
     * Auxiliar Funcion to get the List of all tech's usernames
     * @return list of usernames
     */
    public List<String> getListTechs(){
        Map<String,Worker> map_techs = workers_dao.get(Hierarchy.TECHNICIAN);
        return map_techs.keySet().stream().toList();
    }


    /**
     * -----------------------------------------------
     *          CLIENTS
     * -----------------------------------------------
     */

    /**
     * Gets the client's database
     * @return
     */
    @Override
    public ClientDAO getClients(){
        return this.clients_dao.clone();
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

    /**
     * Updates a client's phone
     * @param c
     * @param phone
     * @return
     */
    @Override
    public boolean updateClientPhone(Client c, String phone){
        c.setPhone(phone);
        return this.clients_dao.update(c);
    }

    /**
     * Updates a client's mail
     * @param c
     * @param mail
     * @return
     */
    @Override
    public boolean updateClientMail(Client c, String mail){
        c.setEmail(mail);
        return this.clients_dao.update(c);
    }



    /**
     * -----------------------------------------------
     *          LOAD AND SAVE
     * -----------------------------------------------
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