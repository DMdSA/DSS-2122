package BusinessLayer.Equipments;

import java.io.Serializable;

public enum BudgetStatus implements Serializable {

    WITHOUT_BUDGET(1),
    WAITING_APPROVAL(2),
    DECLINED(3),
    WAITING_REPAIR(4),
    REPAIRING(5),
    WAITING_PICKUP(6),
    DELIVERED(7),
    ARCHIVED(8);

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
            case 5 -> "REPAIRING";
            case 6 -> "WAITING_PICKUP";
            case 7 -> "DELIVERED";
            case 8 -> "ARCHIVED";
            default -> "?error?";
        };
    }

    public static BudgetStatus getStatus(int v){
        return switch (v) {
            case 1 -> WITHOUT_BUDGET;
            case 2 -> WAITING_APPROVAL;
            case 3 -> DECLINED;
            case 4 -> WAITING_REPAIR;
            case 5 -> REPAIRING;
            case 6 -> WAITING_PICKUP;
            case 7 -> DELIVERED;
            case 8 -> ARCHIVED;
            default -> null;
        };
    }
}
