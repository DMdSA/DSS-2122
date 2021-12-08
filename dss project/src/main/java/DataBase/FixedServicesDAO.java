package DataBase;

import BusinessLayer.FixedServices.FixedService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

// CRUD
public class FixedServicesDAO {

    private Map<Integer, FixedService> services;
    private int n_services;

    /**
     * Default Constructor
     */
    public FixedServicesDAO(){
        this.services = new HashMap<>();
        this.n_services = 0;
    }

    /**
     * Parameterized Constructor
     * @param services
     */
    public FixedServicesDAO(Map<Integer, FixedService> services){

        this.services = new HashMap<>();
        for(Map.Entry<Integer, FixedService> e : services.entrySet()){
            this.services.put(e.getKey(), e.getValue().clone());
        }
        this.n_services = this.services.size();
    }

    /**
     * Get number of available services
     * @return number of services
     */
    public int getN_services() {
        return n_services;
    }

    /**
     * Set number of available services
     * @param n_services number of services
     */
    public void setN_services(int n_services) {
        this.n_services = n_services;
    }


    /**
     * Get a service's info by it's ID
     * @param id service's ID
     * @return FixedService's Info
     */
    public FixedService get(int id){
        return this.services.get(id).clone();
    }

    /**
     * Add a fixed service to table of services
     * @param fs Fixed service to be inserted
     * @return If the service was successfully added in table
     */
    public boolean add(FixedService fs){

        if(this.services.containsKey(fs)){
            System.out.println("Service already exists");
            return false;
        }
        this.services.put(fs.ID(), fs.clone());
        this.n_services++;
        return true;
    }

    /**
     * Updates a FixedService's Info
     * @param fs FixedService's info to be replaced
     * @return if the action was successful
     */
    public boolean update(FixedService fs){

        // Caso em que tem o serviço e o atualiza
        if(this.services.containsKey(fs.ID())){

            this.services.put(fs.ID(), fs.clone());
            return true;
        }

        // Caso em que não tem o serviço
        return false;
    }


    /**
     * Remove a FixedService from table
     * @param fs FixedService to be removed
     * @return if the action was successful
     */
    public boolean remove(FixedService fs){

        // Caso em que o serviço é removido com sucesso
        if(this.services.containsKey(fs.ID())){

            this.services.remove(fs.ID());
            this.n_services--;
            return true;
        }

        // Caso em que o serviço não consta nos dados
        return false;
    }

}
