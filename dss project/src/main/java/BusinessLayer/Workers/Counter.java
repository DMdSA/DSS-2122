package BusinessLayer.Workers;

import BusinessLayer.Client.Client;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class Counter extends Worker{

    /**
     * Instance variables
     */
    private Map<String, Pair<Integer, Integer>> statistics;

    /**
     * Constructors
     */
    public Counter() {
        super();
        this.setHierarchy(Hierarchy.COUNTER);
        this.statistics = new HashMap<>();
    }

    public Counter(String user, String pass) {
        super(user, pass);
        this.setHierarchy(Hierarchy.COUNTER);
        this.statistics = new HashMap<>();
    }

    public Counter(String user, String pass, String name, String nif, String phone) {

        super(user, pass, name, nif, phone);
        this.setHierarchy(Hierarchy.COUNTER);
        this.statistics = new HashMap<>();
    }

    public Counter(Counter c){
        super(c);
        this.setHierarchy(c.getHierarchy());
        this.statistics = c.getStatistics();
    }

    public Counter clone(){
        return new Counter(this);
    }

    /**
     * Getters
     */
    public Map<String, Pair<Integer,Integer>> getStatistics(){

        return new HashMap<>(this.statistics);
    }


    /**
     * Atualiza, em +1, o n.º de receções realizadas
     * @param ClientID
     * @return
     */
    public boolean updateRececoes(String ClientID){

        // Se já existia, atualizar
        if(this.statistics.containsKey(ClientID)){

            int previous = this.statistics.get(ClientID).getValue0();

            this.statistics.put(ClientID, new Pair<>(previous+1, this.statistics.get(ClientID).getValue1()));
            return true;
        }

        // Se não existia, acrescentar um novo record
        this.statistics.put(ClientID, new Pair<>(1, 0));
        return true;
    }

    /**
     * Atualiza, em +1, o n.º de entregas realizadas
     * @param clientID
     * @return
     */
    public boolean updateEntregas(String clientID){

        // Se já existia, atualizar
        if(this.statistics.containsKey(clientID)){

            int previous = this.statistics.get(clientID).getValue1();

            this.statistics.put(clientID, new Pair<>(this.statistics.get(clientID).getValue0(), previous+1));
            return true;
        }

        // Se não existia, acrescentar um novo record
        this.statistics.put(clientID, new Pair<>(0, 1));
        return true;
    }

    /**
     * toString
     * @return
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("[CounterWorker] user: \"")
                .append(this.getUser())
                .append("\",  pass: \"")
                .append(this.getPass())
                .append("\",  name: \"")
                .append(this.getName())
                .append("\",  nif: \"")
                .append(this.getNif())
                .append("\",  phone: \"")
                .append(this.getPhone())
                .append("\"");

        return sb.toString();
    }

    /**
     * equals
     */
    public boolean equals(Object o) {

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o==null) return false;

        Counter that = (Counter) o;

        return super.equals(that) &&
                this.statistics.equals(that.statistics);
    }

}
