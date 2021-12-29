package UI;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.Service;
import BusinessLayer.Workers.Hierarchy;
import org.javatuples.Triplet;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static UI.ShopUI.clearview;

public class ServicesUI {

    /**
     * Instance Variables
     */
    private ShopUI shopUI;

    /**
     * Constructor
     */
    public ServicesUI(ShopUI shopui) {
        this.shopUI = shopui;
    }


    /**
     * Registers a new normal service, asking for the client's nif and registering
     * their equipment's description
     */
    void RegisterNormalService() {
        clearview();

        System.out.print("#> Nif:");
        String nif = shopUI.getScanner().nextLine();

        boolean registered = this.shopUI.getWorkers_facade().hasClient(nif);
        if (!registered) {
            System.out.println("error: That nif is not registered!");
            return;
        }

        System.out.print("#> Equipment description: ");
        String equipment_name = shopUI.getScanner().nextLine();
        // regista um pedido de orçamento
        this.shopUI.getServices_facade().registerBudgetRequest(this.shopUI.getWorkers_facade(), new Service(nif, equipment_name, shopUI.getUsername()));    //TODO CUIDADO COUNTERID
        System.out.println("New register made for nif [" + nif + "]");
        shopUI.pause();
        clearview();
    }

    /**
     * Menu used for searching for a budget
     * Can be done by its status, or via client's nif
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void ConsultBudgetRequest() throws IOException, ClassNotFoundException {
        clearview();

        Menu ConsultBudget = new Menu("Search Budget",
                Arrays.asList(
                        "Back"
                        , "by Status"
                        , "by ClientID")
        );

        // set of handlers
        ConsultBudget.setHandler(0, shopUI::MainMenu);
        ConsultBudget.setHandler(1, this::ConsultBudgetByStatus);
        ConsultBudget.setHandler(2, this::ConsultBudgetByNif);

        ConsultBudget.run();
    }

    /**
     * Menu used for searching for budgets by their statuses
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void ConsultBudgetByStatus() throws IOException, ClassNotFoundException {
        clearview();

        Menu ConsultStatus = new Menu("States",
                Arrays.asList(
                        "Back"
                        , BudgetStatus.WITHOUT_BUDGET.toString()
                        , BudgetStatus.DOING_BUDGET.toString()
                        , BudgetStatus.WAITING_APPROVAL.toString()
                        , BudgetStatus.DECLINED.toString()
                        , BudgetStatus.WAITING_REPAIR.toString()
                        , BudgetStatus.REPAIRING.toString()
                        , BudgetStatus.ON_HOLD.toString()
                        , BudgetStatus.WAITING_PICKUP.toString()
                        , BudgetStatus.DELIVERED.toString()
                        , BudgetStatus.ARCHIVED.toString()
                ));

        // set of handlers
        ConsultStatus.setHandler(0, this::ConsultBudgetRequest);
        ConsultStatus.setHandler(1, () -> ConsultBudget(BudgetStatus.WITHOUT_BUDGET));
        ConsultStatus.setHandler(2, () -> ConsultBudget(BudgetStatus.DOING_BUDGET));
        ConsultStatus.setHandler(3, () -> ConsultBudget(BudgetStatus.WAITING_APPROVAL));
        ConsultStatus.setHandler(4, () -> ConsultBudget(BudgetStatus.DECLINED));
        ConsultStatus.setHandler(5, () -> ConsultBudget(BudgetStatus.WAITING_REPAIR));
        ConsultStatus.setHandler(6, () -> ConsultBudget(BudgetStatus.REPAIRING));
        ConsultStatus.setHandler(7, () -> ConsultBudget(BudgetStatus.ON_HOLD));
        ConsultStatus.setHandler(8, () -> ConsultBudget(BudgetStatus.WAITING_PICKUP));
        ConsultStatus.setHandler(9, () -> ConsultBudget(BudgetStatus.DELIVERED));
        ConsultStatus.setHandler(10, () -> ConsultBudget(BudgetStatus.ARCHIVED));

        ConsultStatus.run();
    }

    /**
     * Consults all budgets requested by a client
     */
    private void ConsultBudgetByNif() throws IOException, ClassNotFoundException {
        clearview();

        // Leitura input
        String nif = null;
        System.out.print("Client's Nif: ");
        nif = shopUI.getScanner().nextLine();

        boolean registered = this.shopUI.getWorkers_facade().hasClient(nif);
        if (!registered) {
            System.out.println("error: That nif is not registered!");
            return;
        }

        // Procura
        List<Budget> budgets = this.shopUI.getServices_facade().getBudgetbyNif(nif);

        if (budgets.size() == 0) {
            System.out.println("error: This client has no requests");
            shopUI.pause();
            return;
        }
        System.out.println();
        for (Budget budget : budgets) {
            System.out.println("Got : " + budget);
        }
        shopUI.pause();
        this.updateBudgetMenu(budgets);
    }

