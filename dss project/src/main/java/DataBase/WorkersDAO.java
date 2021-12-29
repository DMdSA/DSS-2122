package DataBase;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Technician;
import BusinessLayer.Workers.Worker;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WorkersDAO implements Serializable {

    /**
     * Instance Variables
     */
    Map<Hierarchy, Map<String, Worker>> workersDAO;

    /**
     * Constructor
     *
     * For each hierarchy in system,
     * it creates a Map which maps each worker's username to it's object
     */
    public WorkersDAO() {

        this.workersDAO = new HashMap<>();

        for (Hierarchy h : Hierarchy.values()) {
            this.workersDAO.put(h, new HashMap<>());
        }
    }

    public WorkersDAO(WorkersDAO wdao){
        this.workersDAO = wdao.getWorkers();
    }

    /**
     * Clone
     */
    public WorkersDAO clone(){
        return new WorkersDAO(this);
    }

    /**
     * Returns the full list of workers on the system
     * @return
     */
    public Map<Hierarchy, Map<String, Worker>> getWorkers(){

        Map<Hierarchy, Map<String, Worker>> workers = new HashMap<>();

        for(Map.Entry<Hierarchy, Map<String, Worker>> ee : this.workersDAO.entrySet()){

            Hierarchy h = ee.getKey();
            workers.put(h, new HashMap<>());

            for(Map.Entry<String, Worker> e : ee.getValue().entrySet()){

                workers.get(h).put(e.getKey(), e.getValue().clone());
            }
        }
        return workers;
    }

    /**
     * Verifies if a specific worker exists, given their hierarchy and username
     * @param h
     * @param user
     * @return
     */
    public boolean hasWorker(Hierarchy h, String user){

        return this.workersDAO.get(h).containsKey(user);
    }

    /**
     * Updates a technician's availability
     * @param t
     * @param flag
     */
    public void updateAvaibility(Technician t, boolean flag){
        t.setAvailability(flag);
    }

    /**
     * Verifies if there is any worker with a given username and password, and then
     * returns it
     * @param user
     * @param pass
     * @return
     */
    public Worker confirmWorker(String user, String pass){

        for(Map.Entry<Hierarchy, Map<String, Worker>> e : this.workersDAO.entrySet()){

            for(Map.Entry<String, Worker> ee : e.getValue().entrySet()){

                if(ee.getKey().equals(user) && ee.getValue().getPass().equals(pass))
                    return ee.getValue().clone();
            }
        }
        return null;
    }

    /**
     * Gets the number of workers saved on system
     * @return
     */
    public int getSize(){

        int counter = 0;
        for(Map.Entry<Hierarchy, Map<String,Worker>> e : this.workersDAO.entrySet()){
            counter += e.getValue().size();
        }
        return counter;
    }

    /**
     * Add a new worker to database
     * @param w worker being added
     * @return true if the worker is new and successfully added
     */
    public boolean add(Worker w){

        Hierarchy h = w.getHierarchy();

        // Se já o tiver na base
        if(this.workersDAO.get(h).containsKey(w.getUser())){
            return false;
        }
        // Se for novo, adicionar
        this.workersDAO.get(h).put(w.getUser(), w); //clone?
        return true;
    }

    /**
     * Remove an existing worker
     * @param w worker being removed
     * @return true, if he existed and has been removed
     */
    public boolean remove(Worker w){

        Hierarchy h = w.getHierarchy();

        // Se ele existir, remove-o
        if(this.workersDAO.get(h).containsKey(w.getUser())){
            this.workersDAO.get(h).remove(w.getUser());
            return true;
        }
        // Caso contrário, não faz nada
        return false;
    }

    /**
     * Get an existing worker by his hierarchy and user
     * @param h hierarchy
     * @param user worker's username
     * @return
     */
    public Worker get(Hierarchy h, String user){

        // Se ele existir, devolve-o
        if(this.workersDAO.get(h).containsKey(user)){

            return this.workersDAO.get(h).get(user);
        }
        // Se não existir, devolve null
        return null;
    }

    /**
     * Gets the map of all users by their hierarchy
     * @param h
     * @return
     */
    public Map<String, Worker> get(Hierarchy h){

        Map<String, Worker> answer = new HashMap<>();

        Map<String, Worker> reallist = this.workersDAO.get(h);

        for(Map.Entry<String, Worker> e : reallist.entrySet()){
            answer.put(e.getKey(), e.getValue().clone());
        }

        return answer;
    }


    /**
     * Updates an existing worker
     * @param w Worker object
     * @return if the worker existed and has been updated
     */
    public boolean update(Worker w){

        Hierarchy h = w.getHierarchy();

        Map<String, Worker> workers = this.workersDAO.get(h);

        // Se existir, pode atualizá-lo, ou não
        if(workers.containsKey(w.getUser())){

            this.workersDAO.get(h).put(w.getUser(), w.clone()); // clone??
            return true;
        }
        // Se não existia na base, deve ser adicionado, não alterado
        return false;
    }

    /**
     * Updates the number of deliveries made by a counter worker
     * @param h
     * @param user
     * @param clientID
     * @return
     */
    public boolean updateDeliveries(Hierarchy h, String user, String clientID){
        Counter c = (Counter)this.workersDAO.get(h).get(user);
        c.updateEntregas(clientID);
        return this.update(c);
    }

    /**
     * Verifies if the system has any technician available at the moment
     * @return
     */
    public boolean hasTechAvailable(){

        Map<String, Worker> techs = this.workersDAO.get(Hierarchy.TECHNICIAN);

        for(Map.Entry<String, Worker> e : techs.entrySet()){

            Technician t = (Technician) e.getValue();
            if(t.getAvailability()){
                return true;
            }
        }
        return false;
    }
}
