package BusinessLayer.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {

    private String name;
    private String nif;
    private String email;
    private LocalDate birthday;
    private String phone_number;

    public Client(){

    }



    public Client(String name, String nif, String email){

        this.name = name; this.nif = nif; this.email = email;
        this.birthday = null;
        this.phone_number = null;
    }


    public Client(String name, String nif, String email, String birthday, String number){

        this.name = name;
        this.nif = nif;
        this.email = email;
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.phone_number = number;
    }


    public void setName(String name) {this.name = name;}
    public void setNif(String nif){ this.nif = nif;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone_number(String phonenumber) { this.phone_number = phonenumber;}
    public void setBirthday(String birthday){ this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"));}

    public String getName(){ return this.name;}
    public String getNIF() {return this.nif;}
    public String getEmail(){ return this.email;}
    public String getBirthday(){ return this.birthday.toString();}
    public String getPhonenumber(){ return this.phone_number;}
}
