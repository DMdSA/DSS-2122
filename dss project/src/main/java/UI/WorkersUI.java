package UI;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Workers.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static UI.ShopUI.clearview;

public class WorkersUI {

    /**
     * Instance Variables
     */
    private ShopUI shopUI;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");

    /**
     * Constructor
     */
    public WorkersUI(ShopUI shopUI){
        this.shopUI = shopUI;
    }


    /**
     * Manager's Menu to register a new worker to the system
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
     *
     * This function asks for each worker's variable, using that input to create
     * a new object
     */
    private void RegisterWorker(Hierarchy h) {

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

        switch (h) {
            case COUNTER -> flag = this.shopUI.getWorkers_facade().registerWorker(new Counter(username, password, name, nif, phone));
            case TECHNICIAN -> flag = this.shopUI.getWorkers_facade().registerWorker(new Technician(username, password, name, nif, phone));
            case MANAGER -> flag = this.shopUI.getWorkers_facade().registerWorker(new Manager(username, password, name, nif, phone));
            default -> {
            }
        }
        // Se o registo não tiver ocorrido como era suposto, avisa o utilizador
        if(!flag) {
            System.out.println("\nerror: could not add the new worker!");
            shopUI.pause();
            clearview();
            return;
        }
        // Se o registo foi feito com sucesso, avisa o utilizador
        System.out.println("\nSuccessfully added worker [" + name + "]!");
        shopUI.pause();
        clearview();
    }


    /**
     * Manager's menu to consult every worker on the system
     *
     * It can search a list of all workers, by their hierarchy, or try to search for
     * a specific one, by their username
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
     * Manager's Menu to update a worker on the system
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

    /**
     * Auxiliar function to update a Worker's name
     * @param h
     * @param worker
     *
     * It asks for a name via input and uses it to update the worker's variable
     */
    private void UpdateWorkerName(Hierarchy h, Worker worker){

        System.out.print("#> new Name: ");
        String name = this.shopUI.getScanner().nextLine();

        // updates their name
        boolean flag = this.shopUI.getWorkers_facade().updateWorkerName(h, worker, name);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }

