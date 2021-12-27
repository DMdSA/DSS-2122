package UI;

import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Manager;
import BusinessLayer.Workers.Technician;

import java.io.IOException;
import java.util.Arrays;

import static UI.ShopUI.clearview;

public class WorkersUI {

    /**
     * Instance Variables
     */
    private ShopUI shopUI;

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
                ));

        worker_type.setHandler(0, shopUI::MainMenu);
        worker_type.setHandler(1, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.COUNTER));
        worker_type.setHandler(2, () -> PrintWorker(Hierarchy.COUNTER));
        worker_type.setHandler(3, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.TECHNICIAN));
        worker_type.setHandler(4, () -> PrintWorker(Hierarchy.TECHNICIAN));
        worker_type.setHandler(5, () -> Book.Show_Workers(this.shopUI.getWorkers_facade().getWorkers(), Hierarchy.MANAGER));

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
        user = this.shopUI.getScanner().nextLine();

        // If the worker exists, it is printed
        if(this.shopUI.getWorkers_facade().hasWorker(h, user)){
            System.out.println("\nGot worker: " + this.shopUI.getWorkers_facade().getWorker(h, user)+"\n");
            shopUI.pause();
            clearview();
        }
        else{
            System.out.println("error: That worker is not registered");
            shopUI.pause();
            clearview();
        }
    }
}
