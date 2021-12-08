package BusinessLayer.Client;


import BusinessLayer.IClient;
import DataBase.ClientDAO;

public class ClientFacade implements IClient {

    // search why this visibility

    // Is there any need for this class?


    Client client;

    public String nif(){return client.getNIF();}

    public String name(){ return client.getName();}

    public String phonenumber(){ return client.getPhonenumber();}

    public String email(){ return client.getEmail();}

    public String birthday() { return client.getBirthday().toString();}

}
