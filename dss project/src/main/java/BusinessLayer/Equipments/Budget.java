package BusinessLayer.Equipments;
import org.javatuples.Triplet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Budget implements Comparable<Budget>, Serializable {

    /**
     * Instance Variables
     */
    private UUID budgetID;                                                      // budget id

    private String clientID;                                                    // Client
    private Equipment equip;                                                    // Equipamento
    private double estimatedPrice;                                              // Preço Estimado pelo Tech
    private String tech_username_budget;                                        // Technician username who did budget
    private String tech_username_repair;                                        // Technician username who did repair
    private LocalDate requestDate;                                              // Data pedido
    private List<Triplet<String, String, Double>> estimatedTODO;             // Passos
    private BudgetStatus status;                                                // Status
    private boolean express;

    /**
     * Constructors
     */
    // Destinado à criação por um CounterWorker
    public Budget(String clientID, Equipment e){

        this.budgetID = UUID.randomUUID();
        this.clientID = clientID;
        this.equip = e;
        this.estimatedPrice = 0;
        this.tech_username_budget = "";
        this.tech_username_repair = "";
        this.requestDate = LocalDate.now();
        this.estimatedTODO = new ArrayList<>();
        this.status = BudgetStatus.WITHOUT_BUDGET;
        this.express = false;
    }

    // Constructor destinado a pedidos EXPRESSO
    public Budget(String clientId, Equipment e, double estimatedPrice){

        this.budgetID = UUID.randomUUID();
        this.clientID = clientId;
        this.equip = e;
        this.estimatedPrice = estimatedPrice;
        this.tech_username_budget = "";
        this.tech_username_repair = "";
        this.requestDate = LocalDate.now();
        this.estimatedTODO = new ArrayList<>();
        this.status = BudgetStatus.WAITING_REPAIR;
        this.express = true;
    }


    public Budget(String clientID, Equipment e, String tech_username_budget, String tech_username_repair){

        this.budgetID = UUID.randomUUID();

        this.clientID = clientID;
        this.equip = e;
        this.equip.setTechnicianResponsible(this.tech_username_budget);
        this.estimatedPrice = 0;
        this.tech_username_budget = tech_username_budget;
        this.tech_username_repair = tech_username_repair;
        this.requestDate = LocalDate.now();
        this.estimatedTODO = new ArrayList<>();
        this.status = BudgetStatus.WITHOUT_BUDGET;
        this.express = false;
    }


    public Budget(Budget b){
        this.budgetID = b.getBudgetID();
        this.clientID = b.getClientID();
        this.equip = b.getEquip();
        this.estimatedPrice = b.getEstimatedPrice();
        this.tech_username_budget = b.getTech_username_budget();
        this.tech_username_repair = b.getTech_username_repair();
        this.requestDate = b.getRequestDate();
        this.estimatedTODO = b.getTodo();
        this.status = b.getStatus();
        this.express = b.getExpress();
    }

    public Budget clone(){
        return new Budget(this);
    }


    /**
     * Getters
     */
    //falta clone ?? todo

    public UUID getBudgetID(){return this.budgetID;}
    public String getClientID(){ return this.clientID;}
    public Equipment getEquip(){ return this.equip.clone();}
    public Double getEstimatedPrice(){return this.estimatedPrice;}
    public String getTech_username_budget(){ return this.tech_username_budget;}
    public String getTech_username_repair(){ return this.tech_username_repair;}
    public LocalDate getRequestDate(){ return this.requestDate;}
    public List<Triplet<String, String, Double>> getTodo(){ return new ArrayList<>(this.estimatedTODO);}
    public BudgetStatus getStatus(){ return this.status;}
    public boolean getExpress(){ return this.express;}


    /**
     * Setters
     */
    // clones ??? todo
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public void setEquip(Equipment equip){
        this.equip = equip;
    }
    public void setEstimatedPrice(double price){
        this.estimatedPrice = price;
    }
    public void setTech_username_budget(String user){
        this.tech_username_budget = user;
    }
    public void setTech_username_repair(String user){
        this.tech_username_repair = user;
    }
    public void setTodo(List<Triplet<String,String,Double>> list){
        this.estimatedTODO = list;
    }
    public void setStatus(BudgetStatus status){
        this.status = status;
    }


    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o == null) return false;

        Budget that = (Budget) o;

        return this.budgetID.equals(that.budgetID) &&
                this.clientID.equals(that.clientID) &&
                this.estimatedPrice == that.estimatedPrice &&
                this.tech_username_budget.equals(that.tech_username_budget) &&
                this.tech_username_repair.equals(that.tech_username_repair) &&
                this.requestDate.equals(that.requestDate) &&
                this.estimatedTODO.equals(that.estimatedTODO) &&
                this.status.equals(that.status) &&
                this.express == that.express;
    }

    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("BudgetId: \"")
                .append(this.budgetID)
                .append("\", Equipment: \"")
                .append(this.equip.name())
                .append("\", Express: \"")
                .append(this.express)
                .append("\"  clientId: \"")
                .append(this.clientID)
                .append("\", Price Estimated: \"")
                .append(this.estimatedPrice)
                .append("\",\n\t   tech responsible for budget: \"")
                .append(this.tech_username_budget)
                .append("\",   tech responsible for repair: \"")
                .append(this.tech_username_repair)
                .append("\",   request date: \"")
                .append(this.requestDate)
                .append("\",   status: \"")
                .append(this.status)
                .append("\",   counter responsible: \"")
                .append(this.equip.counterResponsible())
                .append("\"\n");
        return sb.toString();
    }

    /**
     * TODO
     * @return
     */
    public String todoListToString(){
        //todo
        return null;
    }

    /**
     * compareTo para budgets
     * @param o
     * @return
     */
    @Override
    public int compareTo(Budget o) {

        // verificar se funciona
        return this.requestDate.compareTo(o.getRequestDate());
    }
}
