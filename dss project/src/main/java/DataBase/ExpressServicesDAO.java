package DataBase;

import BusinessLayer.Equipments.ExpressRepair;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpressServicesDAO implements Serializable {

    /**
     * Instance Variables
     */
    private Lock locker;
    private int nextToken;
    private Map<Integer, ExpressRepair> available_express_services;

    /**
     * Empty constructor,
     * prepares a locker, a new Map<Integer,ExpressRepair>, where each integer
     * is mapped to a unique ExpressRepair service
     */
    public ExpressServicesDAO(){
        this.locker = new ReentrantLock();
        this.available_express_services = new HashMap<>();
        this.nextToken = 1;
    }

    /**
     * Constructor
     * @param ers List of ExpressRepair services
     *
     * After creating a locker and a map<Integer,ExpressRepair> it uses
     * an auxiliar methhod, add(ExpressRepair> to get this map's next token available
     * and it associates it with the new objects being added
     */
    public ExpressServicesDAO(List<ExpressRepair> ers){

        this.locker = new ReentrantLock();
        this.available_express_services = new HashMap<>();
        this.nextToken = 1;

        for(ExpressRepair er : ers) {

            this.add(er);
        }
    }

    /**
     * Returns the amount of services stored
     * @return
     */
    public int size(){
        return this.available_express_services.size();
    }

    public ExpressRepair get(int token){
        if(token < this.nextToken) {
            return this.available_express_services.get(token).clone();
        }
        else return null;
    }


    public List<ExpressRepair> getAllServices(){

        int size = this.available_express_services.size();
        List<ExpressRepair> answer = new ArrayList<>();
        for(int i = 1; i <= size; i++){
            answer.add(this.available_express_services.get(i).clone());
        }
        return answer;
    }

    /**
     * Add a new ExpressRepair service, associating it with the new
     * token available, as it's key
     *
     * @param er
     * @return
     */
    public boolean add(ExpressRepair er){

        // se o objeto já existir, devolve falso
        if(this.available_express_services.containsValue(er)){
            return false;
        }
        // Se não existir, adiciona-o
        int my_token;
        try{
            this.locker.lock();
            my_token = this.nextToken;
            this.nextToken++;
            this.available_express_services.put(my_token, er.clone());
            return true;
        } finally {
            this.locker.unlock();
        }
    }

    /**
     * Removes an ExpressRepair object from the system
     * @param er
     * @return
     */
    public boolean remove(ExpressRepair er){

        // Aqui, sei que ele existe, então o token vai ter sempre valor != 0
        if(this.available_express_services.containsValue(er)){

            int token = 0;
            for(Map.Entry<Integer, ExpressRepair> e : this.available_express_services.entrySet()){
                if(e.getValue().equals(er))
                    token = e.getKey();
            }
            try{
                this.locker.lock();
                this.available_express_services.remove(token);
                return true;
            } finally {
                this.locker.unlock();
            }
        }
        // Se não existir, posso eliminá-la
        return false;
    }

    public boolean remove(int token){
        if(this.available_express_services.containsKey(token)){
            this.available_express_services.remove(token);
            return true;
        }
        return false;
    }

    /**
     * If the given expressrepair is mapped, it updates it
     * @param er
     * @return
     */
    public boolean update(ExpressRepair er){

        // Se estiver presente, atualiza-o
        if(this.available_express_services.containsValue(er)){

            int token = 0;
            for(Map.Entry<Integer, ExpressRepair> e : this.available_express_services.entrySet()){
                if(e.getValue().equals(er))
                    token = e.getKey();
            }

            try{
                this.locker.lock();
                this.available_express_services.put(token, er.clone());
                return true;
            } finally {
                this.locker.unlock();
            }
        }
        // Se não estiver presente, é porque não pode atualizar nada
        return false;
    }
}