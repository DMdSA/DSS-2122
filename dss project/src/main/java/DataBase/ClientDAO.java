package DataBase;

import BusinessLayer.Client.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientDAO extends DAOClass{

    public Map<String,Client> clientDAO = new HashMap<>(); //<NIF,Client>


    public Client get(String nif){

        return clientDAO.get(nif);
    }

    public boolean insert(Client client){

        if(clientDAO.containsKey(client.getNIF())){
            return false;
        }
        else{
            clientDAO.put(client.getNIF(),client);
            return true;
        }
    }

    public boolean remove(String username){

        if(!clientDAO.containsKey(username)){
            return false;
        }
        else{
            clientDAO.remove(username);
        }
        return false;
    }

    public boolean update(Client client){

        if(!clientDAO.containsKey(client.getNIF())){
            this.insert(client);
            return false;
        }
        else{
            this.remove(client.getNIF());
            this.insert(client);
            return true;
        }
    }

}
