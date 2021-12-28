package UI;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.Services.Service;
import org.javatuples.Triplet;

import java.io.IOException;
import java.time.LocalTime;
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
     * Registers a new normal service
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

        this.shopUI.getServices_facade().registerBudgetRequest(this.shopUI.getWorkers_facade(), new Service(nif, equipment_name, shopUI.getUsername()));    //TODO CUIDADO COUNTERID
        System.out.println("New register made for nif [" + nif + "]");
        shopUI.pause();
        clearview();
        // TODO
    }

    /**
     * Shows an auxiliar menu with the purpose of showing budgets to the user
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void ConsultBudgetRequest() throws IOException, ClassNotFoundException {
        clearview();

        // shows the search client menu
        Menu ConsultBudget = new Menu("Search Budget",
                Arrays.asList(
                        "Back"
                        , "by Status"
                        , "by ClientID"));


        ConsultBudget.setHandler(0, shopUI::MainMenu);
        ConsultBudget.setHandler(1, this::ConsultBudgetByStatus);
        ConsultBudget.setHandler(2, this::ConsultBudgetByNif);
        ConsultBudget.run();
    }

    /**
     * Auxiliar menu to show budgets by their status
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
                        , BudgetStatus.WAITING_APPROVAL.toString()
                        , BudgetStatus.DECLINED.toString()
                        , BudgetStatus.WAITING_REPAIR.toString()
                        , BudgetStatus.REPAIRING.toString()
                        , BudgetStatus.WAITING_PICKUP.toString()
                        , BudgetStatus.DELIVERED.toString()
                        , BudgetStatus.ARCHIVED.toString()
                ));

        ConsultStatus.setHandler(0, this::ConsultBudgetRequest);
        ConsultStatus.setHandler(1, () -> ConsultBudget(BudgetStatus.WITHOUT_BUDGET));
        ConsultStatus.setHandler(2, () -> ConsultBudget(BudgetStatus.WAITING_APPROVAL));
        ConsultStatus.setHandler(3, () -> ConsultBudget(BudgetStatus.DECLINED));
        ConsultStatus.setHandler(4, () -> ConsultBudget(BudgetStatus.WAITING_REPAIR));
        ConsultStatus.setHandler(5, () -> ConsultBudget(BudgetStatus.REPAIRING));
        ConsultStatus.setHandler(6, () -> ConsultBudget(BudgetStatus.WAITING_PICKUP));
        ConsultStatus.setHandler(7, () -> ConsultBudget(BudgetStatus.DELIVERED));
        ConsultStatus.setHandler(8, () -> ConsultBudget(BudgetStatus.ARCHIVED));
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
        for (int i = 0; i < budgets.size(); i++) {
            System.out.println("Got : " + budgets.get(i));          //todo
        }
        shopUI.pause();
        this.updateBudgetMenu(budgets);
    }


    private void updateBudgetMenu(List<Budget> budgets) throws IOException, ClassNotFoundException {
        Menu confirmupdatebudget = new Menu("Do you want to update any budget?",
                Arrays.asList(
                        "No"
                        , "Yes"
                ));
        confirmupdatebudget.setHandler(0, shopUI::MainMenu);
        confirmupdatebudget.setHandler(1, () -> which_budget(budgets));
        confirmupdatebudget.run();
    }

    private void which_budget(List<Budget> budgets) throws IOException, ClassNotFoundException {
        clearview();

        for (int i = 0; i < budgets.size(); i++) {
            System.out.println("Got(#" + (i + 1) + ") : " + budgets.get(i));
        }
        System.out.println("\nIntroduz o numero do budget a atualizar ou 0 para voltar atras: ");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if (choice == 0) {
            updateBudgetMenu(budgets);
        }
        if (choice < 1 || choice > budgets.size() + 1) {
            System.out.println("Numero inválido");
            shopUI.pause();
            updateBudgetMenu(budgets);
        } else {
            which_parameter_to_update_budget(budgets.get(choice - 1), budgets);
        }
    }

    private void which_parameter_to_update_budget(Budget b, List<Budget> budgets) throws IOException, ClassNotFoundException {
        Menu budget_parameters = new Menu("Which parameter to change?",
                Arrays.asList(
                        "Back"
                        , "Preço final"
                        , "Passos estimados"
                        , "Estado"
                ));
        budget_parameters.setHandler(0, () -> which_budget(budgets));
        budget_parameters.setHandler(1, () -> change_preco_budget(b));
        budget_parameters.setHandler(2, () -> change_passos_budget(b));
        budget_parameters.setHandler(3, () -> change_estado_budget(b, budgets));
        budget_parameters.run();
    }

    private void change_preco_budget(Budget b) {

        System.out.print("New price: ");
        double new_price = Double.parseDouble(shopUI.getScanner().nextLine());

        shopUI.getServices_facade().update_budget(b, new_price, b.getTodo(), b.getStatus());
        clearview();
    }

    private void change_passos_budget(Budget b) {

        boolean flag = true;
        List<Triplet<String, LocalTime, Double>> new_passos = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:mm:ss");
        while (flag) {
            System.out.print("Introduz a descrição do passo: ");
            String aux_s = shopUI.getScanner().nextLine();
            System.out.print("Introduz as horas no formato (H:mm:ss): ");
            String aux_time = shopUI.getScanner().nextLine();
            System.out.print("Introduz o custo do passo: ");
            String aux_price = shopUI.getScanner().nextLine();
            try {
                new_passos.add(new Triplet(aux_s, dtf.parse(aux_time), Double.parseDouble(aux_price)));
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Data invalida");
                break;
            }
            System.out.print("Introduza 1 para adicionar mais um passo ou 0 para terminar: ");
            int aux_choice = Integer.parseInt(shopUI.getScanner().nextLine());
            if (aux_choice != 1) {
                flag = false;
            }
        }

        shopUI.getServices_facade().update_budget(b, b.getFinalPrice(), new_passos, b.getStatus());
        shopUI.getServices_facade().view_passos(b);
    }


    private void change_estado_budget(Budget b, List<Budget> budgets) throws IOException, ClassNotFoundException {
        clearview();
        System.out.println("""
                \\---<>* STATES *<>---\\\s
                (1) - WITHOUT_BUDGET\s
                (2) - WAITING_APPROVAL\s
                (3) - DECLINED\s
                (4) - WAITING_REPAIR\s
                (5) - REPAIRING\s
                (6) - WAITING_PICKUP\s
                (7) - DELIVERED\s
                (8) - ARCHIVED
                """);
        System.out.println("#> Option: ");
        int choice = Integer.parseInt(shopUI.getScanner().nextLine());
        if (choice < 1 || choice > 8) {
            System.out.println("#> error: Unavailable option");
            shopUI.pause();
            which_parameter_to_update_budget(b, budgets);
        }
        //shopUI.getWorkers_facade().updateBudgetStatus()
        shopUI.getServices_facade().update_budget(b, b.getFinalPrice(), b.getTodo(), BudgetStatus.getStatus(choice));
    }


    /**
     * Consults a list of budgets by their status
     *
     * @param budgetStatus
     */
    private void ConsultBudget(BudgetStatus budgetStatus) {

        List<Budget> budgets;
        budgets = new ArrayList<>(shopUI.getServices_facade().getBudgetRequestsbyStatus(budgetStatus).values()); //TODO CLONE!!!!!!!

        Book.Show_Budgets_By_Status(budgets);
    }


    /**
     * Menu that shows the available options for seeing the express services available
     */
    void ConsultExpressServicesMenu() throws IOException, ClassNotFoundException {

        clearview();
        Menu express_services = new Menu("Search Express Services",
                Arrays.asList(
                        "Back"
                        , "Consult all services"
                        , "Consult service"
                ));

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
        System.out.print("Option: ");
        try {
            option = shopUI.getScanner().nextLine();
            op = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("Opção Inválida!!!");
            shopUI.pause();
            return;
        }
        if (op <= 0 || op > size) {
            System.out.println("Opção Inválida!!!");
            shopUI.pause();
            return;
        }

        ExpressRepair express = shopUI.getServices_facade().getExpressService(op);
        System.out.println("\nGot : " + express.toString(op));
        shopUI.pause();

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

        register_express.setHandler(0, this::ConsultExpressServicesMenu);
        register_express.setHandler(1, () -> RegisterExpressService(er));
        register_express.run();
    }

    /**
     * Actual registration of a new express service request
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

        payment.setHandler(0, shopUI::MainMenu);
        payment.setHandler(1, this::Payment);

        payment.run();
    }


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



    private Budget ChooseToDeliver(List<Budget> budgets){

        int index = 0;
        for(; index < budgets.size(); index++){

            System.out.println("\n#> Got ("+index+"): " + budgets.get(index));
        }

        System.out.print("Which one to deliver: ");
        String option;
        int op;
        try {
            option = shopUI.getScanner().nextLine();
            op = Integer.parseInt(option);
        } catch (NumberFormatException e) {
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