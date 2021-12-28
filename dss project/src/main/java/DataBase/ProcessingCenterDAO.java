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

        //TODO
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
     * Add
     */
    public boolean add(Budget b){

        BudgetStatus bs = b.getStatus();

        if(this.budgets.get(bs).containsValue(b)){

            // já existia
            return false;
        }
        this.budgets.get(bs).put(b.getBudgetID(), b);   // clone???
        return true;
    }

    /**
     * Remove
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
     * Update
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
     *
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
