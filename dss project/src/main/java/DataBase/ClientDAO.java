package DataBase;

import BusinessLayer.Client.Client;
import BusinessLayer.Services.Service;

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



    /*
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


    public ClientDAO loadFileToObject() throws IOException, ClassNotFoundException {

        this.clients = new HashMap<>();

        FileInputStream fileStream = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(fileStream);

        Object o = input.readObject();

        if(o instanceof ClientDAO){
            return (ClientDAO) o;
        }
        return null;
    }
    */

}
