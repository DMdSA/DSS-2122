package BusinessLayer.Equipments;

import java.io.Serializable;

public enum BudgetStatus implements Serializable {

    WITHOUT_BUDGET(1),
    DOING_BUDGET(2),
    WAITING_APPROVAL(3),
    DECLINED(4),
    WAITING_REPAIR(5),
    REPAIRING(6),
    ON_HOLD(7),
    WAITING_PICKUP(8),
    DELIVERED(9),
    ARCHIVED(10);

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
            case 2 -> "DOING_BUDGET";
            case 3 -> "WAITING_APPROVAL";
            case 4-> "DECLINED";
            case 5 -> "WAITING_REPAIR";
            case 6 -> "REPAIRING";
            case 7 -> "ON_HOLD";
            case 8 -> "WAITING_PICKUP";
            case 9 -> "DELIVERED";
            case 10 -> "ARCHIVED";
            default -> "?error?";
        };
    }

    public static BudgetStatus getStatus(int v){
        return switch (v) {
            case 1 -> WITHOUT_BUDGET;
            case 2 -> DOING_BUDGET;
            case 3 -> WAITING_APPROVAL;
            case 4 -> DECLINED;
            case 5 -> WAITING_REPAIR;
            case 6 -> REPAIRING;
            case 7 -> ON_HOLD;
            case 8 -> WAITING_PICKUP;
            case 9 -> DELIVERED;
            case 10 -> ARCHIVED;
            default -> null;
        };
    }
}
