package UI;
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
    private boolean logged_in;
    private WorkersFacade workers_facade;
    private Scanner getInput;
    private String username;
    private Hierarchy hierarchy_logged;

    private ClientsUI clientsUI;
    private ServicesUI servicesUI;
    private WorkersUI workersUI;


    /**
     * Constructor
     * It prepares the worker's facade to be used
     */
    public ShopUI(WorkersFacade fc) throws IOException, ClassNotFoundException {
        this.logged_in = false;
        this.workers_facade = fc;
        //this.Load();
        this.getInput = new Scanner(System.in);
        this.hierarchy_logged = null;

        this.clientsUI = new ClientsUI(this);
        this.servicesUI = new ServicesUI(this);
        this.workersUI = new WorkersUI(this);
    }

    WorkersFacade getWorkers_facade(){
        return this.workers_facade;
    }

    Scanner getScanner(){
        return this.getInput;
    }

    String getUsername(){
        return this.username;
    }

    void pause() {
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
    void MainMenu() throws IOException, ClassNotFoundException {

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
                        , "Available Express Services"                          // 4
                        , "Consult Service Requests"                            // 5
                        , "Check Technician Availability"                       // 6 TODO
                        , "Save Registers"                                      // 7
                        , "Load registers"                                      // 8
                ));

         CounterMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
         CounterMenu.setPreCondition(4, ()->this.workers_facade.hasExpressServices());
         CounterMenu.setPreCondition(5, ()->this.workers_facade.hasBudgetRequests());
         CounterMenu.setPreCondition(6, ()->this.workers_facade.hasWorkers(Hierarchy.TECHNICIAN));

         //CounterMenu.setHandler(0, this:: ?);
         CounterMenu.setHandler(1, clientsUI::consult_client);
         CounterMenu.setHandler(2, clientsUI::RegisterClient);
         CounterMenu.setHandler(3, servicesUI::RegisterNormalService);
         CounterMenu.setHandler(4, servicesUI::ConsultExpressServicesMenu);
         CounterMenu.setHandler(5, servicesUI::ConsultBudgetRequest);
         //CounterMenu.setHandler(6, this:: ?);
        CounterMenu.setHandler(7, this::Save);
        CounterMenu.setHandler(8, this::Load);
        CounterMenu.run();
    }


    private void TechnicianMenu() throws IOException, ClassNotFoundException {

        Menu TechnicianMenu = new Menu("Technician - [@"+this.username+"]",
                 Arrays.asList(
                           "Exit"                                               // 0 TODO
                         , "Consult Service Requests"                           // 1
                         , "Available Express Services"                         // 2
                         , "Save"                                               // 3
                         , "Load"                                               // 4
                 ));

        TechnicianMenu.setPreCondition(1, ()->this.workers_facade.hasBudgetRequests());
        TechnicianMenu.setPreCondition(2, ()->this.workers_facade.hasExpressServices());

        //TechnicianMenu.setHandler(0, this::?);
        TechnicianMenu.setHandler(1, servicesUI::ConsultBudgetRequest);
        TechnicianMenu.setHandler(2, servicesUI::ConsultExpressServicesMenu);
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
                        , "Consult Service Requests"                            // 3
                        , "Available Express Services"                          // 4
                        , "Consult Worker"                                      // 5
                        , "Save"                                                // 6
                        , "Load"                                                // 7
                ));
        ManagerMenu.setPreCondition(2, ()->this.workers_facade.hasClients());
        ManagerMenu.setPreCondition(3, ()->this.workers_facade.hasBudgetRequests());
        ManagerMenu.setPreCondition(4, ()->this.workers_facade.hasExpressServices());
        ManagerMenu.setPreCondition(5, ()->this.workers_facade.hasWorkers());

        //ManagerMenu.setHandler(0, this::?);
        ManagerMenu.setHandler(1, workersUI::RegisterWorkerMenu);
        ManagerMenu.setHandler(2, clientsUI::consult_client);
        ManagerMenu.setHandler(3, servicesUI:: ConsultBudgetRequest);
        ManagerMenu.setHandler(4, servicesUI::ConsultExpressServicesMenu);
        ManagerMenu.setHandler(5, workersUI::ConsultWorker);
        ManagerMenu.setHandler(6, this::Save);
        ManagerMenu.setHandler(7, this::Load);
        ManagerMenu.run();
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
}