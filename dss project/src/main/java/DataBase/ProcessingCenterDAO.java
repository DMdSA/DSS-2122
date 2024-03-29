package DataBase;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import java.io.Serializable;
import java.util.*;

public class ProcessingCenterDAO implements Serializable {

    /**
     * Instance Variables
     */
    private Map<BudgetStatus, Map<UUID, Budget>> budgets;


    /**
     * Constructor
     * Prepares a Map<BudgetStatus, Map<UUID, Budget>, mapping each budget status
     * to a new Map of unique ID's mapped to their associated budgets
     */
    public ProcessingCenterDAO(){

        // o mapa externo pode ser Hashmap, o interno terá de ser ordenado!
        this.budgets = new HashMap<>();

        for(BudgetStatus bs : BudgetStatus.values()){
            this.budgets.put(bs, new TreeMap<>());
        }
    }

    /**
     * Get by budget's ID
     */
    public Budget get(UUID id){

        for(Map.Entry<BudgetStatus, Map<UUID, Budget>> entry : this.budgets.entrySet()){

            if(entry.getValue().containsKey(id)){
                return entry.getValue().get(id);
            }
        }
        return null;
    }

    /**
     * Gets a list of budgets that a technician made (budget only, not repairs)
     * @param username
     * @return
     */
    public List<Budget> getByUsername(String username){

        List<Budget> budgets = new ArrayList<>();
        // ??
        for(Map.Entry<BudgetStatus, Map<UUID, Budget>> entry : this.budgets.entrySet()){

            for(Map.Entry<UUID, Budget> e : entry.getValue().entrySet()){

                if(e.getValue().getTech_username_budget().equals(username)){
                    budgets.add(e.getValue());
                }
            }
        }
        return budgets;
    }

    /**
     * Gets all the budgets worker on by a specific Technician (both repair and budget)
     * @param username
     * @return
     */
    public List<Budget> getTechnicianBudgets(String username){

        List<Budget> budgets = new ArrayList<>();
        for(Map.Entry<BudgetStatus, Map<UUID, Budget>> entry : this.budgets.entrySet()){

            for(Map.Entry<UUID, Budget> e : entry.getValue().entrySet()){

                if(e.getValue().getTech_username_budget().equals(username)
                        || e.getValue().getTech_username_repair().equals(username)){
                    budgets.add(e.getValue());
                }
            }
        }
        return budgets;
    }

    /**
     * Get by Client's ID
     */
    public List<Budget> get(String clientId){

        List<Budget> answer = new ArrayList<>();

        // Para cada entrada de Status
        for(Map.Entry<BudgetStatus, Map<UUID, Budget>> entry : this.budgets.entrySet()){

            // Para cada budget registado
            for(Map.Entry<UUID, Budget> e : entry.getValue().entrySet()){
                // Encontrar os referentes ao cliente pedido
                if(e.getValue().getClientID().equals(clientId)){
                    answer.add(e.getValue());
                }
            }
        }
        return answer;
    }

    /**
     * ???
     * @param bs
     * @return
     */
    public Budget getFirst(BudgetStatus bs){
        TreeMap<UUID,Budget> aux = (TreeMap<UUID, Budget>) this.budgets.get(bs);
        return aux.firstEntry().getValue();
    }


    /**
     * Add a new budget to database
     */
    public boolean add(Budget b){

        BudgetStatus bs = b.getStatus();

        if(this.budgets.get(bs).containsValue(b)){

            // já existia
            return false;
        }
        this.budgets.get(bs).put(b.getBudgetID(), b.clone());
        return true;
    }

    /**
     * Removes a budget from database
     */
    public boolean remove(Budget b){

        BudgetStatus bs = b.getStatus();
        Map<UUID, Budget> bgts = this.budgets.get(bs);
        // Se existir na base
        if(bgts.containsKey(b.getBudgetID())){
            // Se forem iguais, remove-o
            if(b.equals(bgts.get(b.getBudgetID()))){
                bgts.remove(b.getBudgetID());
                return true;
            }
        }
        return false;
    }

    /**
     * Updates a budget
     */
    public boolean update(Budget b){

        BudgetStatus bs = b.getStatus();
        Map<UUID, Budget> bgts = this.budgets.get(bs);

        // Se contiver o mmo ID
        if(bgts.containsKey(b.getBudgetID())){
            // Se forem mesmo iguais,
            if(bgts.get(b.getBudgetID()).equals(b)){
                // Substitui
                bgts.put(b.getBudgetID(), b);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the budget and transfers it to the new status
     * @param b previous budget
     * @param bs new budgetstatus
     * @return
     *
     * The method is to remove the budget from its previous line of status, update it
     * with the new wanted status and finally add it to the database, again
     */
    public boolean update(Budget b, BudgetStatus bs){

        // remove previous version
        BudgetStatus previous_bs = b.getStatus();
        Map<UUID, Budget> previous = this.budgets.get(previous_bs);
        previous.remove(b.getBudgetID());

        // add the new one
        b.setStatus(bs);
        return this.add(b);
    }


    /**
     * Get the map of budgets associated with a specific budget status
     * @param s
     * @return
     */
    public Map<UUID,Budget> get_by_state(BudgetStatus s){
        return budgets.get(s);
    }

    /**
     * ???
     * @param s
     * @param bs
     * @return
     */
    public List<Budget> get_by_user_and_status_repair(String s, BudgetStatus bs){
        List<Budget> aux = new ArrayList<>();
        for(Map.Entry<UUID, Budget> e : this.budgets.get(bs).entrySet()){
                if (e.getValue().getTech_username_repair().equals(s)) {
                    aux.add(e.getValue());

                }
        }
        return aux;
    }

    /**
     * ???
     * @param s
     * @param bs
     * @return
     */
    public List<Budget> get_by_user_and_status_budget(String s, BudgetStatus bs){
        List<Budget> aux = new ArrayList<>();
        for(Map.Entry<UUID, Budget> e : this.budgets.get(bs).entrySet()){
            if (e.getValue().getTech_username_budget().equals(s)) {
                aux.add(e.getValue());
            }
        }
        return aux;
    }

    /**
     * Return the number of budgets saved in a specific status
     * @return
     */
    public int size(BudgetStatus bs){

        return budgets.get(bs).size();
    }

    /**
     * Counts the global amount of budgets registered, from all status
     * @return
     */
    public int globalSize(){
        int counter = 0;

        for(Map.Entry<BudgetStatus,Map<UUID, Budget>> e : this.budgets.entrySet()){
            counter += e.getValue().size();
        }
        return counter;
    }
}