    /**
     * Auxiliar Menu to choose if you want to update a budget
     *  @param budgets List of the budgets with given client NIF
     */
    private void updateBudgetMenu(List<Budget> budgets) throws IOException, ClassNotFoundException {

        Menu confirm_update_budget = new Menu("Do you want to update any budget?",
                Arrays.asList(
                        "No"
                        , "Yes"
                ));

        // set of handlers
        confirm_update_budget.setHandler(0, shopUI::MainMenu);
        confirm_update_budget.setHandler(1, () -> which_budget(budgets));

        confirm_update_budget.run();
    }

    /**
     * Auxiliar Function to choose from a list of budgets which budget to update
     *  @param budgets List of the budgets with given client NIF
     */
    private void which_budget(List<Budget> budgets) throws IOException, ClassNotFoundException {
        clearview();

        // ??
        for (int i = 0; i < budgets.size(); i++) {
            //i+1 para na consola aparecer de 1 ate list.size +1, para deixar 0 como exit
            System.out.println("#> Got(#" + (i + 1) + ") : " + budgets.get(i));
        }

        System.out.println("\n#> Introduz o numero do budget a atualizar ou 0 para voltar atras: ");

        int choice = -1;
        try {
            choice = Integer.parseInt(shopUI.getScanner().nextLine());
        }
        catch (NumberFormatException ignored) {
        }
        if (choice == 0) {
            updateBudgetMenu(budgets);
        }
        if (choice < 1 || choice > budgets.size() + 1) {

            System.out.println("#> Numero inválido"); //Caso a escolha nao corresponda a budget nenhum, erro e volta para tras
            shopUI.pause();
            updateBudgetMenu(budgets);
        }
        else {
            // ???
            which_parameter_to_update_budget(budgets.get(choice - 1), budgets); //choice - 1 para o range voltar de 0 a list.size
        }
    }

    /**
     * Auxiliar Menu to choose which parameter from a budget to update
     * @param b budget previously chosen to update
     *  @param budgets List of the budgets with given client NIF
     */
    private void which_parameter_to_update_budget(Budget b, List<Budget> budgets) throws IOException, ClassNotFoundException {

        Menu budget_parameters = new Menu("Which parameter to change?",
                Arrays.asList(
                        "Back"
                        , "Preço final"
                        , "Passos estimados"
                        , "Estado"
                ));

        // set of handlers
        budget_parameters.setHandler(0, () -> which_budget(budgets));
        budget_parameters.setHandler(1, () -> Change_preco_budget(b));
        budget_parameters.setHandler(2, () -> change_passos_budget(b));
        budget_parameters.setHandler(3, () -> change_estado_budget(b, budgets));

        budget_parameters.run();
    }

    /**
     * Auxiliar fuction to get the new price of a budget and update it
     * @param b budget previously chosen to update
     */
    private void Change_preco_budget(Budget b) {

        System.out.print("New price: ");
        double new_price = Double.parseDouble(shopUI.getScanner().nextLine());

        // atualiza o budget
        boolean flag = shopUI.getServices_facade().update_budget(b, new_price, b.getTodo(), b.getStatus());

        if (flag){
            System.out.println("# Budget updated with success");
        }
        else {
            System.out.println("#> error: could not update budget" + b.getBudgetID());
        }
        shopUI.pause();
        clearview();
    }

    /**
     * Auxiliar fuction to get a list of steps to update in the Budget b
     * @param b budget previously chosen to update
     */
    private void change_passos_budget(Budget b) {

        boolean flag = true;
        List<Triplet<String, String, Double>> new_passos = new ArrayList<>();

         //formatter to convert from string to LocalTime
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");

        // enquanto o user quiser adicionar passos,
        while (flag) {

            System.out.print("Introduz a descrição do passo: ");
            String aux_s = shopUI.getScanner().nextLine();

            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();

            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();

            try {
                // ?
                new_passos.add(new Triplet<>(aux_s, dtf.parse(aux_time).toString(), Double.parseDouble(aux_price)));
            }
            catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                break;
            }
            // opção de continuar, ou terminar a ação
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

        // atualizar o budget
        shopUI.getServices_facade().update_budget(b, b.getEstimatedPrice(), new_passos, b.getStatus());
    }

