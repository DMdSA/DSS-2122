package UI;
import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import BusinessLayer.WorkersFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ShopUI {

    /**
     * Instance Variables
     * workers_facade is a part of the business logic
     *
     * getInput is a scanner to read user's options
     */
    private boolean logged_in;
    private WorkersFacade workers_facade;
    private Scanner getInput;

    private String username;
    private Hierarchy hierarchy_logged;


    /**
     * Constructor
     * It prepares the worker's facade to be used
     */
    public ShopUI(){
        this.logged_in = false;
        this.workers_facade = new WorkersFacade();
        this.getInput = new Scanner(System.in);
    }

    //--------------------------------
    // DEBUG ONLY
    public ShopUI(WorkersFacade wf){
        this.logged_in = false;
        this.workers_facade = wf;
        this.getInput = new Scanner(System.in);
    }
    // ------------------------------

    /**
     * Confirms if someone using the system has already made the login
     * It returns an instance variable being used for that purpose
     * @return
     */
    public boolean isLoggedIn(){
        return this.logged_in;
    }


    /**
     * Main run
     */
    public void run() throws IOException, ClassNotFoundException {

        this.loginMenu();

        //this.mainMenu();
    }

    private void introduction(){
        System.out.println("\n\n\n\n\n".repeat(10));
        Menu introduction = new Menu("Miei Repair Center",
                Arrays.asList(
                        "Program developed by group xx"
                        , "Version 1.0"
                        , "2021/2022"
                        , "UMINHO"));
        introduction.show();
        pressAnyKeyToContinue();
    }


    private void loginMenu() throws IOException, ClassNotFoundException {
        System.out.println("\n\n\n\n\n".repeat(10));
        Menu loginMenu = new Menu("Login - Welcome",
                Arrays.asList(
                        "Exit"
                        , "Login"
                        , "Credits"));
        loginMenu.setHandler(0,this::exit);
        loginMenu.setHandler(1,this::login);
        loginMenu.setHandler(2,this::introduction);
        loginMenu.run();
    }

    private void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception ignored)
        {}
    }

    private void login() throws IOException, ClassNotFoundException {
        System.out.println("\n\n\n\n\n".repeat(10));
        System.out.println("Username:"); //Debug
        String user = getInput.nextLine();
        System.out.println("Password");
        String password = getInput.nextLine();
        Worker aux = workers_facade.hasWorker(user);
        if(aux != null){
            if(aux.getPass().equals(password)){
                this.logged_in = true;
                this.username = aux.getUser();
                this.hierarchy_logged = aux.getHierarchy();
                System.out.println("Welcome " + aux.getHierarchy());
                this.mainMenu();

            }
            else{
                System.out.println("Wrong Credentials");
                pressAnyKeyToContinue();
                loginMenu();
            }
        }
        else{
            System.out.println("Wrong Credentials");
            pressAnyKeyToContinue();
            loginMenu();
        }
    }

    /**
     * Main menu for user's interface, after login
     * It has a specific menu for each type of worker/hierarchy
     */
    private void mainMenu() throws IOException, ClassNotFoundException {
        System.out.println();

        /**
         * Neste ponto, era engraçado ter um controlador por parte da operação "login".
         * Se o login fosse efetuado por um COUNTER_WORKER, apareceria o menu relacionado a ele.
         * Se o login fosse efetuado por um TECHNICIAN, ""  ""      ""          ""
         * Se ""            ""          ""  MANAGER         ""              ""
         */

        /**
         * COUNTER WORKER - MENU
         */
         Menu CounterMenu = new Menu("Counter - [@user]",
                Arrays.asList(
                          "Exit"                                                // 0 TODO
                        , "Consult Client"                                      // 1
                        , "Register Client"                                     // 2
                        , "Register Normal Service"                             // 3 TODO
                        , "Register Express Service"                            // 4 TODO
                        , "Consult Express Services"                            // 5 TODO
                        , "Consult Budget Request"                              // 6 TODO
                        , "Check Technician Availability"                       // 7 TODO
                        , "Save Registers"                                      // 8
                        , "Load registers"                                      // 9
                ));


         CounterMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
         CounterMenu.setPreCondition(5, ()->this.workers_facade.hasExpressServices());
         CounterMenu.setPreCondition(6, ()->this.workers_facade.hasBudgetRequests());
         CounterMenu.setPreCondition(7, ()->this.workers_facade.hasWorkers(Hierarchy.TECHNICIAN));

        CounterMenu.setHandler(0, this::exit);
         CounterMenu.setHandler(1, this::consult_client);
         CounterMenu.setHandler(2, this:: RegisterClient);
         CounterMenu.setHandler(3, this::RegisterNormalService);
         //CounterMenu.setHandler(4, this:: ?);
         //CounterMenu.setHandler(5, this:: ?);
         //CounterMenu.setHandler(6, this:: ?); asd
         //CounterMenu.setHandler(7, this:: ?);
        CounterMenu.setHandler(8, this::Save);
        CounterMenu.setHandler(9, this::Load);
        if(hierarchy_logged == Hierarchy.COUNTER) {
            CounterMenu.run();
            System.out.println();
        }

        /**
         * TECHNICIAN - MENU
         */
        Menu TechnicianMenu = new Menu("Technician - [@user]",
                 Arrays.asList(
                           "Exit"                                               // 0 TODO
                         , "Consult Budget Requests"                            // 1 TODO
                         , "Consult Express Requests"                           // 2 TODO
                         , "Save"                                               // 3
                         , "Load"                                               // 4
                 ));

        TechnicianMenu.setPreCondition(1, ()->this.workers_facade.hasBudgetRequests());
        TechnicianMenu.setPreCondition(2, ()->this.workers_facade.hasExpressServices());

        //TechnicianMenu.setHandler(1, ?);
        //TechnicianMenu.setHandler(2, ?);
        TechnicianMenu.setHandler(3, this::Save);
        TechnicianMenu.setHandler(4, this::Load);
        if(hierarchy_logged == Hierarchy.TECHNICIAN){
            TechnicianMenu.run();
            System.out.println();
        }


        /**
         * MANAGER - MENU
         */
        Menu ManagerMenu = new Menu("Manager - [@user]",
                Arrays.asList(
                          "Exit"                                                // 0 TODO
                        , "Register Worker"                                     // 1 TODO
                        , "Consult Client"                                      // 2
                        , "Consult Normal Service"                              // 3 TODO
                        , "Consult Express Service"                             // 4 TODO
                        , "Consult Worker"                                      // 5
                        , "Save"                                                // 6
                        , "Load"                                                // 7
                ));
        ManagerMenu.setPreCondition(2, ()->this.workers_facade.hasClients());
        ManagerMenu.setPreCondition(3, ()->this.workers_facade.hasBudgetRequests());
        ManagerMenu.setPreCondition(4, ()->this.workers_facade.hasExpressServices());
        ManagerMenu.setPreCondition(5, ()->this.workers_facade.hasWorkers());

        //ManagerMenu.setHandler(0,);
        //ManagerMenu.setHandler(1,);
        ManagerMenu.setHandler(2, this::consult_client);
        //ManagerMenu.setHandler(3,);
        //ManagerMenu.setHandler(4,);
        ManagerMenu.setHandler(5, this::ConsultWorker);
        ManagerMenu.setHandler(6, this::Save);
        ManagerMenu.setHandler(7, this::Load);
        if(hierarchy_logged == Hierarchy.MANAGER){
            ManagerMenu.run();
            System.out.println();
        }
    }


    private void exit(){
        exit();
    }

    /**
     * Consult the list of existing clients
     */
    private void consult_client() throws IOException, ClassNotFoundException {

        /**
         * Em vez de 1º mostrar todos e depois dar a opcao de "cada", podia meter a listagem de todos como uma das opcoes do menu... TODO
         */

        // Prints a book of clients
        System.out.println("\n\n\n\n\n".repeat(10));
        // shows the search client menu
        Menu ConsultClient = new Menu("Search Client",
                Arrays.asList(
                          "Back"
                        , "All Clients"
                        , "by Nif"
                        , "by Phone"));

        // depois de encontrado, podia haver um menu direto para "registar servico" ?

        ConsultClient.setHandler(0, this::mainMenu);
        ConsultClient.setHandler(1, () -> Book.show_clients(this.workers_facade.getClients()));
        ConsultClient.setHandler(2, this::ConsultClientByNif);
        ConsultClient.setHandler(3, this::ConsultClientByPhone);
        ConsultClient.run();
        System.out.println("\n\n\n\n\n".repeat(10));

    }

    /**
     * Searchs for a client by his ID/NIF
     */
    private void ConsultClientByNif(){

        // Leitura input
        String nif = null;
        nif = this.getInput.nextLine();

        // Procura
        Client c = this.workers_facade.getClientByNif(nif);
        if(c != null)
            System.out.println("Got : " + c);
        else
            System.out.println("error: Client does not exist");
    }

    /**
     * Searchs for a client by his phone number
     */
    private void ConsultClientByPhone(){

        // Leitura input
        String phone = null;
        phone = this.getInput.nextLine();

        // Procura
        Client c = this.workers_facade.getClientByPhone(phone);
        if(c!=null)
            System.out.println("Got : " + c);
        else
            System.out.println("error: Client does not exist");
    }

    /**
     * Registers a new client
     */
    private void RegisterClient(){

        System.out.print("#> NIF: ");
        String nif = this.getInput.nextLine();

        if(this.workers_facade.hasClient(nif)){
            System.out.println("error: Client already exists!");
            return;
        }

        System.out.print("#> Name: ");
        String name = this.getInput.nextLine();

        System.out.print("#> Email: ");
        String mail = this.getInput.nextLine();

        System.out.print("#> Phone: ");
        String phone = this.getInput.nextLine();

        try {
            this.workers_facade.registerClient(new Client(name, nif, mail, phone));
        } catch (NullPointerException npe){
            System.out.println("error: could not add the new client. nullpointerexception");
        }
    }

    /**
     * Registers a new normal service
     */
    private void RegisterNormalService(){

        System.out.print("#> Nif:");
        String nif = this.getInput.nextLine();

        boolean registered = this.workers_facade.hasClient(nif);
        if(!registered){
            System.out.println("error: That nif is not registered!");
            return;
        }

        String equipment_name = this.getInput.nextLine();

        this.workers_facade.registerBudgetRequest(new Service(nif, equipment_name, "counter1"));    //TODO CUIDADO COUNTERID
        // TODO
    }

    /**
     * Consults normal services
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ConsultBudgetRequest() throws IOException, ClassNotFoundException {
        System.out.println("\n\n\n\n\n".repeat(10));

        // shows the search client menu
        Menu ConsultBudget = new Menu("Search Budget",
                Arrays.asList(
                        "Back"
                        , "by Status"
                        , "by ClientID"));

        // depois de encontrado, podia haver um menu direto para "registar servico" ?

        ConsultBudget.setHandler(0, this::mainMenu);
        ConsultBudget.setHandler(1, this::ConsultBudgetByStatus);
        ConsultBudget.setHandler(2, this::ConsultBudgetByNif);
        ConsultBudget.run();
    }

    /**
     * Consults a budget by it's status
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ConsultBudgetByStatus() throws IOException, ClassNotFoundException {
        System.out.println("\n\n\n\n\n".repeat(10));
        Menu ConsultStatus = new Menu("States",
                Arrays.asList(
                        "Back",
                BudgetStatus.WITHOUT_BUDGET.toString(),
                BudgetStatus.WAITING_APPROVAL.toString(),
                BudgetStatus.DECLINED.toString(),
                BudgetStatus.WAITING_REPAIR.toString(),
                BudgetStatus.WAITING_PICKUP.toString(),
                BudgetStatus.DELIVERED.toString(),
                BudgetStatus.ARCHIVED.toString()));

        ConsultStatus.setHandler(0,this::ConsultBudgetRequest);
        ConsultStatus.run();
        ConsultStatus.setHandler(1,()->ConsultBudget(BudgetStatus.WITHOUT_BUDGET));
    }

    /**
     * Consult a budget by nif
     */
    private void ConsultBudgetByNif(){
        // Leitura input
        String nif = null;
        nif = this.getInput.nextLine();

        // Procura
        List<Budget> budgets = new ArrayList<>();
        budgets = this.workers_facade.getBudgetbyNif(nif);
        if(budgets.size() != 0) {
            for(int i = 0; i < budgets.size(); i++){
                System.out.println("Got : " + budgets.get(i));
            }
        }
        else
            System.out.println("error: No Budgets with the specified Nif");
    }

    /**
     * Consults a list of budgets by their status
     * @param budgetStatus
     */
   private void ConsultBudget(BudgetStatus budgetStatus) {
        List<Budget> budgets;
        budgets = new ArrayList<>(workers_facade.getBudgetRequestsbyStatus(budgetStatus).values()); //TODO CLONE!!!!!!!
        //TODO BOOK DMA yey
    }


    /**
     * Saves the current system state to an object file
     */
    private void Save(){
        this.workers_facade.save();
        System.out.println("\n\n\n\n\n".repeat(10));
    }

    /**
     * Loads a system state from an object file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void Load() throws IOException, ClassNotFoundException {
        this.workers_facade = this.workers_facade.load();
        System.out.println("\n\n\n\n\n".repeat(10));
    }


    /**
     * Menu to consult workers on the system
     */
    private void ConsultWorker(){

        Menu worker_type = new Menu("Search Workers",
                Arrays.asList(
                          "Back"
                        , "Counter Workers"
                        , "Counter Worker by user"
                        , "Technicians"
                        , "Technician by user"
                ));

        //worker_type.setHandler(0, this::ManagerMenu);
        worker_type.setHandler(1, () -> Book.Show_Workers(this.workers_facade.getWorkers(), Hierarchy.COUNTER));
        worker_type.setHandler(2, ()-> PrintWorker(Hierarchy.COUNTER));
        worker_type.setHandler(3, () -> Book.Show_Workers(this.workers_facade.getWorkers(), Hierarchy.TECHNICIAN));
        worker_type.setHandler(4, () -> PrintWorker(Hierarchy.TECHNICIAN));
    }


    /**
     * Prints a requested user by it's hierarchy, asking for his user
     * @param h
     */
    private void PrintWorker(Hierarchy h){

        String user = this.getInput.nextLine();

        // If the worker exists, it is printed
        if(this.workers_facade.hasWorker(h, user)){
            System.out.println("Got worker: " + this.workers_facade.getWorker(h, user));
        }
        else{
            System.out.println("error: That worker is not registered");
        }
    }

}
