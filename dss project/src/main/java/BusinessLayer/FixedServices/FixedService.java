package BusinessLayer.FixedServices;

import java.util.ArrayList;
import java.util.List;

public class FixedService {

    private int serviceID;
    private String serviceDescription;
    private List<String> equipmentsAccepted;
    private double price;

    // Full row of information
    public FixedService(int id, String description, List<String> equipments, double price){
        this.serviceID = id;
        this.serviceDescription = description;
        this.equipmentsAccepted = new ArrayList<>(equipments);
        this.price = price;
    }

    // Accepts all kind of equipments
    public FixedService(int id, String description, double price){

        this.serviceID = id;
        this.serviceDescription = description;
        this.price = price;
        this.equipmentsAccepted = null;
    }

    public FixedService(FixedService fs){

        this.serviceID = fs.ID();
        this.serviceDescription = this.Description();
        this.equipmentsAccepted = this.EquipmentsAccepted();
        this.price = this.Price();
    }


    public FixedService clone(){ return new FixedService(this);}


    /**
     * Getters
     */
    public int ID(){return this.serviceID;}
    public String Description(){return this.serviceDescription;}
    public List<String> EquipmentsAccepted(){return this.equipmentsAccepted;}
    public double Price(){return (this.price);}

    /**
     * Setters
     */
    public void setID(int id){this.serviceID = id;}
    public void setDescription(String d){this.serviceDescription = d;}
    public void setEquipmentsAccepted(List<String> ea){this.equipmentsAccepted = new ArrayList<>(ea);}
    public void setPrice(double p){this.price = p;}

}




