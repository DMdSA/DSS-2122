package DataBase;

import BusinessLayer.Client.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientDAO {

    // Map< NIF, CLIENT >
    public Map<String,Client> clients = new HashMap<>();


    /**
     *
     * @param nif
     * @return
     */
    public Client get(String nif){
        return clients.get(nif);
    }

    // Como obter clientes por Número de telemóvel?
    // Só se percorresse cliente a cliente

    /**
     *
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
     *
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
     *
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
