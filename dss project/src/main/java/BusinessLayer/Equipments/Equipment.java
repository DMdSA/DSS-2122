package BusinessLayer.Equipments;

import java.io.Serializable;
import java.util.UUID;

public class Equipment implements Serializable {

    /**
     * Instance Variables
     */
    private UUID id;                                // ID
    private String name;                            // NAME
    private String clientNif;                       // CLIENT
    private String counterResponsible;              // COUNTER RESPONSIBLE
    private String technicianResponsible;           // TECH RESPONSIBLE
    private boolean repaired;                       // REPAIRED

    /**
     * Constructors
     */
    public Equipment(String name, String clientNif, String counterResponsible){

        this.id = UUID.randomUUID();
        this.name = name;
        this.clientNif = clientNif;
        this.counterResponsible = counterResponsible;
        this.technicianResponsible = null;
        this.repaired = false;

    }

    public Equipment(String name, String clientNif, String counter, String tech){

        this.id = UUID.randomUUID();
        this.name = name;
        this.clientNif = clientNif;
        this.counterResponsible = counter;
        this.technicianResponsible = tech;
        this.repaired = false;
    }

    public Equipment(Equipment e){
        this.id = e.ID();
        this.name = e.name();
        this.clientNif = e.clientNif();
        this.counterResponsible = e.counterResponsible();
        this.technicianResponsible = e.technicianResponsible();
        this.repaired = e.isRepaired();
    }

    public Equipment clone(){
        return new Equipment(this);
    }

    /**
    Setters
     */
    public void setID(UUID id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setClientNif(String clientNif){
        this.clientNif = clientNif;
    }

    public void setCounterResponsible(String counterResponsible) {
        this.counterResponsible = counterResponsible;
    }

    public void setTechnicianResponsible(String tech){
        this.technicianResponsible = tech;
    }

    public void setRepaired(boolean flag){
        this.repaired = flag;
    }

    /**
     * Getters
     */
    public UUID ID(){ return this.id;}
    public String name(){ return this.name;}
    public String clientNif(){ return this.clientNif;}
    public boolean isRepaired(){ return this.repaired;}
    public String counterResponsible(){return this.counterResponsible;}
    public String technicianResponsible(){ return this.technicianResponsible;}

    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("Id:\"")
                .append(this.id)
                .append("\", name:\"")
                .append(this.name)
                .append("\", client nif:\"")
                .append(this.clientNif)
                .append("\", repaired:\"")
                .append(this.repaired)
                .append("\"");
        return sb.toString();
    }


    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;

        if(this.getClass() != o.getClass() || o == null) return false;

        Equipment that = (Equipment) o;

        return this.name.equals(that.name) &&
                this.id.equals(that.id) &&
                this.clientNif.equals(that.clientNif) &&
                this.counterResponsible.equals(that.counterResponsible) &&
                this.technicianResponsible.equals(that.technicianResponsible) &&
                this.repaired == that.repaired;
    }

}
