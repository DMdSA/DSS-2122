package UI;

import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Workers.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static UI.ShopUI.clearview;

public class WorkersUI {

    /**
     * Instance Variables
     */
    private ShopUI shopUI;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss"); //formatter to convert from string to LocalTime

    /**
     * Constructor
     */
    public WorkersUI(ShopUI shopUI){
        this.shopUI = shopUI;
    }



    void RegisterWorkerMenu() throws IOException, ClassNotFoundException {

        clearview();
        Menu register_worker = new Menu("Register Worker",
                Arrays.asList(
                          "Back"
                        , "Counter Worker"
                        , "Technician"
                        , "Manager"
                ));

        register_worker.setHandler(0, shopUI::MainMenu);
        register_worker.setHandler(1, () -> RegisterWorker(Hierarchy.COUNTER));
        register_worker.setHandler(2, () -> RegisterWorker(Hierarchy.TECHNICIAN));
        register_worker.setHandler(3, () -> RegisterWorker(Hierarchy.MANAGER));
        register_worker.run();
    }

    /**
     * Registers a new Worker to the system
     * @param h
     */
    private void RegisterWorker(Hierarchy h) throws IOException, ClassNotFoundException{

        clearview();
        System.out.println("[New " + h.getHierarchyName() + "]\n");

        System.out.print("username: ");
        String username = this.shopUI.getScanner().nextLine();

        if(this.shopUI.getWorkers_facade().hasWorker(Hierarchy.COUNTER, username) || this.shopUI.getWorkers_facade().hasWorker(Hierarchy.TECHNICIAN, username)
                        || this.shopUI.getWorkers_facade().hasWorker(Hierarchy.MANAGER, username)){

            System.out.println("error: that username is already registered");
            return;
        }

        System.out.print("password: ");
        String password = this.shopUI.getScanner().nextLine();

        System.out.print("name: ");
        String name = this.shopUI.getScanner().nextLine();

        System.out.print("nif: ");
        String nif = this.shopUI.getScanner().nextLine();

        System.out.print("phone: ");
        String phone = this.shopUI.getScanner().nextLine();

        boolean flag = false;


        switch (h){
            case COUNTER :

                flag = this.shopUI.getWorkers_facade().registerWorker(new Counter(username, password, name, nif, phone));
                break;
            case TECHNICIAN :
                flag = this.shopUI.getWorkers_facade().registerWorker(new Technician(username, password, name, nif, phone));
                break;
            case MANAGER:
                flag = this.shopUI.getWorkers_facade().registerWorker(new Manager(username, password, name, nif, phone));
                break;
            default:
                break;
        }
        if(!flag) {
            System.out.println("\nerror: could not add the new worker!");
            shopUI.pause();
            clearview();
            return;
        }
        System.out.println("\nSuccessfully added worker [" + name + "]!");
        shopUI.pause();
        clearview();
    }




