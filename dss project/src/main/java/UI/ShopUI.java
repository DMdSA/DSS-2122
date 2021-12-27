package UI;
import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.*;
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
     * <p>
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
    public ShopUI() throws IOException, ClassNotFoundException {
        this.logged_in = false;
        this.workers_facade = new WorkersFacade();
        this.Load();
        this.getInput = new Scanner(System.in);
        this.hierarchy_logged = null;
    }


    private void pause() {
        System.out.println("#> enter to proceed...");
        this.getInput.nextLine();
    }

    static void clearview() {
        System.out.println("\n\n\n\n\n".repeat(10));
    }


    /**
     * Main run
     */
    public void run() throws IOException, ClassNotFoundException {

        this.LoginMenu();
    }

    /**
     * Login Menu
     */
    private void LoginMenu() throws IOException, ClassNotFoundException {

        clearview();
        Menu loginMenu = new Menu("Login - Welcome",
                Arrays.asList(
                        "Exit"
                        , "Login"
                        , "Credits"));
        //loginMenu.setHandler(0, this::exit);
        loginMenu.setHandler(1, this::login);
        loginMenu.setHandler(2, this::Credits);
        loginMenu.run();
    }

    private void Credits(){

        clearview();
        Menu introduction = new Menu("Miei Repair Center",
                Arrays.asList(
                        "Program developed by group xx"
                        , "Version 1.0"
                        , "2021/2022"
                        , "UMINHO"));
        introduction.show();
        pause();
        clearview();
    }


    private void login() throws IOException, ClassNotFoundException {

        clearview();

        System.out.print("Username: "); //Debug
        String user = getInput.nextLine();
        System.out.print("Password: ");
        String password = getInput.nextLine();

        Worker w = this.workers_facade.hasWorker(user, password);

        if (w != null) {

            this.logged_in = true;
            this.username = user;
            this.hierarchy_logged = w.getHierarchy();
            System.out.println("#> Welcome [" + user + " - " + this.hierarchy_logged + "]");
            pause();
            this.MainMenu();

        } else {
            System.out.println("Wrong Credentials");
            pause();
            LoginMenu();
        }
    }

    /**
     * Main menu for user's interface, after login
     * It has a specific menu for each type of worker/hierarchy
     */
    private void MainMenu() throws IOException, ClassNotFoundException {

        clearview();
        switch (this.hierarchy_logged) {
            case COUNTER -> CounterMenu();
            case TECHNICIAN -> TechnicianMenu();
            case MANAGER -> ManagerMenu();
        }
    }


    private void CounterMenu() throws IOException, ClassNotFoundException {

        Menu CounterMenu = new Menu("Counter - [@"+this.username+"]",
                Arrays.asList(
                          "Exit"                                                // 0 TODO
                        , "Consult Client"                                      // 1
                        , "Register Client"                                     // 2
                        , "Register Normal Service"                             // 3
                        , "Register Express Service"                            // 4 TODO
                        , "Consult Express Services"                            // 5 TODO
                        , "Consult Budget Request"                              // 6
                        , "Check Technician Availability"                       // 7 TODO
                        , "Save Registers"                                      // 8
                        , "Load registers"                                      // 9
                ));

         CounterMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
         CounterMenu.setPreCondition(5, ()->this.workers_facade.hasExpressServices());
         CounterMenu.setPreCondition(6, ()->this.workers_facade.hasBudgetRequests());
         CounterMenu.setPreCondition(7, ()->this.workers_facade.hasWorkers(Hierarchy.TECHNICIAN));

         //CounterMenu.setHandler(0, this:: ?);
         CounterMenu.setHandler(1, this::consult_client);
         CounterMenu.setHandler(2, this:: RegisterClient);
         CounterMenu.setHandler(3, this::RegisterNormalService);
         //CounterMenu.setHandler(4, this:: ?);
         //CounterMenu.setHandler(5, this:: ?);
         CounterMenu.setHandler(6, this:: ConsultBudgetRequest);
         //CounterMenu.setHandler(7, this:: ?);
        CounterMenu.setHandler(8, this::Save);
        CounterMenu.setHandler(9, this::Load);
        CounterMenu.run();
    }


    private void TechnicianMenu() throws IOException, ClassNotFoundException {

        Menu TechnicianMenu = new Menu("Technician - [@"+this.username+"]",
                 Arrays.asList(
                           "Exit"                                               // 0 TODO
                         , "Consult Budget Requests"                            // 1
                         , "Consult Express Requests"                           // 2 TODO
                         , "Save"                                               // 3
                         , "Load"                                               // 4
                 ));

        TechnicianMenu.setPreCondition(1, ()->this.workers_facade.hasBudgetRequests());
        TechnicianMenu.setPreCondition(2, ()->this.workers_facade.hasExpressServices());

        //TechnicianMenu.setHandler(0, this::?);
        TechnicianMenu.setHandler(1, this:: ConsultBudgetRequest);
        //TechnicianMenu.setHandler(2, this::?);
        TechnicianMenu.setHandler(3, this::Save);
        TechnicianMenu.setHandler(4, this::Load);
        TechnicianMenu.run();
    }

    private void ManagerMenu() throws IOException, ClassNotFoundException {

        Menu ManagerMenu = new Menu("Manager - [@"+this.username+"]",
                Arrays.asList(
                          "Exit"                                                // 0 TODO
                        , "Register Worker"                                     // 1
                        , "Consult Client"                                      // 2
                        , "Consult Normal Service"                              // 3
                        , "Consult Express Service"                             // 4 TODO
                        , "Consult Worker"                                      // 5
                        , "Save"                                                // 6
                        , "Load"                                                // 7
                ));
        ManagerMenu.setPreCondition(2, ()->this.workers_facade.hasClients());
        ManagerMenu.setPreCondition(3, ()->this.workers_facade.hasBudgetRequests());
        ManagerMenu.setPreCondition(4, ()->this.workers_facade.hasExpressServices());
        ManagerMenu.setPreCondition(5, ()->this.workers_facade.hasWorkers());

        //ManagerMenu.setHandler(0, this::?);
        ManagerMenu.setHandler(1, this::RegisterWorkerMenu);
        ManagerMenu.setHandler(2, this::consult_client);
        ManagerMenu.setHandler(3, this:: ConsultBudgetRequest);
        //ManagerMenu.setHandler(4,);
        ManagerMenu.setHandler(5, this::ConsultWorker);
        ManagerMenu.setHandler(6, this::Save);
        ManagerMenu.setHandler(7, this::Load);
        ManagerMenu.run();
    }


    /**
     * Consult the list of existing clients
     */
    private void consult_client() throws IOException, ClassNotFoundException {

        /**
         * Em vez de 1º mostrar todos e depois dar a opcao de "cada", podia meter a listagem de todos como uma das opcoes do menu... TODO
         */

        // Prints a book of clients
        clearview();
        // shows the search client menu
        Menu ConsultClient = new Menu("Search Client",
                Arrays.asList(
                          "Back"
                        , "All Clients"
                        , "by Nif"
                        , "by Phone"));

        // depois de encontrado, podia haver um menu direto para "registar servico" ?

        ConsultClient.setHandler(0, this::MainMenu);
        ConsultClient.setHandler(1, () -> Book.show_clients(this.workers_facade.getClients()));
        ConsultClient.setHandler(2, this::ConsultClientByNif);
        ConsultClient.setHandler(3, this::ConsultClientByPhone);
        ConsultClient.run();
        clearview();

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

        clearview();
        System.out.print("#> Nif:");
        String nif = this.getInput.nextLine();

        boolean registered = this.workers_facade.hasClient(nif);
        if(!registered){
            System.out.println("error: That nif is not registered!");
            return;
        }

        System.out.print("#> Equipment description: ");
        String equipment_name = this.getInput.nextLine();

        this.workers_facade.registerBudgetRequest(new Service(nif, equipment_name, "counter1"));    //TODO CUIDADO COUNTERID
        System.out.println("New register made for nif ["+nif+"]");
        pause();
        clearview();
        // TODO
    }

    /**
     * Shows an auxiliar menu with the purpose of showing budgets to the user
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ConsultBudgetRequest() throws IOException, ClassNotFoundException {
        clearview();

        // shows the search client menu
        Menu ConsultBudget = new Menu("Search Budget",
                Arrays.asList(
                          "Back"
                        , "by Status"
                        , "by ClientID"));


        ConsultBudget.setHandler(0, this::MainMenu);
        ConsultBudget.setHandler(1, this::ConsultBudgetByStatus);
        ConsultBudget.setHandler(2, this::ConsultBudgetByNif);
        ConsultBudget.run();
    }

    /**
     * Auxiliar menu to show budgets by their status
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ConsultBudgetByStatus() throws IOException, ClassNotFoundException {
        clearview();
        Menu ConsultStatus = new Menu("States",
                Arrays.asList(
                          "Back"
                        , BudgetStatus.WITHOUT_BUDGET.toString()
                        , BudgetStatus.WAITING_APPROVAL.toString()
                        , BudgetStatus.DECLINED.toString()
                        , BudgetStatus.WAITING_REPAIR.toString()
                        , BudgetStatus.WAITING_PICKUP.toString()
                        , BudgetStatus.DELIVERED.toString()
                        , BudgetStatus.ARCHIVED.toString()
                ));

        ConsultStatus.setHandler(0,this::ConsultBudgetRequest);
        ConsultStatus.setHandler(1,()->ConsultBudget(BudgetStatus.WITHOUT_BUDGET));
        ConsultStatus.setHandler(2,()->ConsultBudget(BudgetStatus.WAITING_APPROVAL));
        ConsultStatus.setHandler(3,()->ConsultBudget(BudgetStatus.DECLINED));
        ConsultStatus.setHandler(4,()->ConsultBudget(BudgetStatus.WAITING_REPAIR));
        ConsultStatus.setHandler(5,()->ConsultBudget(BudgetStatus.WAITING_PICKUP));
        ConsultStatus.setHandler(6,()->ConsultBudget(BudgetStatus.DELIVERED));
        ConsultStatus.setHandler(7,()->ConsultBudget(BudgetStatus.ARCHIVED));
        ConsultStatus.run();
    }

    /**
     * Consults all budgets requested by a client
     */
    private void ConsultBudgetByNif(){

        clearview();
        // Leitura input
        String nif = null;
        System.out.print("Client's Nif: ");
        nif = this.getInput.nextLine();

        boolean registered = this.workers_facade.hasClient(nif);
        if(!registered){
            System.out.println("error: That nif is not registered!");
            return;
        }

        // Procura
        List<Budget> budgets = this.workers_facade.getBudgetbyNif(nif);

        if(budgets.size() != 0) {
            for(int i = 0; i < budgets.size(); i++){
                System.out.println("Got : " + budgets.get(i));          //todo
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

       Book.Show_Budgets_By_Status(budgets);
    }


    /**
     * Saves the current system state to an object file
     */
    private void Save(){
        this.workers_facade.save();
        clearview();
    }

    /**
     * Loads a system state from an object file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void Load() throws IOException, ClassNotFoundException {
        this.workers_facade = this.workers_facade.load();
        clearview();
    }


    private void RegisterWorkerMenu() throws IOException, ClassNotFoundException {

        clearview();
        Menu register_worker = new Menu("Register Worker",
                Arrays.asList(
                          "Back"
                        , "Counter Worker"
                        , "Technician"
                        , "Manager"
                ));

        register_worker.setHandler(0, this::MainMenu);
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
        String username = this.getInput.nextLine();

        if(this.workers_facade.hasWorker(Hierarchy.COUNTER, username) || this.workers_facade.hasWorker(Hierarchy.TECHNICIAN, username)
                        || this.workers_facade.hasWorker(Hierarchy.MANAGER, username)){

            System.out.println("error: that username is already registered");
            return;
        }

        System.out.print("password: ");
        String password = this.getInput.nextLine();

        System.out.print("name: ");
        String name = this.getInput.nextLine();

        System.out.print("nif: ");
        String nif = this.getInput.nextLine();

        System.out.print("phone: ");
        String phone = this.getInput.nextLine();

        boolean flag = false;


        switch (h){
            case COUNTER :

                flag = this.workers_facade.registerWorker(new Counter(username, password, name, nif, phone));
                break;
            case TECHNICIAN :
                flag = this.workers_facade.registerWorker(new Technician(username, password, name, nif, phone));
                break;
            case MANAGER:
                flag = this.workers_facade.registerWorker(new Manager(username, password, name, nif, phone));
                break;
            default:
                break;
        }
        if(!flag) {
            System.out.println("\nerror: could not add the new worker!");
            pause();
            clearview();
            return;
        }
        System.out.println("\nSuccessfully added worker [" + name + "]!");
        pause();
        clearview();
    }




    /**
     * Menu to consult workers on the system
     */
    private void ConsultWorker() throws IOException, ClassNotFoundException {

        clearview();
        Menu worker_type = new Menu("Search Workers",
                Arrays.asList(
                          "Back"
                        , "Counter Workers"
                        , "Counter Worker by user"
                        , "Technicians"
                        , "Technician by user"
                        , "Managers"
                ));

        worker_type.setHandler(0, this::MainMenu);
        worker_type.setHandler(1, () -> Book.Show_Workers(this.workers_facade.getWorkers(), Hierarchy.COUNTER));
        worker_type.setHandler(2, () -> PrintWorker(Hierarchy.COUNTER));
        worker_type.setHandler(3, () -> Book.Show_Workers(this.workers_facade.getWorkers(), Hierarchy.TECHNICIAN));
        worker_type.setHandler(4, () -> PrintWorker(Hierarchy.TECHNICIAN));
        worker_type.setHandler(5, () -> Book.Show_Workers(this.workers_facade.getWorkers(), Hierarchy.MANAGER));

        worker_type.run();
    }


    /**
     * Prints a requested user by it's hierarchy, asking for his user
     * @param h
     */
    private void PrintWorker(Hierarchy h){

        clearview();
        String user = null;
        System.out.print("Worker username: ");
        user = this.getInput.nextLine();

        // If the worker exists, it is printed
        if(this.workers_facade.hasWorker(h, user)){
            System.out.println("\nGot worker: " + this.workers_facade.getWorker(h, user)+"\n");
            pause();
            clearview();
        }
        else{
            System.out.println("error: That worker is not registered");
            pause();
            clearview();
        }
    }

}
