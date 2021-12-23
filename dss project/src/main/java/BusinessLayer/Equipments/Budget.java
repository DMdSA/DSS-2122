package BusinessLayer.Equipments;
import org.javatuples.Triplet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Budget {

    private UUID budgetID;                                                      // budget id

    private String clientID;                                                    // Client
    private Equipment equip;                                                    // Equipamento
    private double finalPrice;                                                  // Preço final
    private String tech_username;                                               // Technician username
    private LocalDate requestDate;                                              // Data pedido
    private List<Triplet<String, LocalTime, Double>> estimatedTODO;             // Passos
    private BudgetStatus status;                                                // Status


    public Budget(String clientID, Equipment e, String tech_username){

        this.budgetID = UUID.randomUUID();

        this.clientID = clientID;
        this.equip = e;
        this.equip.setTechnicianResponsible(this.tech_username);
        this.finalPrice = -1;
        this.tech_username = tech_username;
        this.requestDate = LocalDate.now();
        this.estimatedTODO = new ArrayList<>();
        this.status = BudgetStatus.WITHOUT_BUDGET;
    }


    /**
     * Getters
     */
    //falta clone ??

    public UUID getBudgetID(){return this.budgetID;}
    public String getClientID(){ return this.clientID;}
    public Equipment getEquip(){ return this.equip;}
    public Double getFinalPrice(){return this.finalPrice;}
    public String getTech_username(){ return this.tech_username;}
    public LocalDate getRequestDate(){ return this.requestDate;}
    public List<Triplet<String, LocalTime, Double>> getTodo(){ return new ArrayList<>(this.estimatedTODO);}
    public BudgetStatus getStatus(){ return this.status;}


    /**
     * Setters
     */
    // clones ???
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public void setEquip(Equipment equip){
        this.equip = equip;
    }
    public void setFinalPrice(double price){
        this.finalPrice = price;
    }
    public void setTech_username(String user){
        this.tech_username = user;
    }
    public void setTodo(List<Triplet<String,LocalTime,Double>> list){
        this.estimatedTODO = list;
    }
    public void setStatus(BudgetStatus status){
        this.status = status;
    }


    /**
     * equals
     */
    public boolean equals(Object o){




    }


    /**
     * String
     */
}
