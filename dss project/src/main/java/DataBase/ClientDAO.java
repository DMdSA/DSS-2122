package DataBase;

import BusinessLayer.Client;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ClientDAO implements Serializable {

    /**
     * Instance Variables
     */
    // Map< NIF, CLIENT >
    private Map<String,Client> clients;
    private static final String path = "C:\\Users\\diogo\\Ambiente de Trabalho\\UNIVERSIDADE MINHO\\3ano\\1semestre\\Desenvolvimento Sistemas Software\\Trabalho Pratico\\dss project\\src\\main\\java\\DataBase\\clients";

    /**
     * Constructor
     *
     * This constructor creates a Map<String, Client> which maps each client's ID,
     * being "nif" the ID, to each client's object
     */
    public ClientDAO(){
        this.clients = new HashMap<>();
        //TODO
    }


    public ClientDAO(ClientDAO dao){
        this.clients = dao.get();
    }

    public ClientDAO clone(){
        return new ClientDAO(this);
    }


    /**
     * Get the number of clients saved on system
     * @return
     */
    public int getSize(){
        return this.clients.size();
    }


    public boolean hasClient(String nif){
        return this.clients.containsKey(nif);
    }



    /**
     * Get a client by his id
     * @param nif Client's nif and ID
     * @return
     */
    public Client getByNif(String nif){
        return this.clients.getOrDefault(nif, null);
    }

    public Client getByPhone(String phone){

        for(Map.Entry<String, Client> e : this.clients.entrySet()){

            if(e.getValue().getPhonenumber().equals(phone)){
                return e.getValue();
            }
        }
        return null;
    }


    /**
     * Get all clients
     */
    public Map<String, Client> get(){

        Map<String, Client> answer = new HashMap<>();

        for(Map.Entry<String, Client> e : this.clients.entrySet())
            answer.put(e.getKey(),e.getValue().clone());

        return answer;
        // TODO clone??
    }


    /**
     * Add a new client
     * @param client
     * @return true if it is a new client and successfully added
     */
    public boolean add(Client client){

        if(clients.containsKey(client.getNIF())){
            // User already exists
            return false;
        }
        else{
            // User is added to MAP of clients
            clients.put(client.getNIF(),client);
            return true;
        }
    }

    /**
     * Remove an existing client
     * @param NIF Client's id
     *
     * If a client with given id is found, it is removed.
     * Client's nif used as his ID
     *
     * @return
     */
    public boolean remove(String NIF){

        if(!clients.containsKey(NIF)){
            // The requested client does not exist
            return false;
        }
            // The client is successfully removed
            clients.remove(NIF);
            return true;
    }

    /**
     * Update an existing client
     * @param client client being updated
     *
     * Uses client's nif to see if he already exists in database.
     * If an occurrence is found, it is updated.
     *
     * @return
     */
    public boolean update(Client client){

        // Só dá update se o cliente existir, senão, terá de adicionar um novo
        if(this.clients.containsKey(client.getNIF())){
            this.clients.put(client.getNIF(), client);
            return true;
        }
        return false;
    }


    /**
     * Writes a ClientDAO to an object file
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
     * Tries to load a ClientDAO object from an object file
     * @return ClientDAO, if found, null otherwise
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ClientDAO loadFileToObject() throws IOException, ClassNotFoundException {

        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);

        Object o = input.readObject();

        if(o instanceof ClientDAO){
            return (ClientDAO) o;
        }
        return null;
    }

}
