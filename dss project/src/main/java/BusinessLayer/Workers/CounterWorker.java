package BusinessLayer.Workers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CounterWorker {

    private String username;
    private String password;
    private boolean authenticated;

    private String name;
    private String email;
    private String phonenumber;
    private LocalDate firstday;


    public CounterWorker(String username, String password){

        this.username = username;
        this.password = password;
        LocalDate now = LocalDate.now();
        this.firstday = LocalDate.parse(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.authenticated = false;
        this.email = null; this.phonenumber = null;
        this.name = null;
    }

    /**
     * Setters
     */
    public void setUsername(String u){this.username=u;}
    public void setPassword(String p){this.password=p;}
    public void setAuthenticated(boolean a){this.authenticated=a;}
    public void setName(String n) {
        this.name = n;
    }
    public void setEmail(String e){this.email = e;}
    public void setPhone(String pn){
        this.phonenumber = pn;
    }

    /**
     * Getters
     */
    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public boolean getAuthenticated(){return this.authenticated;}
    public String getName(){ return this.name;}
    public String getPhone(){ return this.phonenumber;}
    public String getEmail() {return this.email;}

}
