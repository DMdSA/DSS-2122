package UI;

import BusinessLayer.Client.Client;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Manager;
import BusinessLayer.WorkersFacade;

import java.util.Arrays;
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
    public void run(){

        Menu introduction = new Menu("Miei Repair Center",
                Arrays.asList(
                          "Program developed by group xx"
                        , "Version 1.0"
                        , "2021/2022"
                        , "UMINHO"));
        introduction.show();

        // login ?? TODO

        this.mainMenu();
    }


    private void loginMenu(){

        Menu loginMenu = new Menu("Login - Welcome",
                Arrays.asList(
                          "Exit"
                        , "Login"
                        , "Credits"));
        loginMenu.show();
        System.out.println();
    }

    /**
     * Main menu for user's interface, after login
     * It has a specific menu for each type of worker/hierarchy
     */
    private void mainMenu(){
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
                          "Exit"                                                // TODO
                        , "Consult Client"                                      // TODO
                        , "Register Client"                                     // TODO
                        , "Register Normal Service"                             // TODO
                        , "Register Express Service"                            // TODO
                        , "Consult Express Services"                            // TODO
                        , "Consult Budget Request"                              // TODO
                        , "Check Technician Availability"));                    // TODO

         CounterMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
         CounterMenu.setPreCondition(5, ()->this.workers_facade.hasExpressServices());
         CounterMenu.setPreCondition(6, ()->this.workers_facade.hasBudgetRequests());
         CounterMenu.setPreCondition(7, ()->this.workers_facade.hasWorkers(Hierarchy.TECHNICIAN));

         //CounterMenu.setHandler(0, this:: ?);
         CounterMenu.setHandler(1, this::consult_client);
         //CounterMenu.setHandler(2, this:: ?);
         //CounterMenu.setHandler(3, this:: ?);
         //CounterMenu.setHandler(4, this:: ?);
         //CounterMenu.setHandler(5, this:: ?);
         //CounterMenu.setHandler(6, this:: ?);
         //CounterMenu.setHandler(7, this:: ?);
        CounterMenu.run();
        System.out.println();

        /**
         * TECHNICIAN - MENU
         */
        Menu TechnicianMenu = new Menu("Technician - [@user]",
                 Arrays.asList(
                           "Exit"                                               // TODO
                         , "Consult Budget Requests"                            // TODO
                         , "Consult Express Requests"));                        // TODO

        TechnicianMenu.setPreCondition(1, ()->this.workers_facade.hasBudgetRequests());
        TechnicianMenu.setPreCondition(2, ()->this.workers_facade.hasExpressServices());


        /**
         * MANAGER - MENU
         */
        Menu ManagerMenu = new Menu("Manager - [@user]",
                Arrays.asList(
                          "Exit"                                                // TODO
                        , "Consult Client"                                      // TODO
                        , "Consult Normal Service"                              // TODO
                        , "Consult Express Service"                             // TODO
                        , "Consult Worker"                                      // TODO
                ));
        ManagerMenu.setPreCondition(1, ()->this.workers_facade.hasClients());
        ManagerMenu.setPreCondition(2, ()->this.workers_facade.hasBudgetRequests());
        ManagerMenu.setPreCondition(3, ()->this.workers_facade.hasExpressServices());
        ManagerMenu.setPreCondition(4, ()->this.workers_facade.hasWorkers());

        //ManagerMenu.setHandler(0,);
        ManagerMenu.setHandler(1, this::consult_client);
        //ManagerMenu.setHandler(2,);
        //ManagerMenu.setHandler(3,);
        //ManagerMenu.setHandler(4,);
    }


    /**
     * Consult the list of existing clients
     */
    private void consult_client(){

        /**
         * Em vez de 1º mostrar todos e depois dar a opcao de "cada", podia meter a listagem de todos como uma das opcoes do menu... TODO
         */

        // Prints a book of clients
        System.out.println("\n\n\n\n\n".repeat(10));
        Book.show_clients(this.workers_facade.getClients());

        // shows the search client menu
        Menu ConsultClient = new Menu("Search Client",
                Arrays.asList(
                          "Back"
                        , "by Nif"
                        , "by Phone"));

        ConsultClient.setHandler(0, this::mainMenu);
        ConsultClient.setHandler(1, this::ConsultClientByNif);
        ConsultClient.setHandler(2, this::ConsultClientByPhone);
        ConsultClient.run();

    }

    /**
     * Searchs for a client by his ID/NIF
     */
    private Client ConsultClientByNif(){

        // Leitura input
        String nif = null;
        nif = this.getInput.nextLine();

        // Procura
        Client c = this.workers_facade.getClientByNif(nif);
        if(c != null)
            System.out.println("Got : " + c);
        else
            System.out.println("error: Client does not exist");
        return c;
    }

    /**
     * Searchs for a client by his phone number
     */
    private Client ConsultClientByPhone(){

        // Leitura input
        String phone = null;
        phone = this.getInput.nextLine();

        // Procura
        Client c = this.workers_facade.getClientByPhone(phone);
        if(c!=null)
            System.out.println("Got : " + c);
        else
            System.out.println("error: Client does not exist");
        return c;
    }

}
