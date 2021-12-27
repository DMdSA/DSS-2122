package BusinessLayer.Equipments;

import java.io.Serializable;

public enum BudgetStatus implements Serializable {

    WITHOUT_BUDGET(1),
    WAITING_APPROVAL(2),
    DECLINED(3),
    WAITING_REPAIR(4),
    WAITING_PICKUP(5),
    DELIVERED(6),
    ARCHIVED(7);

    /**
     * Instance Variable
     */
    private final int value;

    /**
     * Constructor
     */
    BudgetStatus(int v){
        this.value = v;
    }

    /**
     * Getters
     */
    public int getValue(){
        return this.value;
    }


    public String getStatusDesc(){

        return switch (this.value){
            case 1 -> "WITHOUT_BUDGET";
            case 2 -> "WAITING_APPROVAL";
            case 3 -> "DECLINED";
            case 4 -> "WAITING_REPAIR";
            case 5 -> "WAITING_PICKUP";
            case 6 -> "DELIVERED";
            case 7 -> "ARCHIVED";
            default -> "?error?";
        };
    }
}