    /**
     * Auxiliar fuction to choose the new status of a budget
     * @param b budget previously chosen to update
     */
    private void change_estado_budget(Budget b, List<Budget> budgets) throws IOException, ClassNotFoundException {
        clearview();

        System.out.println("""
                \\---<>* STATES *<>---\\\s
                (1) - WITHOUT_BUDGET\s
                (2) - DOING_BUDGET\s
                (3) - WAITING_APPROVAL\s
                (4) - DECLINED\s
                (5) - WAITING_REPAIR\s
                (6) - REPAIRING\s
                (7) - ON_HOLD\s
                (8) - WAITING_PICKUP\s
                (9) - DELIVERED\s
                (10) - ARCHIVED
                """);
        System.out.println("#> Option: ");
        int choice = -1;
        try {
            choice = Integer.parseInt(shopUI.getScanner().nextLine());
        }
        catch (NumberFormatException ignored) {
        }
        if (choice < 1 || choice > 10) {

            System.out.println("#> error: Unavailable option");
            shopUI.pause();
            which_parameter_to_update_budget(b, budgets);
        }
        else {
            // atualiza o budget
            shopUI.getServices_facade().update_budget(b, b.getEstimatedPrice(), b.getTodo(), BudgetStatus.getStatus(choice));
            System.out.println(("#> STATE UPDATED!"));
        }
        shopUI.pause();
    }


    /**
     * Consults the list of budgets by their status
     *
     * @param budgetStatus
     */
    private void ConsultBudget(BudgetStatus budgetStatus) {

        List<Budget> budgets;
        budgets = new ArrayList<>(shopUI.getServices_facade().getBudgetRequestsbyStatus(budgetStatus).values());

        Book.Show_Budgets_By_Status(budgets);
    }


    /**
     * Menu that shows the available options for seeing the express services available
     */
    void ConsultExpressServicesMenu(Hierarchy h) throws IOException, ClassNotFoundException {
        clearview();

        Menu express_services = new Menu("Search Express Services",
                Arrays.asList(
                        "Back"
                        , "Consult all services"
                        , "Consult service"
                ));

        // set of pre conditions
        express_services.setPreCondition(2, ()->(h.equals(Hierarchy.COUNTER)));

        // set of handlers
        express_services.setHandler(0, shopUI::MainMenu);
        express_services.setHandler(1, this::ConsultExpressServices);
        express_services.setHandler(2, this::ConsultExpressService);

        express_services.run();
    }

    /**
     * Shows the available express services
     */
    private void ConsultExpressServices() {

        clearview();
        List<Object> objects = shopUI.getServices_facade().consultExpressServices();
        Book.Show_ExpressServices(objects);
    }

    /**
     * Shows a specific express service and gives the option
     * to create a new request
     */
    private void ConsultExpressService() throws IOException, ClassNotFoundException {

        int size = shopUI.getServices_facade().numberOfExpressServices();

        String option;
        int op;
        System.out.print("Service Token: ");

        try {

            option = shopUI.getScanner().nextLine();
            op = Integer.parseInt(option);
        }
        catch (NumberFormatException e) {

            System.out.println("Invalid Service!!!");
            shopUI.pause();
            return;
        }

        // avaliar o input recebido
        if (op <= 0 || op > size) {
            System.out.println("Invalid Service!!!");
            shopUI.pause();
            return;
        }

        // get do serviço expresso pedido
        ExpressRepair express = shopUI.getServices_facade().getExpressService(op);
        System.out.println("\nGot : " + express.toString(op));
        shopUI.pause();

        // apresentar o menu de registo de novo pedido expresso
        this.RegisterExpressServiceMenu(express);
    }

    /**
     * Menu that works for creating a new request of an express service
     *
     * @param er
     */
    private void RegisterExpressServiceMenu(ExpressRepair er) throws IOException, ClassNotFoundException {

        Menu register_express = new Menu("Register Express Service ?",
                Arrays.asList(
                        "Cancel"
                        , "Confirm"
                ));

        // set of handlers
        register_express.setHandler(0, () -> ConsultExpressServicesMenu(this.shopUI.getHierarchy_logged()));
        register_express.setHandler(1, () -> RegisterExpressService(er));

        register_express.run();
    }

