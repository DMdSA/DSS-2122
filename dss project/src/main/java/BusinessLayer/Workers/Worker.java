package BusinessLayer.Workers;

import java.io.Serializable;

public class Worker implements Serializable {

    /**---------------------
     * Instance Variables---
     */
    private String user;
    private String pass;
    private String name;
    private String nif;
    private String phone;
    private Hierarchy hierarchy;

    /**
     * Constructors
     */
    public Worker(){
        this.user = null;
        this.pass = null;
        this.name = null;
        this.nif = null;
        this.phone = null;
        this.hierarchy = null;
    }

    public Worker(String user, String pass){

        this.user = user;
        this.pass = pass;
        this.name = null;
        this.nif = null;
        this.phone = null;
        this.hierarchy = null;
    }

    public Worker(String user, String pass, String name, String nif, String phone){

        this.user = user;
        this.pass = pass;
        this.name = name;
        this.nif = nif;
        this.phone = phone;
        this.hierarchy = null;
    }


    public Worker(Worker w){
        this.user = w.getUser();
        this.pass = w.getPass();
        this.name = w.getName();
        this.nif = w.getNif();
        this.phone = w.getPhone();
        this.hierarchy = w.getHierarchy();
    }

    public Worker clone(){
        return new Worker(this);
    }


    /**
     * Getters
     */
    public String getUser(){return this.user;}
    public String getPass(){return this.pass;}
    public String getName(){return this.name;}
    public String getNif(){return this.nif;}
    public String getPhone(){ return this.phone;}
    public Hierarchy getHierarchy(){ return this.hierarchy;}

    /**
     * Setters
     */
    public void setUser(String user){this.user=user;}
    public void setPass(String pass){this.pass=pass;}
    public void setName(String name){this.name=name;}
    public void setNif(String nif){this.nif = nif;}
    public void setPhone(String phone){this.phone=phone;}
    public void setHierarchy(Hierarchy h){this.hierarchy = h;}

    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("User:\"")
                .append(user)
                .append("\" | Password:\"")
                .append(pass)
                .append("\" | name:\"")
                .append(name)
                .append("\" | Nif:\"")
                .append("\" | Phone:\"")
                .append(phone)
                .append("\"");
        return sb.toString();
    }

    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o==null) return false;

        Worker that = (Worker) o;
        return this.user.equals(that.user) &&
                this.pass.equals(that.pass) &&
                this.name.equals(that.name) &&
                this.nif.equals(that.nif) &&
                this.phone.equals(that.phone) &&
                this.hierarchy == that.hierarchy;
    }


}