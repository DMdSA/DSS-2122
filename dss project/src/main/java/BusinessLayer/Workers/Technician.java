package BusinessLayer.Workers;

import java.io.Serializable;

public class Technician extends Worker {

    /**
     * Instance variables
     */
    private boolean isAvailable;

    /**
     * Constructors
     */
    public Technician() {
        super();
        this.setHierarchy(Hierarchy.TECHNICIAN);
        this.isAvailable = true;
    }

    public Technician(String user, String pass) {
        super(user, pass);
        this.setHierarchy(Hierarchy.TECHNICIAN);
        this.isAvailable = true;
    }

    public Technician(String user, String pass, String name, String nif, String phone) {

        super(user, pass, name, nif, phone);
        this.setHierarchy(Hierarchy.TECHNICIAN);
        this.isAvailable = true;
    }

    public Technician(Technician t){
        super(t);
        this.setHierarchy(t.getHierarchy());
        this.isAvailable = t.getAvailability();
    }

    public Technician clone(){
        return new Technician(this);
    }


    /**
     * Getters
     */
    public boolean getAvailability(){
        return this.isAvailable;
    }

    /**
     * Setters
     */
    public void setAvailability(boolean av){
        this.isAvailable = av;
    }


    /**
     * toString
     * @return
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[Technician] user:\"")
                .append(this.getUser())
                .append("\", pass:\"")
                .append(this.getPass())
                .append(", name:\"")
                .append(this.getName())
                .append(", nif:\"")
                .append(this.getNif());

        return sb.toString();
    }

    /**
     * equals
     */
    public boolean equals(Object o) {

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o==null) return false;

        Technician that = (Technician) o;

        return super.equals(that) &&
                this.isAvailable == that.isAvailable;
    }

}
