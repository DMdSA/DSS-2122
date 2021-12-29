package UI;

import BusinessLayer.Client;

import java.io.IOException;
import java.util.Arrays;

import static UI.ShopUI.clearview;

public class ClientsUI {

    /**
     * Instance variables
     */
    private ShopUI shopui;

    /**
     * Constructor
     * @param shopUI
     */
    public ClientsUI(ShopUI shopUI){
        this.shopui = shopUI;
    }


    /**
     * Consult the list of existing clients
     */
    void consult_client() throws IOException, ClassNotFoundException {

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

        ConsultClient.setHandler(0, shopui::MainMenu);
        ConsultClient.setHandler(1, () -> Book.show_clients(shopui.getWorkers_facade().getClients()));
        ConsultClient.setHandler(2, this::ConsultClientByNif);
        ConsultClient.setHandler(3, this::ConsultClientByPhone);
        ConsultClient.run();
        clearview();

    }

    /**
     * Searchs for a client by his ID/NIF
     */
    private void ConsultClientByNif() throws IOException, ClassNotFoundException {

        clearview();
        // Leitura input
        System.out.print("Nif: ");
        String nif = null;
        nif = shopui.getScanner().nextLine();

        // Procura
        Client c = this.shopui.getWorkers_facade().getClientByNif(nif);
        if(c != null) {
            System.out.println("Got : " + c);
            shopui.pause();
            MenuUpdateClient(c);
        }
        else{
            System.out.println("error: client not found!");
            shopui.pause();
        }
    }

    /**
     * Searchs for a client by his phone number
     */
    private void ConsultClientByPhone(){

        clearview();
        // Leitura input
        System.out.print("Phone: ");
        String phone = null;
        phone = shopui.getScanner().nextLine();

        // Procura
        Client c = shopui.getWorkers_facade().getClientByPhone(phone);
        if(c!=null) {
            System.out.println("Got : " + c);
        }
        else
            System.out.println("error: Client does not exist");
        shopui.pause();
    }

    /**
     * Registers a new client
     */
    void RegisterClient(){

        clearview();
        System.out.print("#> NIF: ");
        String nif = shopui.getScanner().nextLine();

        if(shopui.getWorkers_facade().hasClient(nif)){
            System.out.println("error: Client already exists!");
            return;
        }

        System.out.print("#> Name: ");
        String name = shopui.getScanner().nextLine();

        System.out.print("#> Email: ");
        String mail = shopui.getScanner().nextLine();

        System.out.print("#> Phone: ");
        String phone = shopui.getScanner().nextLine();

        try {
            shopui.getWorkers_facade().registerClient(new Client(name, nif, mail, phone));
            System.out.println("Added " + nif + " to database");
            shopui.pause();
            clearview();

        } catch (NullPointerException npe){
            System.out.println("error: could not add the new client. nullpointerexception");
        }
    }

    /**
     *  MENU Atualizar cliente
     */
    private void MenuUpdateClient(Client c) throws IOException, ClassNotFoundException {

        clearview();
        Menu UpdateClient = new Menu("Updating Client",
                Arrays.asList(
                        "Back"
                        , "Update Phone"
                        , "Update Mail"));

        UpdateClient.setHandler(0, this::consult_client);
        UpdateClient.setHandler(1, () -> UpdateClientPhone(c));
        UpdateClient.setHandler(2, () -> UpdateClientMail(c));
        UpdateClient.run();
    }

    private void UpdateClientPhone(Client c){

        clearview();
        System.out.print("#> new phone: ");
        String phone = this.shopui.getScanner().nextLine();

        boolean flag = shopui.getWorkers_facade().updateClientPhone(c, phone);

        if(flag){
            System.out.println("#> Updated client with success");
        }
        else{
            System.out.println("#> error: could not update client " + c.getNIF());
        }

        shopui.pause(); clearview();
    }

     private void UpdateClientMail(Client c){

        clearview();
        System.out.print("#> new mail: ");
        String mail = this.shopui.getScanner().nextLine();

        boolean flag = shopui.getWorkers_facade().updateClientMail(c, mail);

        if(flag){
            System.out.println("#> Updated client with success");
        }
        else{
            System.out.println("#> error: could not update client " + c.getNIF());
        }

        shopui.pause(); clearview();
    }
}
