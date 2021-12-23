package BusinessLayer.Client;

public class Client {

    /**
     * Instance variables
     */
    private String name;
    private String nif;
    private String email;
    private String phone_number;

    /**
     * Constructors
     * @param name
     * @param nif
     * @param email
     */
    public Client(String name, String nif, String email){

        this.name = name; this.nif = nif; this.email = email;
    }

    public Client(String name, String nif, String email, String phone){

        this.name = name;
        this.nif = nif;
        this.email = email;
        this.phone_number = phone;
    }

    public Client(Client c){
        this.name = c.getName();
        this.nif = c.getNIF();
        this.email = c.getEmail();
        this.phone_number = c.getPhonenumber();
    }

    public Client clone(){
        return new Client(this);
    }


    /**
     * Setters
     */
    public void setName(String name) {this.name = name;}
    public void setNif(String nif){ this.nif = nif;}
    public void setEmail(String email) {this.email = email;}
    public void setPhone_number(String phonenumber) { this.phone_number = phonenumber;}

    /**
     * Getters
     */
    public String getName(){ return this.name;}
    public String getNIF() {return this.nif;}
    public String getEmail(){ return this.email;}
    public String getPhonenumber(){ return this.phone_number;}


    /**
     * toString
     */
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("[Client] Name:\"")
                .append(this.name)
                .append(" ");
        sb.append("Nif:\"")
                .append(this.nif)
                .append(" ");
        sb.append("email:\"")
                .append(this.email)
                .append("\"");

        return sb.toString();
    }


    /**
     * equals
     */
    public boolean equals(Object o){

        if(this == o) return true;
        if(this.getClass() != o.getClass() || o == null) return false;

        Client that = (Client) o;
        return this.name.equals(that.name) &&
                this.nif.equals(that.nif) &&
                this.email.equals(that.email) &&
                this.phone_number.equals(that.phone_number);
    }
}
