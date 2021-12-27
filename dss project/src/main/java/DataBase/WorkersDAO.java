package DataBase;

import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WorkersDAO implements Serializable {

    /**
     * Instance Variables
     */
    Map<Hierarchy, Map<String, Worker>> workersDAO;
    private static final String path = "C:\\Users\\diogo\\Ambiente de Trabalho\\UNIVERSIDADE MINHO\\3ano\\1semestre\\Desenvolvimento Sistemas Software\\Trabalho Pratico\\dss project\\src\\main\\java\\DataBase\\workers";

    /**
     * Constructor
     *
     * For each hierarchy in system,
     * it creates a Map which maps each worker's username to it's object
     */
    public WorkersDAO(){
        this.workersDAO = new HashMap<>();
        for(Hierarchy h : Hierarchy.values()){
            this.workersDAO.put(h, new HashMap<>());
        }

        // TODO TODO TODO
    }


    public boolean hasWorker(Hierarchy h, String user){

        return this.workersDAO.get(h).containsKey(user);
    }

    public Worker confirmWorker(String user, String pass){

        for(Map.Entry<Hierarchy, Map<String, Worker>> e : this.workersDAO.entrySet()){

            for(Map.Entry<String, Worker> ee : e.getValue().entrySet()){

                if(ee.getKey().equals(user) && ee.getValue().getPass().equals(pass))
                    return ee.getValue().clone();
            }
        }
        return null;
    }


    /**
     * Gets the number of workers saved on system
     * @return
     */
    public int getSize(){

        int counter = 0;

        for(Map.Entry<Hierarchy, Map<String,Worker>> e : this.workersDAO.entrySet()){

            counter += e.getValue().size();
        }
        return counter;
    }


    /**
     * Add a new worker to database
     * @param w worker being added
     * @return true if the worker is new and successfully added
     */
    public boolean add(Worker w){

        Hierarchy h = w.getHierarchy();

        // Se já o tiver na base
        if(this.workersDAO.get(h).containsKey(w.getUser())){
            return false;
        }
        // Se for novo, adicionar
        this.workersDAO.get(h).put(w.getUser(), w); //clone?
        return true;
    }

    /**
     * Remove an existing worker
     * @param w worker being removed
     * @return true, if he existed and has been removed
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
     * Get an existing worker by his hierarchy and user
     * @param h hierarchy
     * @param user worker's username
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
     * Updates an existing worker
     * @param w Worker object
     * @return if the worker existed and has been updated
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

    /**
     * Writes a WorkersDAO object to a file
     * path is specified in object's instance variables
     */
    public void WriteObjectToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads an WorkersDAO object from an object file
     * @return WorkersDAO object, or null
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static WorkersDAO loadFileToObject() throws IOException, ClassNotFoundException {

        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);

        Object o = input.readObject();

        if(o instanceof WorkersDAO){
            return (WorkersDAO) o;
        }
        return null;
    }

}
