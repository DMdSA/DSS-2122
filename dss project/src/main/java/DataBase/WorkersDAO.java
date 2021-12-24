package DataBase;

import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.Map;

public class WorkersDAO {

    /**
     * Instance Variables
     */
    Map<Hierarchy, Map<String, Worker>> workersDAO;

    /**
     * Constructor
     */
    public WorkersDAO(){
        this.workersDAO = new HashMap<>();
        for(Hierarchy h : Hierarchy.values()){
            this.workersDAO.put(h, new HashMap<>());
        }

        // TODO TODO TODO
    }

    /**
     * add a new worker
     * @param w
     * @return
     */
    public boolean add(Worker w){

        Hierarchy h = w.getHierarchy();

        // Se já o tiver na base
        if(this.workersDAO.get(h).containsKey(w.getUser())){

            // Se forem exatamente iguais, não foi necessário adicionar
            if(w.equals(this.workersDAO.get(h).get(w.getUser()))){
                return false;
            }
        }

        // se não forem iguais, adicionar
        this.workersDAO.get(h).put(w.getUser(), w); //clone?
        return true;
    }

    /**
     * remove a worker
     * @param w
     * @return
     */
    public boolean remove(Worker w){

        Hierarchy h = w.getHierarchy();

        // Se ele existir, remove-o
        if(this.workersDAO.get(h).containsKey(w.getUser())){
            this.workersDAO.get(h).remove(w.getUser());
            return true;
        }
        // Caso contrário, não faz nada
        return false;
    }

    /**
     * get a worker by his hierarchy and user
     * @param h
     * @param user
     * @return
     */
    public Worker get(Hierarchy h, String user){

        // Se ele existir, devolve-o
        if(this.workersDAO.get(h).containsKey(user)){

            return this.workersDAO.get(h).get(user);
        }
        // Se não existir, devolve null
        return null;
    }

    public Map<String, Worker> get(Hierarchy h){

        return this.workersDAO.get(h);
        // TODO clone???
    }



    /**
     * update a worker
     * @param w
     * @return
     */
    public boolean update(Worker w){

        Hierarchy h = w.getHierarchy();

        Map<String, Worker> workers = this.workersDAO.get(h);

        // Se existir, pode atualizá-lo, ou não
        if(workers.containsKey(w.getUser())){

            // Se forem iguais, não há necessidade de alterar
            if(workers.get(w.getUser()).equals(w)){
                return false;
            }
            // Se não forem iguais, deve ser atualizado
            this.workersDAO.get(h).put(w.getUser(), w); // clone??
        }

        // Se não existia na base, deve ser adicionado, não alterado
        return false;

    }


}