        shopUI.pause(); clearview();
    }

    /**
     * Updates a worker's password
     * @param h
     * @param worker
     *
     * It asks for input and uses it to update the worker's variable
     */
    private void UpdateWorkerPass(Hierarchy h, Worker worker){

        System.out.print("#> new Password: ");
        String pass = this.shopUI.getScanner().nextLine();

        // atualiza a password
        boolean flag = this.shopUI.getWorkers_facade().updateWorkerPass(h, worker, pass);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }
        shopUI.pause(); clearview();
    }

    /**
     * Update's a worker's NIF
     * @param h
     * @param worker
     *
     * Asks for input and uses it to update the variable
     */
    private void UpdateWorkerNif(Hierarchy h, Worker worker){

        System.out.print("#> new NIF: ");
        String nif = this.shopUI.getScanner().nextLine();

        boolean flag = this.shopUI.getWorkers_facade().updateWorkerNif(h, worker, nif);

        if (flag) {
            System.out.println("#> Updated worker information successfully!");
        }
        else {
            System.out.println("#> error: could not update worker " + worker.getUser());
        }
        shopUI.pause(); clearview();
    }

    /**
     * Updates a worker's phone number
     * @param h
     * @param worker
     *
     * Asks for input, so the system can use it to update the wanted variable
     */
    private void UpdateWorkerPhone(Hierarchy h, Worker worker){

        System.out.print("#> new Phone: ");
        String phone = this.shopUI.getScanner().nextLine();

        // atualiza o phone number
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
     * Prints a requested user by it's hierarchy (asking for his user by input)
     * @param h
     */
    private void PrintWorker(Hierarchy h) throws IOException, ClassNotFoundException {

        clearview();
        String user = null;
        System.out.print("Worker username: ");
        user = this.shopUI.getScanner().nextLine();

        // If the worker exists, it is printed
        if(this.shopUI.getWorkers_facade().hasWorker(h, user)){

            // get do user, pela sua hierarquia e username
            Worker w = this.shopUI.getWorkers_facade().getWorker(h, user);
            System.out.println("\nGot worker: " + w.toString() +"\n");

            // se o worker for do tipo CounterWorker, aproveita para imprimir as suas estatísticas
            if(w instanceof Counter){

                Counter c = (Counter) w;
                // get das estatísticas
                Pair<Integer,Integer> pair = this.shopUI.getWorkers_facade().getCounterStatistics(c);
                System.out.println("[Receptions]: " + pair.getValue0() + "  [Deliveries]: " + pair.getValue1() + "\n");
            }

            this.shopUI.pause();
            MenuUpdateWorker(h, w);
            clearview();
        }
        // Se o username não estiver registado, a operação é abortada
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

    /**
     * Auxiliar Menu to Tech choose his new task
     * @param username
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void isTechWorking(String username) throws IOException, ClassNotFoundException {
        clearview();

        //List of budgets the user is doing budget or repairing
        List<Budget> budgets_pending = this.shopUI.getServices_facade().Repairing_budgets(username);
        List<Budget> budgets_onhold = this.shopUI.getServices_facade().On_Hold_Budgets(username);

        //Locks a new option while he either has a reparation or budget pending
        //Has the option to create a new work or continue an on_hold reparation
        if(budgets_pending.size() == 0){
            Menu choose_work = new Menu("Choose Work",
                    Arrays.asList(
                            "Back"
                            , "Work on Budget"
                            , "Work on Repair"
                            , "Continue on hold reparation"
                    ));
            // Set of pre conditions
            choose_work.setPreCondition(1,() -> this.shopUI.getServices_facade().hasBudgetRequestByStatus(BudgetStatus.WITHOUT_BUDGET));
            choose_work.setPreCondition(2,() -> this.shopUI.getServices_facade().hasBudgetRequestByStatus(BudgetStatus.WAITING_REPAIR));
            choose_work.setPreCondition(3,() -> (budgets_onhold.size() > 0));

            // set of handlers
            choose_work.setHandler(0, () -> this.shopUI.MainMenu());
            choose_work.setHandler(1, () -> this.Work_on_budgets(BudgetStatus.WITHOUT_BUDGET));
            choose_work.setHandler(2, () -> this.Work_on_budgets(BudgetStatus.WAITING_REPAIR));
            choose_work.setHandler(3, () -> this.Work_on_hold(budgets_onhold.get(0)));
            choose_work.run();

        }
        //If the tech is already working on something, it shows that work's menu
        else{
            // todo comment
            if(budgets_pending.get(0).getStatus() == BudgetStatus.DOING_BUDGET) {
                doing_budget_menu(budgets_pending.get(0), username);
            }
            // todo comment
            else if(budgets_pending.get(0).getStatus() == BudgetStatus.REPAIRING){
                    Repairing_menu(budgets_pending.get(0), username);
            }
         }
    }

    /**
     * Auxiliar Function to put a Tech working on a Budget or Reparation
     * @param bs Budget Status to know if the tech will do a budget or on a reparation
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Work_on_budgets(BudgetStatus bs) throws IOException, ClassNotFoundException {

        // atualiza a disponibilidade do técnico em questão, para false
        this.shopUI.getWorkers_facade().update_worker_availability(shopUI.getUsername(),false);
        //
        Budget b = this.shopUI.getServices_facade().putTechWorking(bs,shopUI.getUsername());

        System.out.println("Now doing a new task");
        System.out.println();
        System.out.println(b.toString());
        this.shopUI.pause();
        shopUI.MainMenu();
    }

    /**
     * Auxiliar function to put a certain Tech continuing a on_hold reparation
     * @param b Budget the Tech will work on
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Work_on_hold(Budget b) throws IOException, ClassNotFoundException {

        this.shopUI.getServices_facade().continue_reparation(b);

        System.out.println("Now doing a new task");
        System.out.println();
        System.out.println(b.toString());
        this.shopUI.pause();
        shopUI.MainMenu();
    }

    /**
     * Auxiliar Menu with budget work options
     * @param b Budget the Tech's working on
     * @param username Tech's username
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void doing_budget_menu(Budget b, String username) throws IOException, ClassNotFoundException {
        clearview();

        Menu choose_work_budget = new Menu("Budget Work",
                Arrays.asList(
                        "Back"
                        , "View Steps"
                        , "Add Step"
                        , "Rewrite Step"
                        , "Finish Budget"
                ));
        // set of pre conditions
        choose_work_budget.setPreCondition(3,() -> (b.getTodo().size() > 0)); //Only lets rewrite steps if there's at least 1 step

        // set of handlers
        choose_work_budget.setHandler(0,() -> this.shopUI.MainMenu());
        choose_work_budget.setHandler(1,() -> this.View_steps(b));
        choose_work_budget.setHandler(2,() -> this.Add_steps(b));
        choose_work_budget.setHandler(3,() -> this.Rewrite_step(b));
        choose_work_budget.setHandler(4,() -> this.Finish_budget(b,username));

        choose_work_budget.run();
    }

    /**
     * Auxiliar Function to view the steps of a budget
     * @param b Budget the Tech's working on
     */
    public void View_steps(Budget b){

        // se a lista de afazeres do budget estiver vazia, não imprime nada
        if(b.getTodo().size() == 0){

            System.out.println("There isn't any steps yet");
        }
        // Se houver conteúdo na lista,
        else{

            int counter = 1;
            clearview();
            for(Triplet<String, String,Double> e : b.getTodo()){
                System.out.println("------Step no." + counter + "------");
                System.out.println("Description: " + e.getValue0());
                System.out.println("Time: " + e.getValue1());
                System.out.println("Price: " + e.getValue2()+"\n");
                counter++;
            }
        }
        this.shopUI.pause();
        clearview();
    }

    /**
     * Auxiliar Function to add a new Step
     * @param b Budget Tech is working on
     */
    public void Add_steps(Budget b){

        boolean flag = true;
        double price = b.getEstimatedPrice();
        List<Triplet<String, String, Double>> todo_steps_list = b.getTodo();

        // enquanto o user quiser adicionar passos,
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
                todo_steps_list.add(new Triplet<>(aux_s, dtf.parse(aux_time).toString(), Double.parseDouble(aux_price)));

            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                this.shopUI.pause();
                break;
            }

            // opção para continuar a adicionar passos, ou terminar tarefa
            System.out.print("Introduza 1 para adicionar mais um passo ou 0 para terminar: ");
            int aux_choice = -1;
            try {
                aux_choice = Integer.parseInt(shopUI.getScanner().nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("#> not an option");
            }
            if (aux_choice != 1) {
                flag = false;
            }
        }

        // atualiza a lista de afazeres de um budget
        this.shopUI.getServices_facade().update_budget(b,price,todo_steps_list,b.getStatus());
    }

    /**
     * Auxiliar function to rewrite a given step, including the description, updating the estimated price
     * @param b Budget Tech is working on
     */
    public void Rewrite_step(Budget b) {

        // lista de afazeres do budget em questão
        List<Triplet<String ,String,Double>> aux = b.getTodo();

        int n_steps = b.getTodo().size();
        int choice = 0;
        double old_price = 0;
        this.View_steps(b);

        System.out.print("Which step to rewrite: ");
        try {
            choice = Integer.parseInt(this.shopUI.getScanner().nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("#> not an option");
        }
        // Se o input não estiver contido nos limites estabelecidos, aborta a ação
        if (choice < 1 || choice > n_steps) {

            System.out.println("error: step not avaiable");
            this.shopUI.pause();
        }
        else {
            //
            old_price = b.getTodo().get(choice-1).getValue2();

            System.out.print("Introduz a descrição do passo: ");
            String aux_s = shopUI.getScanner().nextLine();

            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();

            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();

            try {
                //
                aux.set(choice - 1, new Triplet<>(aux_s, dtf.parse(aux_time).toString(), Double.parseDouble(aux_price)));
                old_price = old_price - Double.parseDouble(aux_price);
            }
            catch (DateTimeParseException dateTimeParseException) {

                System.out.println("Data invalida");
                this.shopUI.pause();
            }
        }
        this.shopUI.getServices_facade().update_budget(b,b.getEstimatedPrice()-old_price,aux,b.getStatus());
    }

    /**
     * Auxiliar function to confirm the conclusion of a budget
     * @param b Budget Tech is working on
     * @param username Tech's username
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Finish_budget(Budget b, String username) throws IOException, ClassNotFoundException {

        System.out.println("Confirmation: Press 1 to finish the budget");
        int choice = 0;
        try {
            choice = Integer.parseInt(shopUI.getScanner().nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("#> not an option");
        }
        // o inteiro [1] é utilizado para confirmar o término de uma operação
        if(choice == 1){

            // Se o orçamento foi terminado, o estado do mesmo é atualizado para WAITING_REPAIR
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_REPAIR);
            // o técnico de reparações volta a estar disponível
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            shopUI.MainMenu();
        }
    }

    /**
     * Menu with the Repair options
     * @param b Budget referring to the equipment which the Tech is repairing
     * @param username Tech's username
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Repairing_menu(Budget b, String username) throws IOException, ClassNotFoundException {
        clearview();

        Menu repair = new Menu("Repair Work",
                Arrays.asList(
                        "Back"
                        ,"Show steps"
                        ,"Rewrite Step Value"
                        ,"Can't be repaired"
                        ,"Put on Wait"
                        ,"Finish Repair"
                ));

        // set of pre conditions
        repair.setPreCondition(2,() -> (b.getTodo().size() > 0)); //Only lets rewrite if there is at least 1 step

        // set of handlers
        repair.setHandler(0,() -> shopUI.MainMenu());
        repair.setHandler(1,() -> this.View_steps(b));
        repair.setHandler(2,() -> this.Rewrite_step_values(b,username));
        repair.setHandler(3,() -> this.Repair_action(b,username, BudgetStatus.WAITING_PICKUP));
        repair.setHandler(4,() -> this.Repair_action(b,username,BudgetStatus.ON_HOLD));
        repair.setHandler(5,() -> this.Repair_action(b,username,BudgetStatus.WAITING_PICKUP));

        repair.run();
    }

    /**
     * Auxiliar function to rewrite the values of a given step.
     * @param b Budget refering to the equipment Tech is repairing
     * @param username Tech's username
     */
    public void Rewrite_step_values(Budget b, String username){

        // lista de passos de um budget
        List<Triplet<String ,String,Double>> aux = b.getTodo();
        int n_steps = b.getTodo().size();
        int choice = 0;
        this.View_steps(b);

        System.out.print("Which step to rewrite: ");
        try {
            choice = Integer.parseInt(this.shopUI.getScanner().nextLine());
        }catch (NumberFormatException e) {
            System.out.println("#> not an option");
        }
        // se o limite do input não for respeitado, a ação é terminada
        if (choice < 1 || choice > n_steps) {

            System.out.println("error: step not avaiable");
            this.shopUI.pause();
        }
        else {

            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();

            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();

            try {
                // ?
                aux.set(choice - 1, new Triplet<>(b.getTodo().get(choice-1).getValue0(), dtf.parse(aux_time).toString(), Double.parseDouble(aux_price)));
                Is_price_bigger_than_expected(b,username,aux);

            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                this.shopUI.pause();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // atualiza a lista de passos do budget
        this.shopUI.getServices_facade().update_budget(b,b.getEstimatedPrice(),aux,b.getStatus());
    }

    /**
     * Verifies if after each Rewrite the real_price is <120% of the original budget.
     * If it is, it pauses the repair until the client answers
     *
     * @param b Budget refering to the equipment Tech is repairing
     * @param username Tech's username
     * @param aux The list of steps updated
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Is_price_bigger_than_expected(Budget b, String username, List<Triplet<String, String, Double>> aux) throws IOException, ClassNotFoundException {

        double real_price = 0;
        // ?
        for(Triplet<String,String,Double> a : aux) {
            real_price = real_price + a.getValue2();
        }

        // Se a condição for verificada, é porque o preço ultrapassou os
        // limites pré-estabelecidos
        if(real_price > (1.2 * b.getEstimatedPrice())){

            System.out.println("Price surpassed 120% of original budget, need client's approval");
            // atualiza o estado do budget para WAITING_APPROVAL
            this.shopUI.getServices_facade().updateBudgetStatus(b,BudgetStatus.WAITING_APPROVAL);
            // o técnico volta a ficar disponível para novos trabalhos
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            this.shopUI.pause();
            this.shopUI.MainMenu();
        }
    }

    /**
     * ??
     * @param b Budget refering to the equipment Tech is repairing
     * @param username Tech's username
     * @param bs new BudgetStatus
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void Repair_action(Budget b, String username, BudgetStatus bs) throws IOException, ClassNotFoundException {

        // confirmar se o utilizador pretende concluir a sua ação
        System.out.println("Confirmation: Press 1 to confirm action");
        int choice = -1;
        try {
            choice = Integer.parseInt(shopUI.getScanner().nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("#> invalid option");
        }
        // se a condição for verificada
        if(choice == 1){
            // o estado do budget é atualizado
            this.shopUI.getServices_facade().updateBudgetStatus(b,bs);
            // o técnico volta a ficar disponível
            this.shopUI.getWorkers_facade().update_worker_availability(username,true);
            shopUI.MainMenu();
        }
    }

    /**
     * Manager's workers statistics menu
     *
     * As a manager, one can search for its workers' statistics
     */
    void StatisticsMenu() throws IOException, ClassNotFoundException {

        clearview();
        Menu statistics = new Menu("Statistics",
                Arrays.asList(
                        "Back"
                        , "Counters"
                        , "Counter by user"
                        , "Technicians"
                        , "Technician by user"
                ));

        // set of handlers
        statistics.setHandler(0, shopUI::MainMenu);
        statistics.setHandler(1, () -> AllWorkersStatistics(Hierarchy.COUNTER));
        statistics.setHandler(2, () -> WorkerStatistics(Hierarchy.COUNTER));
        statistics.setHandler(3, () -> AllWorkersStatistics(Hierarchy.TECHNICIAN));
        statistics.setHandler(4, () -> WorkerStatistics(Hierarchy.TECHNICIAN));

        statistics.run();
    }

    /**
     * Prints a specific worker's statistics, by their hierarchy
     * This function asks for the worker's username, via input
     *
     * @param h
     */
    private void WorkerStatistics(Hierarchy h){
        clearview();

        String user = null;
        System.out.print("Worker username: ");
        user = this.shopUI.getScanner().nextLine();

        // If the worker exists, it is printed
        if(this.shopUI.getWorkers_facade().hasWorker(h, user)){

            Worker w = this.shopUI.getWorkers_facade().getWorker(h, user);
            System.out.println("\n#> Got worker: " + w.toString() +"\n");

            // Se o trabalhador for do tipo COUNTER,
            if(w instanceof Counter){

                System.out.println("#> counter statistics");
                Counter c = (Counter) w;
                // Pair<Integer,Integer> -> Pair<Nrececoes, Nentregas>, presente nas variáveis de cada counter worker
                Pair<Integer,Integer> pair = this.shopUI.getWorkers_facade().getCounterStatistics(c);
                System.out.println("#> [Receptions]: " + pair.getValue0() + "  [Deliveries]: " + pair.getValue1() + "\n");
            }

            // Se o trabalhador for do tipo TECHNICIAN
            if(w instanceof Technician){

                System.out.println("#> tech statistics");

                // budgets that he worked on (budgets and/or repairs)
                List<Budget> tech_budgets = this.shopUI.getServices_facade().getTechBudgets(w);

                int size = tech_budgets.size();
                int index = 0;

                List<Pair<Budget, List<Triplet<String,String,Double>>>> tech_stats = new ArrayList<>();

                // para cada budget no qual o technician trabalhou,
                for(Budget b : tech_budgets) {

                    // recebe a lista de passos
                    List<Triplet<String, String, Double>> todo = tech_budgets.get(index).getTodo();
                    tech_stats.add(new Pair<>(tech_budgets.get(index), todo));
                    index++;
                }

                // Obtendo uma lista de objetos, o BOOk pode imprimi-los
                Book.Show_book(Arrays.asList(tech_stats.toArray()));
            }
            this.shopUI.pause();
            clearview();
        }
        // Se o worker procurado não existir, a ação é terminada
        else{
            System.out.println("error: That worker is not registered");
            shopUI.pause();
            clearview();
        }
    }

    /**
     * Calculates and shows the statistics of every worker on the system, by their Hierarchy
     * @param h workers' Hierarchy
     */
    private void AllWorkersStatistics(Hierarchy h){

        // Se forem trabalhadores do tipo COUNTER
        if(h.equals(Hierarchy.COUNTER)){

            // Lista com o username de cada um, acompanhado do n.º de receções e do n.º de entregas
            List<Triplet<String, Integer, Integer>> counter_stats = this.shopUI.getWorkers_facade().getCountersStats();
            // Obtida uma lista de objetos, o BOOK pode imprimi-la
            Book.Show_book(Arrays.asList(counter_stats.toArray()));
        }

        // Se forem trabalhadores do tipo TECHNICIAN
        else if(h.equals(Hierarchy.TECHNICIAN)){

            // Lista com os usernames de todos os técnicos registados
            List<String> techs = this.shopUI.getWorkers_facade().getListTechs();
            // Lista com os usernames associados ao n.º de budgets e ao n.º de repairs
            List<Triplet<String,Integer,Integer>> tech_stats = this.shopUI.getServices_facade().getTechStats(techs);
            // Obtida uma lista de objetos, o BOOK pode imprimi-la
            Book.Show_book(Arrays.asList(tech_stats.toArray()));
        }
    }
}