package UI;
import BusinessLayer.ServicesFacade;
import BusinessLayer.Workers.*;
import BusinessLayer.WorkersFacade;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ShopUI {

    /**
     * Instance Variables
     * workers_facade is a part of the business logic
     * <p>
     * getInput is a scanner to read user's options
     */
    private WorkersFacade workers_facade;
    private ServicesFacade services_facade;
    private Scanner getInput;
    private String username;
    private Hierarchy hierarchy_logged;
    private boolean logged_in;

    private ClientsUI clientsUI;
    private ServicesUI servicesUI;
    private WorkersUI workersUI;


    /**
     * Constructor
     * It prepares the worker's facade to be used
     */
    public ShopUI(WorkersFacade fc, ServicesFacade sc) {

        this.workers_facade = fc;                                       // workers facade
        this.services_facade = sc;                                      // services facade
        this.logged_in = false;                                         // logged in user
        //this.Load();                                                  // initial loader
        this.getInput = new Scanner(System.in);                         // getInput scanner
        this.hierarchy_logged = null;                                   // logged in user's Hierarchy

        this.clientsUI = new ClientsUI(this);                   // auxiliar ClientsUI
        this.servicesUI = new ServicesUI(this);                 // auxiliar ServicesUI
        this.workersUI = new WorkersUI(this);                   // auxiliar WorkersUI
    }


    WorkersFacade getWorkers_facade(){
        return this.workers_facade;
    }
    ServicesFacade getServices_facade(){
        return this.services_facade;
    }
    Hierarchy getHierarchy_logged(){
        return this.hierarchy_logged;
    }
    Scanner getScanner(){
        return this.getInput;
    }
    String getUsername(){
        return this.username;
    }

    /**
     * Auxiliar function to imitate a system pause()
     */
    void pause() {
        System.out.println("#> enter to proceed...");
        this.getInput.nextLine();
    }

    /**
     * Auxiliar function to imitate a system clear()
     */
    static void clearview() {
        System.out.println("\n\n\n\n\n".repeat(10));
    }

    /**
     * Main run
     */
    public void run() throws IOException, ClassNotFoundException {

        // para entrar no sistema é preciso fazer login
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

        // set of handlers
        loginMenu.setHandler(0, this::exit);
        loginMenu.setHandler(1, this::login);
        loginMenu.setHandler(2, this::Credits);

        loginMenu.run();
    }

    /**
     * System's exit function
     */
    private void exit(){
        System.exit(0);
    }

    /**
     * Program's credits menu
     */
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

    /**
     * Login
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * This function asks for the username and password via input
     * and verifies if the credentials are correct, then updating the username and
     * Hierarchy of the person using the system at the moment
     */
    private void login() throws IOException, ClassNotFoundException {
        clearview();

        System.out.print("Username: ");
        String user = getInput.nextLine();

        System.out.print("Password: ");
        String password = getInput.nextLine();

        Worker w = this.workers_facade.hasWorker(user, password);
        // se o worker existir, efetua o login
        if (w != null) {

            this.logged_in = true;
            this.username = user;
            this.hierarchy_logged = w.getHierarchy();
            System.out.println("#> Welcome [" + user + " - " + this.hierarchy_logged + "]");
            pause();
            this.MainMenu();

        }
        // Se o worker não existir nos dados, termina a ação
        else {
            System.out.println("Wrong Credentials");
            pause();
            LoginMenu();
        }
    }

    /**
     * Main menu for user's interface, after login
     * There is a specific menu for each type of worker/hierarchy
     */
    void MainMenu() throws IOException, ClassNotFoundException {

        clearview();
        switch (this.hierarchy_logged) {
            case COUNTER -> CounterMenu();
            case TECHNICIAN -> TechnicianMenu();
            case MANAGER -> ManagerMenu();
        }
    }

    /**
     * Counter's System's Menu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void CounterMenu() throws IOException, ClassNotFoundException {

        Menu CounterMenu = new Menu("Counter - [@"+this.username+"]",
                Arrays.asList(
                          "Exit"                                                // 0 TODO
                        , "Consult Client"                                      // 1
                        , "Register Client"                                     // 2
                        , "Register Normal Service"                             // 3
                        , "Available Express Services"                          // 4
                        , "Consult Service Requests"                            // 5
                        , "Check Technician Availability"                       // 6
                        , "Payment"                                             // 7
                        , "Save Registers"                                      // 8
                        , "Load registers"                                      // 9
                ));

        // set of pre conditions
        CounterMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
        CounterMenu.setPreCondition(4, ()->this.services_facade.hasExpressServices());
        CounterMenu.setPreCondition(5, ()->this.services_facade.hasBudgetRequests());
        CounterMenu.setPreCondition(6, ()->this.workers_facade.hasWorkers(Hierarchy.TECHNICIAN));

        // set of handlers
        CounterMenu.setHandler(0, this::LoginMenu);
        CounterMenu.setHandler(1, clientsUI::consult_client);
        CounterMenu.setHandler(2, clientsUI::RegisterClient);
        CounterMenu.setHandler(3, servicesUI::RegisterNormalService);
        CounterMenu.setHandler(4, () -> servicesUI.ConsultExpressServicesMenu(hierarchy_logged));
        CounterMenu.setHandler(5, servicesUI::ConsultBudgetRequest);
        CounterMenu.setHandler(6, () -> this.workersUI.checkTechAvailability());
        CounterMenu.setHandler(7, servicesUI::PaymentMenu);
        CounterMenu.setHandler(8, this::Save);
        CounterMenu.setHandler(9, this::Load);

        CounterMenu.run();
    }

    /**
     * Technician's system's menu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void TechnicianMenu() throws IOException, ClassNotFoundException {

        Menu TechnicianMenu = new Menu("Technician - [@"+this.username+"]",
                 Arrays.asList(
                           "Exit"                                               // 0 TODO
                         , "Consult Service Requests"                           // 1
                         , "Available Express Services"                         // 2
                         , "Consult My Work"                                    // 3
                         , "Save"                                               // 4
                         , "Load"                                               // 5
                 ));

        // set of pre conditions
        TechnicianMenu.setPreCondition(1, ()->this.services_facade.hasBudgetRequests());
        TechnicianMenu.setPreCondition(2, ()->this.services_facade.hasExpressServices());

        // set of handlers
        TechnicianMenu.setHandler(0, this::LoginMenu);
        TechnicianMenu.setHandler(1, servicesUI::ConsultBudgetRequest);
        TechnicianMenu.setHandler(2, () -> servicesUI.ConsultExpressServicesMenu(hierarchy_logged));
        TechnicianMenu.setHandler(3, ()->workersUI.isTechWorking(this.username));
        TechnicianMenu.setHandler(4, this::Save);
        TechnicianMenu.setHandler(5, this::Load);

        TechnicianMenu.run();
    }

    /**
     * Manager's system's menu
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ManagerMenu() throws IOException, ClassNotFoundException {

        Menu ManagerMenu = new Menu("Manager - [@"+this.username+"]",
                Arrays.asList(
                        "Exit"                                                // 0 TODO
                        , "Consult Worker"                                      // 1
                        , "Register Worker"                                     // 2
                        , "Consult Client"                                      // 3
                        , "Consult Service Requests"                            // 4
                        , "Available Express Services"                          // 5
                        , "Statistics"                                          // 6
                        , "Save"                                                // 7
                        , "Load"                                                // 8
                ));

        // set of pre conditions
        ManagerMenu.setPreCondition(1, ()->this.workers_facade.hasWorkers());
        ManagerMenu.setPreCondition(3, ()->this.workers_facade.hasClients());
        ManagerMenu.setPreCondition(4, ()->this.services_facade.hasBudgetRequests());
        ManagerMenu.setPreCondition(5, ()->this.services_facade.hasExpressServices());
        ManagerMenu.setPreCondition(6, ()->this.workers_facade.hasWorkers());   // não é a resposta correta

        // set of handlers
        ManagerMenu.setHandler(0, this::LoginMenu);
        ManagerMenu.setHandler(1, workersUI::ConsultWorker);
        ManagerMenu.setHandler(2, workersUI::RegisterWorkerMenu);
        ManagerMenu.setHandler(3, clientsUI::consult_client);
        ManagerMenu.setHandler(4, servicesUI::ConsultBudgetRequest);
        ManagerMenu.setHandler(5, () -> servicesUI.ConsultExpressServicesMenu(hierarchy_logged));
        ManagerMenu.setHandler(6, () -> workersUI.StatisticsMenu());
        ManagerMenu.setHandler(7, this::Save);
        ManagerMenu.setHandler(8, this::Load);

        ManagerMenu.run();
    }


    /**
     * Saves the current system state to an object file
     */
    private void Save(){
        this.workers_facade.save();
        this.services_facade.save();
        clearview();
    }

    /**
     * Loads a system state from an object file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void Load() throws IOException, ClassNotFoundException {
        this.workers_facade = this.workers_facade.load();
        this.services_facade = this.services_facade.load();
        clearview();
    }
}