    /**
     * Actual registration of a new express service request
     * asking for user's input
     *
     * @param er
     */
    private void RegisterExpressService(ExpressRepair er) {
        clearview();

        System.out.print("#> Nif:");
        String nif = shopUI.getScanner().nextLine();

        boolean registered = shopUI.getWorkers_facade().hasClient(nif);
        if (!registered) {
            System.out.println("error: That nif is not registered!");
            shopUI.pause();
            clearview();
            return;
        }

        System.out.print("#> Equipment description: ");
        String equipment_name = shopUI.getScanner().nextLine();

        shopUI.getServices_facade().registerBudgetRequest(this.shopUI.getWorkers_facade(), new Service(nif, equipment_name, shopUI.getUsername(), er));
        System.out.println("New register made for nif [" + nif + "]");
        shopUI.pause();
        clearview();
    }


    void PaymentMenu() throws IOException, ClassNotFoundException {
        clearview();

        Menu payment = new Menu("Payment",
                Arrays.asList(
                        "Back",
                        "by Client ID"
                ));

        // set of handlers
        payment.setHandler(0, shopUI::MainMenu);
        payment.setHandler(1, this::Payment);

        payment.run();
    }

    /**
     * Function responsible for the payment action
     * It asks for the client's nif, the searchs for any service ready to be delivered to that
     * client and, if there is any, it proceeds to the payment
     */
    private void Payment(){

        // Vai buscar os pedidos de serviço que estão por entregar
        List<Budget> budgetList = this.SearchToDeliverByClient();

        if(budgetList != null && budgetList.size() > 0) {
            // Se tiver algum servico por entregar, dá a possibilidade de escolher qual e proceder
            Budget b = this.ChooseToDeliver(budgetList);

            if(b == null){
                return;
            }

            // atualizar o budget de Waiting_Pickup -> Delivered
            // inclui remover antigo da linha de status errada, adicionando o novo à linha correta
            this.shopUI.getServices_facade().updateBudgetStatus(b, BudgetStatus.DELIVERED);

            String counter_username = this.shopUI.getUsername();
            boolean flag = this.shopUI.getWorkers_facade().updateDeliveryCounter(counter_username, b.getClientID());

            if (flag) {
                System.out.println("\n#> Payment done.");
            } else {
                System.out.println("\n#> error: could not make the payment");
            }
            shopUI.pause();
        }
    }


    /**
     * Given a list of budgets, this functions asks the user which one he chooses
     * to operate on
     * @param budgets
     * @return
     */
    private Budget ChooseToDeliver(List<Budget> budgets){

        int index = 0;
        // visualização dos orçamentos
        for(; index < budgets.size(); index++){

            System.out.println("\n#> Got ("+index+"): " + budgets.get(index));
        }

        System.out.print("Which one to deliver: ");
        String option;
        int op;

        try {
            option = shopUI.getScanner().nextLine();
            op = Integer.parseInt(option);
        }
        catch (NumberFormatException e) {
            System.out.println("Opção Inválida!!!");
            shopUI.pause();
            return null;
        }

        if (op < 0 || op >= budgets.size()) {
            System.out.println("Opção Inválida!!!");
            shopUI.pause();
            return null;
        }
        return budgets.get(op);
    }


    /**
     * Function that asks for a client's id and then searchs for any budget
     * that is waiting for its client's pickup
     * @return
     */
    private List<Budget> SearchToDeliverByClient() {

        clearview();
        // Leitura do input
        System.out.print("Client's Nif: ");
        String nif = shopUI.getScanner().nextLine();

        boolean registered = this.shopUI.getWorkers_facade().hasClient(nif);
        if (!registered) {
            System.out.println("error: That nif is not registered!");
            return null;
        }
        // Procura
        List<Budget> budgets = this.shopUI.getServices_facade().getBudgetbyNif(nif);
        List<Budget> answer = new ArrayList<>();
        if (budgets.size() == 0) {
            System.out.println("error: This client has no requests");
            shopUI.pause();
            return null;
        }

        // Filtrar apenas pedidos à espera de serem entregues
        for(Budget b : budgets){
            BudgetStatus bs = b.getStatus();
            if(bs.equals(BudgetStatus.WAITING_PICKUP)){
                answer.add(b);
            }
        }
        if (answer.size() == 0) {
            System.out.println("error: This client has no requests to be delivered");
            shopUI.pause();
            return null;
        }
        return answer;
    }
}