package BusinessLayer.Workers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CounterWorker {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phonenumber;
    private LocalDate firstday;
    private boolean autenticated;


    public CounterWorker(String username, String password){

        this.username = username;
        this.password = password;
        LocalDate now = LocalDate.now();
        this.firstday = LocalDate.parse(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.autenticated = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email){this.email = email;}

    public void setPhonenumber(String phonenumber){
        this.phonenumber = phonenumber;
    }

    public void setAutenticatedStatus(boolean b){this.autenticated = b;}

    public boolean getAutenticatedStatus(){return this.autenticated;}

    public String getName(){ return this.name;}

    public String getPhonenumber(){ return this.phonenumber;}

    public String getEmail() {return this.email;}
    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}

}
