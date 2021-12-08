package BusinessLayer.Equipments;
import org.javatuples.Triplet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Budget {

    // falta id do budget

    private Equipment equip;
    private String clientID;
    private double finalPrice;
    private String counterWorker_username;
    private String tech_username;
    private LocalDate requestDate;
    private List<Triplet<String, LocalTime, Double>> estimatedTODO;
    private List<Triplet<String, LocalTime, Double>> actualTODO;
    private BudgetStatus status;


    public Budget(Equipment e, String c_id, String cw, LocalDate d){

        this.equip = e;
        this.clientID = c_id;
        this.counterWorker_username = cw;
        this.requestDate = d;
        this.estimatedTODO = new ArrayList<>();
        this.actualTODO = new ArrayList<>();
        this.status = BudgetStatus.WITHOUT_BUDGET;
    }

    /**
     * Getters
     */
    //falta clone
    public Equipment getEquip(){ return this.equip;}
    public String getClientID(){ return this.clientID;}
    public Double getFinalPrice(){return this.finalPrice;}
    public String getCounterWorker_username(){ return this.counterWorker_username;}
    public String getTech_username(){ return this.tech_username;}
    public LocalDate getRequestDate(){ return this.requestDate;}


    public BudgetStatus getStatus(){ return this.status;}


    /**
     * Setters
     */
    public void setFinalPrice(Double p){ this.finalPrice = p;}
    public void setTech_username(String tech_username){this.tech_username = tech_username;}
    public void setEstimatedTODO(List<Triplet<String, LocalTime, Double>> todo){ this.estimatedTODO = new ArrayList<>(todo);}
    public void setActualTODO(List<Triplet<String, LocalTime, Double>> todo){ this.actualTODO = new ArrayList<>(todo);}
    public void setStatus(BudgetStatus bs){ this.status = bs;}




}
