package BusinessLayer.Services;

import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.Equipment;
import BusinessLayer.Equipments.ExpressRepair;

public class Service {

        // Para registar um serviço, é preciso 1 cliente, 1 equipamento, 1 budget

    /**
     * Instance Variables
     */
    private String clientID;
    private Equipment equipment;
    private Budget budget;
    private boolean isExpress;


    /**
     * Constructors
     */
    public Service(String clientId, String EquipmentDescription, String counterId){

        this.clientID = clientId;
        this.equipment = new Equipment(EquipmentDescription, this.clientID, counterId);
        this.isExpress = false;
        this.budget = new Budget(this.clientID, this.equipment);
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

    /**
     * Setters
     */

    public void setClientID(String clientId){
        this.clientID = clientId;
        this.equipment.setClientNif(clientId);
        this.budget.setClientID(clientID);
    }
    // Faz sentido haver mais setters?


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
                .append(this.budget.getTech_username())
                .append("\", Express: \"")
                .append(this.isExpress)
                .append("\"");
        return sb.toString();
    }
}
