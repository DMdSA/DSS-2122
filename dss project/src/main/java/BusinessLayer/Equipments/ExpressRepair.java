package BusinessLayer.Equipments;

public class ExpressRepair {

    /**
     * Instance Variables
     */
    private String repair_name;
    private String repair_description;
    private double price;

    /**
     * Constructor
     */
    public ExpressRepair(String repair_name, String repair_description, double price){
        this.repair_description = repair_description;
        this.repair_name = repair_name;
        this.price = price;
    }

    /**
     * Getters
     */
    public String getRepair_name() {
        return repair_name;
    }
    public String getRepair_description(){
        return repair_description;
    }
    public double getPrice(){
        return price;
    }

    /**
     * Setters
     */
    public void setPrice(double p){
        this.price = p;
    }

    public void setRepair_name(String s){
        this.repair_name = s;
    }
    public void setRepair_description(String s){
        this.repair_description = s;
    }


    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o==null) return false;

        ExpressRepair that = (ExpressRepair) o;

        return this.repair_name.equals(that.repair_name) &&
                this.repair_description.equals(that.repair_description) &&
                this.price == that.price;
    }

    /**
     * toString
     */

    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[ExpressRepair] Service:\"")
                .append(this.repair_name)
                .append("\", Description:\"")
                .append(this.repair_description)
                .append("\", Price:\"")
                .append(this.price)
                .append("\"");
        return sb.toString();
    }
}
