package BusinessLayer.Client;

import BusinessLayer.IClient;

public class ClientFacade implements IClient {

    // search why this visibility
    Client c;

    public String nif(){return c.getNIF();}

    public String name(){ return c.getName();}

    public String phonenumber(){ return c.getPhonenumber();}

    public String email(){ return c.getEmail();}

    public String birthday() { return c.getBirthday().toString();}

}
