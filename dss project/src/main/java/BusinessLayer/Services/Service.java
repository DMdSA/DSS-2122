package BusinessLayer.Services;

import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.Equipment;

public class Service {

        // Para registar um serviço, é preciso 1 cliente, 1 equipamento, 1 budget

    /**
     * Instance Variables
     */
    private String clientID;
    private Equipment equipment;
    private Budget budget;


    /**
     * Constructors
     */
    public Service(String clientId, String EquipmentDescription, String counterId){

        this.clientID = clientId;
        this.equipment = new Equipment(EquipmentDescription, this.clientID, counterId);
        this.budget = new Budget(this.clientID, this.equipment);
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
                this.budget.equals(that.budget);
    }

    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[Service]: Client Id:\"")
                .append(this.clientID)
                .append("\", Equipment name:\"")
                .append(this.equipment.name())
                .append("\", Equipment Id:\"")
                .append(this.equipment.ID())
                .append("\", Budget Id:\"")
                .append(this.budget.getBudgetID())
                .append("\", Tech responsible:\"")
                .append(this.budget.getTech_username())
                .append("\"");
        return sb.toString();
    }
}
