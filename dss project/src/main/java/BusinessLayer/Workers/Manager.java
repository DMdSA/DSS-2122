package BusinessLayer.Workers;

public class Manager extends Worker{

    /**
     * Instance variables
     */

    /**
     * Constructors
     */
    public Manager() {
        super();
        this.setHierarchy(Hierarchy.MANAGER);
    }

    public Manager(String user, String pass) {
        super(user, pass);
        this.setHierarchy(Hierarchy.MANAGER);
    }

    public Manager(String user, String pass, String name, String nif, String phone) {

        super(user, pass, name, nif, phone);
        this.setHierarchy(Hierarchy.MANAGER);
    }

    public Manager(Manager m){

        super(m);
        this.setHierarchy(m.getHierarchy());
    }

    public Manager clone(){
        return new Manager(this);
    }

    /**
     * Getters
     */





    /**
     * Setters
     */


    /**
     * toString
     * @return
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[Manager] user:\"")
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

        Manager that = (Manager) o;

        return super.equals(that);
    }

}
