package BusinessLayer.Workers;

public enum Hierarchy {


    COUNTER(1),
    TECHNICIAN(2),
    MANAGER(3);

    /**
     * Instance variables
     */
    private final int value;

    /**
     * Constructor
     * @param v
     */
    Hierarchy(int v){
        this.value = v;
    }

    /**
     * Getter
     */
    public int getValue(){
        return this.value;
    }

    /**
     *
     * @param v
     * @return
     */
    public Hierarchy getByValue(int v){

        for(Hierarchy h : Hierarchy.values()){

            if(h.value == v) return h;
        }
        return null;
    }

    /**
     * Gets the associated name of current's Hierarchy
     * @return
     */
    public String getHierarchyName(){

        return switch (this.value) {
            case 1 -> "Counter";
            case 2 -> "Technician";
            case 3 -> "Manager";
            default -> null;
        };
    }
}
