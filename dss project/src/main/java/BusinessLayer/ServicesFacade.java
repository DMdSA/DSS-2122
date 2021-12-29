package BusinessLayer;

import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ExpressServicesDAO;
import DataBase.ProcessingCenterDAO;
import org.javatuples.Triplet;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;

public class ServicesFacade implements IServices, Serializable {

    /**
     * Instance Variables
     */
    private ProcessingCenterDAO processingCenter_dao;
    private ExpressServicesDAO expressServices_dao;

    private static final String path = new String(Paths.get("").toAbsolutePath().toString()
            +File.separator +"src" + File.separator + "main" +File.separator
            + "java" + File.separator + "DataBase" + File.separator
            + "SERVICES_DB");


    /**
     * Constructor
     */
    public ServicesFacade() throws IOException, ClassNotFoundException {

        this.load();
    }

    public ServicesFacade(ProcessingCenterDAO centerdao, ExpressServicesDAO expressdao){
        this.processingCenter_dao = centerdao;
        this.expressServices_dao = expressdao;
    }



    /**
     * Registers a new express service
     * @return
     */
    public boolean registerExpressService() {
        return false;
    }

    /**
     * Updates a budget
     * @param b
     * @param new_price
     * @param new_passos
     * @param new_budgetStatus
     *
     * If the new budgetStatus is equal to the previous version, it only updates the remaining aspects of the object
     * Otherwise, it will remove it from its previous line of BudgetStatus and add it to the new one
     *
     */
    public boolean update_budget(Budget b, double new_price, List<Triplet<String, String, Double>> new_passos, BudgetStatus new_budgetStatus){

        b.setEstimatedPrice(new_price);
        b.setTodo(new_passos);

        if(b.getStatus().equals(new_budgetStatus)){
            return processingCenter_dao.update(b);
        }
        else
            return processingCenter_dao.update(b, new_budgetStatus);
    }

    public void update_budget(Budget b){
        processingCenter_dao.update(b);
    }

    /**
     * Auxiliar Function to get the number of budgets and reparations of the techs
     * @param list_techs list of all the techs' username
     * @return List with all the information about the techs' statistics
     */
    public List<Triplet<String, Integer,Integer>> getTechStats(List<String> list_techs){
        List<Triplet<String,Integer,Integer>> statistics = new ArrayList<>();
        for(String s : list_techs) {
            //Numero de todos os budgets e reparacoes que o Tech trabalhou
            int all = processingCenter_dao.getTechnicianBudgets(s).size();
            //Numero dos budgets
            int budgets = processingCenter_dao.getByUsername(s).size();
            //Numero das reparacoes
            int repair = all - budgets;
            statistics.add(new Triplet<>(s,budgets,repair));
        }
        return statistics;
    }


    /**
     * Registers a new budget request / normal service
     * @param service
     * @return
     */
    public boolean registerBudgetRequest(WorkersFacade wf, Service service) {

        boolean flag = this.processingCenter_dao.add(service.getBudget());
        if(flag) {
            if (wf.getWorkers().get(Hierarchy.COUNTER).containsKey(service.getClientId())) {
                Counter c = (Counter) wf.getWorkers().get(Hierarchy.COUNTER, service.getEquipment().counterResponsible());
                c.updateRececoes(service.getClientId());
                wf.getWorkers().update(c);
            }
            return true;
        }
        return false;
    }


    public boolean addBudget(Budget b){

        return this.processingCenter_dao.add(b);
    }


    public boolean removeBudget(Budget b){

        return this.processingCenter_dao.remove(b);
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
    public List<Object> consultExpressServices() {

        List<ExpressRepair> expresses = this.expressServices_dao.getAllServices();
        return Arrays.asList(expresses.toArray());
    }

    public int numberOfExpressServices(){
        return this.expressServices_dao.size();
    }

    public ExpressRepair getExpressService(int token){
        return this.expressServices_dao.get(token);
    }

    /**
     * Verifies if the system has any express services available
     * @return
     */
    public boolean hasExpressServices(){

        return (this.expressServices_dao.size() > 0);
    }

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
    public boolean hasBudgetRequests(){

        return (this.processingCenter_dao.globalSize() > 0);
    }

    public boolean hasBudgetRequestByStatus(BudgetStatus budgetStatus){
        return (this.processingCenter_dao.get_by_state(budgetStatus).size() > 0);
    }

    /**
     * Gets a technicians's budgets (that he worked on)
     * @param w
     * @return
     */
    public List<Budget> getTechBudgets(Worker w){

        String username = w.getUser();

        return this.processingCenter_dao.getTechnicianBudgets(username);
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


    public boolean updateBudgetStatus(Budget b, BudgetStatus bs) {

        return this.processingCenter_dao.update(b, bs);
    }

    public List<Budget> Repairing_budgets(String username){

        List<Budget> aux = this.processingCenter_dao.get_by_user_and_status_repair(username,BudgetStatus.REPAIRING);
        aux.addAll(this.processingCenter_dao.get_by_user_and_status_budget(username,BudgetStatus.DOING_BUDGET));
        return aux;
    }


    /**
     * Gets the first element from the given BudgetStatus' list, which is the oldest budget in that list
     * @param b
     * @return
     */
    public Budget get_first(BudgetStatus b){
        return this.processingCenter_dao.getFirst(b);
    }

    /**
     * Puts the status of a Budget ON_HOLD
     * @param username
     * @return
     */
    public List<Budget> On_Hold_Budgets(String username){
        return this.processingCenter_dao.get_by_user_and_status_repair(username,BudgetStatus.ON_HOLD);
    }

    /**
     * Updates the State of a Budget to REPAIRING
     * @param b
     */
    public void continue_reparation(Budget b){
        this.processingCenter_dao.update(b,BudgetStatus.REPAIRING);
    }

    /**
     * Auxiliar Function to, depending on the Tech's choice, gives him the oldest Budget in WITHOUT_BUDGET
     * or in WAITING_REPAIR
     * @param budgetStatus Status to know if Tech is going to do a budget or reparation
     * @param username Tech's username
     * @return Budget the Tech will work on
     */
    public Budget putTechWorking(BudgetStatus budgetStatus, String username){
        Budget b = get_first(budgetStatus);

        if(budgetStatus == BudgetStatus.WITHOUT_BUDGET) {
            b.setTech_username_budget(username);
            this.processingCenter_dao.update(b,BudgetStatus.DOING_BUDGET);
        }
        else if(budgetStatus == BudgetStatus.WAITING_REPAIR){
            b.setTech_username_repair(username);
            this.processingCenter_dao.update(b,BudgetStatus.REPAIRING);
        }
        return b;
    }






    /**
     * Saves the system's state in an object file
     */
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
    public ServicesFacade load() throws IOException, ClassNotFoundException {

        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);

        Object o = input.readObject();

        if(o instanceof ServicesFacade){
            return (ServicesFacade) o;
        }
        return null;
    }

}
