package DataBase;

import BusinessLayer.Client.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientDAO {

    /**
     * Instance Variables
     */
    // Map< NIF, CLIENT >
    private Map<String,Client> clients = new HashMap<>();

    /**
     * Constructor
     */
    public ClientDAO(){
        this.clients = new HashMap<>();
        //TODO
    }


    /**
     * Get a client by his nif
     * @param nif
     * @return
     */
    public Client get(String nif){
        return clients.get(nif);
    }

    // Como obter clientes por Número de telemóvel?
    // Só se percorresse cliente a cliente


    /**
     * Get all clients
     */
    public Map<String, Client> get(){
        return this.clients;
        // TODO clone??
    }


    /**
     * Add a new client
     * @param client
     * @return
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
     * Remove a client
     * @param NIF
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
     * Update a client
     * @param client
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
