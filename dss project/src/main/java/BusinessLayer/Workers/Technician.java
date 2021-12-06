package BusinessLayer.Workers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Technician {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phonenumber;
    private LocalDate firstday;


    public Technician(String username, String password){

        this.username = username;
        this.password = password;
        LocalDate now = LocalDate.now();
        this.firstday = LocalDate.parse(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email){this.email = email;}

    public void setPhonenumber(String phonenumber){
        this.phonenumber = phonenumber;
    }


    public String getName(){ return this.name;}

    public String getPhonenumber(){ return this.phonenumber;}

    public String getEmail() {return this.email;}

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}


}
