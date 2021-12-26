package DataBase;

import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;

import java.util.*;

public class ProcessingCenterDAO {

    /**
     * Instance Variables
     */
    private Map<BudgetStatus, Map<UUID, Budget>> budgets;


    /**
     * Constructor
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

    public Map<UUID,Budget> get_by_state(BudgetStatus s){
        return budgets.get(s);
    }

    public int size(){
        return budgets.get(BudgetStatus.WITHOUT_BUDGET).size();
    }


}