    /**
     * Menu to consult workers on the system
     */
    void ConsultWorker() throws IOException, ClassNotFoundException {

        clearview();
        Menu worker_type = new Menu("Search Workers",
                Arrays.asList(
                          "Back"
                        , "Counter Workers"
                        , "Counter Worker by user"
                        , "Technicians"
                        , "Technician by user"
                        , "Managers"
                        , "Manager by user"
                ));

        worker_type.setHandler(0, shopUI::MainMenu);
        worker_type.setHandler(1, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.COUNTER));
        worker_type.setHandler(2, () -> PrintWorker(Hierarchy.COUNTER));
        worker_type.setHandler(3, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.TECHNICIAN));
        worker_type.setHandler(4, () -> PrintWorker(Hierarchy.TECHNICIAN));
        worker_type.setHandler(5, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.MANAGER));
        worker_type.setHandler(6, () -> PrintWorker(Hierarchy.MANAGER));

        worker_type.run();
    }


    /**
     *  MENU Update Worker
     */
    private void MenuUpdateWorker(Hierarchy h, Worker w) throws IOException, ClassNotFoundException{

        Menu UpdateWorker = new Menu("Update Worker",
                            Arrays.asList(
                                    "Back"
                                    , "Update Password"
                                    , "Update Name"
                                    , "Update Phone"
                                    , "Update NIF"
                            ));

        UpdateWorker.setHandler(0, this::ConsultWorker);
        UpdateWorker.setHandler(1, ()->UpdateWorkerPass(h, w));
        UpdateWorker.setHandler(2, ()->UpdateWorkerName(h, w));
        UpdateWorker.setHandler(3, ()->UpdateWorkerPhone(h, w));
        UpdateWorker.setHandler(3, ()->UpdateWorkerNif(h, w));

        UpdateWorker.run();
    }


    private void UpdateWorkerName(Hierarchy h, Worker worker){

        System.out.print("#> new Name: ");
        String name = this.shopUI.getScanner().nextLine();
        this.shopUI.getWorkers_facade().updateWorkerName(h, worker, name);

        boolean flag = shopUI.getWorkers_facade().updateWorkerPhone(h, worker, name);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }

        shopUI.pause(); clearview();
    }


    private void UpdateWorkerPass(Hierarchy h, Worker worker){

        System.out.print("#> new Password: ");
        String pass = this.shopUI.getScanner().nextLine();
        this.shopUI.getWorkers_facade().updateWorkerPass(h, worker, pass);

        boolean flag = shopUI.getWorkers_facade().updateWorkerPhone(h, worker, pass);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }

        shopUI.pause(); clearview();
    }

    private void UpdateWorkerNif(Hierarchy h, Worker worker){

        System.out.print("#> new NIF: ");
        String nif = this.shopUI.getScanner().nextLine();
        this.shopUI.getWorkers_facade().updateWorkerNif(h, worker, nif);

        boolean flag = shopUI.getWorkers_facade().updateWorkerPhone(h, worker, nif);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }

        shopUI.pause(); clearview();
    }

    private void UpdateWorkerPhone(Hierarchy h, Worker worker){

        System.out.print("#> new Phone: ");
        String phone = this.shopUI.getScanner().nextLine();

        boolean flag = shopUI.getWorkers_facade().updateWorkerPhone(h,worker,phone);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }

        shopUI.pause(); clearview();
    }


    /**
     * Prints a requested user by it's hierarchy, asking for his user
     * @param h
     */
    private void PrintWorker(Hierarchy h) throws IOException, ClassNotFoundException {

        clearview();
        String user = null;
        System.out.print("Worker username: ");
        user = this.shopUI.getScanner().nextLine();

        // If the worker exists, it is printed
        if(this.shopUI.getWorkers_facade().hasWorker(h, user)){

            Worker w = this.shopUI.getWorkers_facade().getWorker(h, user);
            System.out.println("\nGot worker: " + w.toString() +"\n");

            if(w instanceof Counter){

                Counter c = (Counter) w;
                Pair<Integer,Integer> pair = this.shopUI.getWorkers_facade().getCounterStatistics(c);
                System.out.println("[Receptions]: " + pair.getValue0() + "  [Deliveries]: " + pair.getValue1() + "\n");
            }

            this.shopUI.pause();
            MenuUpdateWorker(h, w);
            clearview();
        }
        else{
            System.out.println("error: That worker is not registered");
            shopUI.pause();
            clearview();
        }
    }

    /**
     * Verifies if the system has any technician available at the moment
     * @return
     */
    public boolean checkTechAvailability() {

        boolean flag = this.shopUI.getWorkers_facade().checkTechAvailability();
        if (flag) {
            System.out.println("#> There are technicians available");
            this.shopUI.pause();
        } else {
            System.out.println("#> error: there are no technicians available at the moment");
            this.shopUI.pause();
        }
        clearview();
        return flag;
    }

    public void isTechWorking(String username) throws IOException, ClassNotFoundException {
        clearview();
        List<Budget> budgets_pending = this.shopUI.getServices_facade().Repairing_budgets(username);  //List of budgets the user is doing budget or repairing
        List<Budget> budgets_onhold = this.shopUI.getServices_facade().On_Hold_Budgets(username);
        if(budgets_pending.size() == 0){                                                              //Locks a new option while he either has a reparation or budget pending
            Menu choose_work = new Menu("Choose Work",                                           //Has the option to create a new work or continue an on_hold reparation
                    Arrays.asList(
                            "Back"
                            , "Work on Budget"
                            , "Work on Repair"
                            , "Continue on hold reparation"
                    ));
            choose_work.setPreCondition(1,() -> this.shopUI.getServices_facade().hasBudgetRequestByStatus(BudgetStatus.WITHOUT_BUDGET));
            choose_work.setPreCondition(2,() -> this.shopUI.getServices_facade().hasBudgetRequestByStatus(BudgetStatus.WAITING_REPAIR));
            choose_work.setPreCondition(3,() -> (budgets_onhold.size() > 0));

            choose_work.setHandler(0, () -> this.shopUI.MainMenu());
            choose_work.setHandler(1, () -> this.Work_on_budgets(BudgetStatus.WITHOUT_BUDGET));
            choose_work.setHandler(2, () -> this.Work_on_budgets(BudgetStatus.WAITING_REPAIR));
            choose_work.setHandler(3, () -> this.Work_on_hold(budgets_onhold.get(0)));
            choose_work.run();

        } else{
                if(budgets_pending.get(0).getStatus() == BudgetStatus.DOING_BUDGET){
                    doing_budget_menu(budgets_pending.get(0), username);
                }
                else if(budgets_pending.get(0).getStatus() == BudgetStatus.REPAIRING){
                    repairing_menu(budgets_pending.get(0), username);
                }
         }
    }

    public void Work_on_budgets(BudgetStatus bs) throws IOException, ClassNotFoundException {
        this.shopUI.getWorkers_facade().update_worker_availability(shopUI.getUsername(),false);
        Budget b = this.shopUI.getServices_facade().putTechWorking(bs,shopUI.getUsername());
        System.out.println("Now doing a new task");
        System.out.println();
        System.out.println(b.toString());
        this.shopUI.pause();
        shopUI.MainMenu();
    }

    public void Work_on_hold(Budget b) throws IOException, ClassNotFoundException {
        this.shopUI.getServices_facade().continue_reparation(b);
        System.out.println("Now doing a new task");
        System.out.println();
        System.out.println(b.toString());
        this.shopUI.pause();
        shopUI.MainMenu();
    }

    public void doing_budget_menu(Budget b, String username) throws IOException, ClassNotFoundException {
        clearview();
        Menu choose_work_budget = new Menu("Choose Work",
                Arrays.asList(
                        "Back"
                        , "View Steps"
                        , "Add Step"
                        , "Rewrite Step"
                        , "Finish Budget"
                ));
        choose_work_budget.setPreCondition(3,() -> (b.getTodo().size() > 0));

        choose_work_budget.setHandler(0,() -> this.shopUI.MainMenu());
        choose_work_budget.setHandler(1,() -> this.View_steps(b));
        choose_work_budget.setHandler(2,() -> this.Add_steps(b));
        choose_work_budget.setHandler(3,() -> this.Rewrite_step(b));
        choose_work_budget.setHandler(4,() -> this.Finish_budget(b,username));
        choose_work_budget.run();
    }

    public void View_steps(Budget b){
        if(b.getTodo().size() == 0){
            System.out.println("There isn't any steps yet");
        }
        else{
            int counter = 1;
            clearview();
            for(Triplet<String, LocalTime,Double> e : b.getTodo()){
                System.out.println("------Step no." + counter + "------");
                System.out.println("Description: " + e.getValue0());
                System.out.println("Time: " + e.getValue1());
                System.out.println("Price: " + e.getValue2());
                counter++;
            }
        }
        this.shopUI.pause();
        clearview();
    }

    public void Add_steps(Budget b){
        boolean flag = true;
        double price = b.getEstimatedPrice();
        List<Triplet<String, LocalTime, Double>> aux = b.getTodo();
        while (flag) {
            clearview();
            System.out.print("Introduz a descrição do passo: ");
            String aux_s = shopUI.getScanner().nextLine();
            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();
            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();
            try {
                price = price + Double.parseDouble(aux_price);
                aux.add(new Triplet(aux_s, dtf.parse(aux_time), Double.parseDouble(aux_price)));
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                this.shopUI.pause();
                break;
            }
            System.out.print("Introduza 1 para adicionar mais um passo ou 0 para terminar: ");
            int aux_choice = Integer.parseInt(shopUI.getScanner().nextLine());
            if (aux_choice != 1) {
                flag = false;
            }
        }

        this.shopUI.getServices_facade().update_budget(b,price,aux,b.getStatus());
    }

    public void Rewrite_step(Budget b) {
        List<Triplet<String ,LocalTime,Double>> aux = b.getTodo();
        int n_steps = b.getTodo().size();
        int choice = 0;
        double old_price = 0;
        this.View_steps(b);

        System.out.print("Which step to rewrite: ");
        choice = Integer.parseInt(this.shopUI.getScanner().nextLine());
        if (choice < 1 || choice > n_steps) {
            System.out.println("error: step not avaiable");
            this.shopUI.pause();
        } else {
            old_price = b.getTodo().get(choice-1).getValue2();
            System.out.print("Introduz a descrição do passo: ");
            String aux_s = shopUI.getScanner().nextLine();
            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();
            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();
            try {
                aux.set(choice - 1, new Triplet(aux_s, dtf.parse(aux_time), Double.parseDouble(aux_price)));
                old_price = old_price - Double.parseDouble(aux_price);
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                this.shopUI.pause();
            }
        }
        this.shopUI.getServices_facade().update_budget(b,b.getEstimatedPrice()-old_price,aux,b.getStatus());
    }

    public void Finish_budget(Budget b, String username) throws IOException, ClassNotFoundException {
        System.out.println("Confirmation: Press 1 to finish the budget");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if(choice == 1){
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_REPAIR);
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            shopUI.MainMenu();
        }
    }

    public void repairing_menu(Budget b, String username) throws IOException, ClassNotFoundException {
        clearview();
        Menu repair = new Menu("Repair",
                Arrays.asList(
                        "Back"
                        ,"Show steps"
                        ,"Rewrite Step Value"
                        ,"Can't be repaired"
                        ,"Put on Wait"
                        ,"Finish Repair"
                ));
        repair.setPreCondition(2,() -> (b.getTodo().size() > 0));

        repair.setHandler(0,() -> shopUI.MainMenu());
        repair.setHandler(1,() -> this.View_steps(b));
        repair.setHandler(2,() -> this.Rewrite_step_values(b,username));
        repair.setHandler(3,() -> this.Repair_impossible(b,username));
        repair.setHandler(4,() -> this.Put_in_wait(b,username));
        repair.setHandler(5,() -> this.Finish_repair(b,username));
        repair.run();
    }

    public void Rewrite_step_values(Budget b, String username){
        List<Triplet<String ,LocalTime,Double>> aux = b.getTodo();
        int n_steps = b.getTodo().size();
        int choice;
        this.View_steps(b);

        System.out.print("Which step to rewrite: ");
        choice = Integer.parseInt(this.shopUI.getScanner().nextLine());
        if (choice < 1 || choice > n_steps) {
            System.out.println("error: step not avaiable");
            this.shopUI.pause();
        } else {
            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();
            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();
            try {
                aux.set(choice - 1, new Triplet(b.getTodo().get(choice-1).getValue0(), dtf.parse(aux_time), Double.parseDouble(aux_price)));
                is_price_bigger_than_expected(b,username,aux);
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                this.shopUI.pause();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.shopUI.getServices_facade().update_budget(b,b.getEstimatedPrice(),aux,b.getStatus());
    }


    public void is_price_bigger_than_expected(Budget b, String username, List<Triplet<String, LocalTime, Double>> aux) throws IOException, ClassNotFoundException {

        double real_price = 0;
        for(Triplet<String,LocalTime,Double> a : aux) {
            real_price = real_price + a.getValue2();
        }

        if(real_price > 1.2*b.getEstimatedPrice()){
            System.out.println("Price surpassed 120% of original budget, need client's approval");
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_APPROVAL);
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            this.shopUI.pause();
            this.shopUI.MainMenu();
        }
    }

    /**
     * i need a comment
     * @param b
     * @param username
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Repair_impossible(Budget b, String username) throws IOException, ClassNotFoundException {
        System.out.println("Confirmation: Press 1 to confirm impossible reparation");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if(choice == 1){
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_PICKUP);
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            //TODO MAIL A CLIENTE !!!!!!!!!!!!!!!!!!!!!!!!!!
            shopUI.MainMenu();
        }
    }

    public void Put_in_wait(Budget b, String username) throws IOException, ClassNotFoundException {
        System.out.println("Confirmation: Press 1 to put repair in wait");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if(choice == 1){
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.ON_HOLD);
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            shopUI.MainMenu();
        }
    }

    public void Finish_repair(Budget b, String username) throws IOException, ClassNotFoundException {
        System.out.println("Confirmation: Press 1 to finish the budget");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if(choice == 1){
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_PICKUP);
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            shopUI.MainMenu();
        }
    }


}
