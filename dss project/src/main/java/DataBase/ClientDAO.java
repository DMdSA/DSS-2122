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

    /**
     * Constructor
     *
     * This constructor creates a Map<String, Client> which maps each client's ID,
     * being "nif" the ID, to each client's object
     */
    public ClientDAO(){
        this.clients = new HashMap<>();
    }

    public ClientDAO(ClientDAO dao){
        this.clients = dao.get();
    }

    /**
     * Clone
     */
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

    /**
     * Verifies if there is a client registered to a given nif
     * @param nif
     * @return
     */
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

    /**
     * Searchs for a client by their phone number
     * @param phone
     * @return
     */
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
}