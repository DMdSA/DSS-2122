package BusinessLayer;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.ExpressRepair;
import org.javatuples.Triplet;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IServices {

    boolean registerExpressService();

    boolean update_budget(Budget b, double new_price, List<Triplet<String, String, Double>> new_passos, BudgetStatus new_budgetStatus);

    boolean registerBudgetRequest(WorkersFacade wf, Service service);

    boolean addBudget(Budget b);

    boolean removeBudget(Budget b);

    List<Budget> getBudgetbyNif(String nif);

    List<Object> consultExpressServices();

    int numberOfExpressServices();

    ExpressRepair getExpressService(int token);

    boolean hasExpressServices();

    boolean consultBudgetRequests();

    boolean hasBudgetRequests();

    Map<UUID, Budget> getBudgetRequestsbyStatus(BudgetStatus bs);

    boolean updateBudgetStatus(Budget b, BudgetStatus bs);

    void save();

    ServicesFacade load() throws IOException, ClassNotFoundException;
}
