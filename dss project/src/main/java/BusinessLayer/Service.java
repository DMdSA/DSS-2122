package BusinessLayer;

import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.Equipment;
import BusinessLayer.Equipments.ExpressRepair;
import org.javatuples.Triplet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Service {

        // Para registar um serviço, é preciso 1 cliente, 1 equipamento, 1 budget

    /**
     * Instance Variables
     */
    private String clientID;
    private Equipment equipment;
    private Budget budget;
    private boolean isExpress;
    private List<Triplet<String, LocalTime,Double>> passos_real;
    private double preco_real;



    /**
     * Constructors
     */
    public Service(String clientId, String EquipmentDescription, String counterId){

        this.clientID = clientId;
        this.equipment = new Equipment(EquipmentDescription, this.clientID, counterId);
        this.isExpress = false;
        this.budget = new Budget(this.clientID, this.equipment);
        this.passos_real = new ArrayList<>();
        this.preco_real = 0;
    }


    public Service(String clientid, String equipDescription, String counterid, ExpressRepair er){

        this.clientID = clientid;
        this.equipment = new Equipment(equipDescription, this.clientID, counterid);
        this.isExpress = true;
        this.budget = new Budget(this.clientID, this.equipment, er.getPrice());
    }

    /**
     * Getters
     */

    public String getClientId(){
        return this.clientID;
    }

    public Equipment getEquipment(){
        return this.equipment;          // clone???
    }

    public Budget getBudget(){          // clone???
        return this.budget;
    }

    public boolean getIsExpress(){ return this.isExpress;}

    public double getPreco_real(){return this.preco_real;}

    public List<Triplet<String,LocalTime,Double>> getPassos_real(){
        return this.passos_real;
    }

    /**
     * Setters
     */

    public void setClientID(String clientId){
        this.clientID = clientId;
        this.equipment.setClientNif(clientId);
        this.budget.setClientID(clientID);
    }
    // Faz sentido haver mais setters?

    public void setPassos_real(List<Triplet<String,LocalTime,Double>> passos_real){
        this.passos_real = passos_real;
    }

    public void setPreco_real(double preco_real){
        this.preco_real = preco_real;
    }


    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o == null) return false;

        Service that = (Service) o;
        return this.clientID.equals(that.clientID) &&
                this.equipment.equals(that.equipment) &&
                this.budget.equals(that.budget) &&
                this.isExpress == that.getIsExpress();
    }

    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[Service]: Client Id: \"")
                .append(this.clientID)
                .append("\", Equipment name: \"")
                .append(this.equipment.name())
                .append("\", Equipment Id: \"")
                .append(this.equipment.ID())
                .append("\", Budget Id: \"")
                .append(this.budget.getBudgetID())
                .append("\", Tech responsible: \"")
                .append(this.budget.getTech_username_budget())
                .append("\", Express: \"")
                .append(this.isExpress)
                .append("\"");
        return sb.toString();
    }
}
