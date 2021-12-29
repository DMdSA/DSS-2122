package BusinessLayer;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.Workers.Worker;
import org.javatuples.Triplet;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IServices {

    /**
     * Auxiliar Function to get the number of budgets and reparations of the techs
     */
    List<Triplet<String, Integer,Integer>> getTechStats(List<String> list_techs);

    /**
     * Get a budget by it's client's ID/NIF
     */
    List<Budget> getBudgetbyNif(String nif);

    /**
     * Given a specific id/token/integer, gets the associated express service
     */
    ExpressRepair getExpressService(int token);

    /**
     * Gets a technicians's budgets (that he worked on)
     */
    List<Budget> getTechBudgets(Worker w);

    /**
     * Gets all budgets associated with a specific budget status
     */
    Map<UUID, Budget> getBudgetRequestsbyStatus(BudgetStatus bs);

    /**
     * Gets the first element from the given BudgetStatus' list, which is the oldest budget in that list
     */
    Budget get_first(BudgetStatus b);

    /**
     * Registers a new budget request / normal service
     * @return
     */
    boolean registerBudgetRequest(WorkersFacade wf, Service service);

    /**
     * Adds a new budget to database
     */
    boolean addBudget(Budget b);

    /**
     * Removes a budget from database
     */
    boolean removeBudget(Budget b);

    /**
     * Updates a budget
     */
    boolean update_budget(Budget b, double new_price, List<Triplet<String, String, Double>> new_passos, BudgetStatus new_budgetStatus);

    /**
     * updates a budget on database
     */
    void update_budget(Budget b);

    /**
     * Updates a budget to a new line of status
     */
    boolean updateBudgetStatus(Budget b, BudgetStatus bs);

    /**
     * Returns a list of all available express services on the repairing center
     */
    List<Object> consultExpressServices();

    /**
     * Returns the number of express services available on the repairing center
     */
    int numberOfExpressServices();

    /**
     * Verifies if the system has any express services available
     */
    boolean hasExpressServices();

    /**
     * Verifies if the system has any budgets registered
     */
    boolean hasBudgetRequests();

    /**
     * Verifies if the system has any budgets on a specific line of status
     */
    boolean hasBudgetRequestByStatus(BudgetStatus budgetStatus);

    /**
     * todo
     */
    List<Budget> Repairing_budgets(String username);

    /**
     * Puts the status of a Budget ON_HOLD
     */
    List<Budget> On_Hold_Budgets(String username);

    /**
     * Updates the State of a Budget to REPAIRING
     */
    void continue_reparation(Budget b);

    /**
     * Auxiliar Function to, depending on the Tech's choice, gives him the oldest Budget in WITHOUT_BUDGET
     * or in WAITING_REPAIR
     */
    Budget putTechWorking(BudgetStatus budgetStatus, String username);

    /**
     * Saves the system's state in an object file
     */
    void save();

    /**
     * Loads the system's state from an object file
     */
    ServicesFacade load() throws IOException, ClassNotFoundException;
}
