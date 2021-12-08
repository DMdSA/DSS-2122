package BusinessLayer.Equipments;

import BusinessLayer.Client.Client;

import java.util.UUID;

public class Equipment {

    private UUID equipment_id;
    private String name;
    private Client client;
    private boolean repaired;


    public Equipment(String name, Client client){

        this.name = name;
        this.client = client;
        this.equipment_id = UUID.randomUUID();
        this.repaired = false;
    }

    public Equipment(UUID id, String name, Client c){

        this.equipment_id = id; this.name = name; this.client = c;
    }


    /**
    Setters
     */
    public void setID(UUID id) {
        this.equipment_id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setClient(Client c){
        this.client = c;
    }

    public void setRepaired(boolean flag){
        this.repaired = flag;
    }

    /**
     * Getters
     */
    public UUID ID(){ return this.equipment_id;}
    public String name(){ return this.name;}
    public Client Client(){ return this.client;}
    public boolean isRepaired(){ return this.repaired;}
}
