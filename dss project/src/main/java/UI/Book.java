package UI;


import BusinessLayer.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;
import java.util.*;

public class Book {

    private static final Scanner scanner = new Scanner(System.in);

    public static char readOption(){

        int answer;

        System.out.print("#> Option: ");
        String line = scanner.nextLine();
        char c = ' ';
        if(line.length() > 0)
            c = line.charAt(0);

        if(c == 'a' || c == 'd' || c == 'c')
            return c;

        System.out.println("error: invalid option");
        return ' ';
    }

    /**
     * Book that shows system's clients
     * @param clients
     */
    public static void show_clients(ClientDAO clients){

        Map<String, Client> client_map = clients.get();
        List<Object> list = Arrays.asList(client_map.values().stream().toList().toArray());

        Show_book(list);
    }

    /**
     * Book that shows the list of workers from a specific hierarchy/type
     * @param wdao
     * @param h
     */
    public static void Show_Workers(WorkersDAO wdao, Hierarchy h){

        Map<String, Worker> workers = wdao.get(h);

        List<Object> list = Arrays.asList(workers.values().stream().toList().toArray());

        Show_book(list);
    }


    /**
     * Book that shows the list of budgets registered with a specific status
     * @param list
     */
    public static void Show_Budgets_By_Status(List<Budget> list){

        Collections.sort(list);
        List<Object> budgets_list = Arrays.asList(list.toArray());
        Show_book(budgets_list);
    }

    /**
     * Book that shows the express services available at the repair center
     * @param services
     */
    public static void Show_ExpressServices(List<Object> services){

        Show_book(services);
    }


    /**
     * Auxiliar function that controls the flow of the book
     * @param list List of objects to be shown
     */
    private static void Show_book(List<Object> list){

        int number = list.size();

        int per_page = 6;
        if(number <= per_page){
            per_page = number;
        }
        int original_per_page = per_page;
        int n_pages = (int)Math.ceil((double)number/per_page);
        int index = 0;
        int current_page = 1;

        while(true) {

            int aux_index = index + per_page;
            System.out.println();
            // print dos objetos
            for (; index < aux_index; index++) {

                if(list.get(index) instanceof ExpressRepair){
                    System.out.println(((ExpressRepair) list.get(index)).toString(index+1));
                }
                else {
                    System.out.println(list.get(index));
                }
            }
            // user help
            System.out.println("\na - previous | d - next | c - leave | Page [" + current_page + "]/[" + n_pages + "]");
            char option = ' ';

            // read next option
            while (option != 'a' && option != 'd' && option != 'c') {
                option = readOption();
            }
            // if "previous",
            if (option == 'a') {

                if(current_page == n_pages && current_page == 1 && number < per_page){
                    index -= per_page;
                }

                // Se estiver na última página,
                if(current_page == n_pages && current_page != 1) {

                    //andar para tras a última + 1
                    index -= (original_per_page + per_page);
                    // atualizar o per_page para o valor original
                    per_page = original_per_page;
                }
                // Se a página atual for diferente da 1ª, anda para tras
                else if (current_page >= 1){
                    index -= original_per_page;
                }

                //Se a pagina for diferente da primeira, pode retroceder no número
                if (current_page > 1) current_page--;

            }
            // if "next",
            else if (option == 'd') {


                // estou a saltar para a ultima pagina
                if (current_page < n_pages) {

                    // Se a última página for mais pequena que o normal, atualiza esse valor
                    if (index + (per_page - 1) > number) {
                        per_page = number - index;
                    }
                    current_page++;

                    // Se tentei avançar e estou na última página, volto a imprimir a ultima pagina
                } else if (current_page == n_pages) {

                    index -= per_page;
                }
            }
            // Leave
            else{
                ShopUI.clearview();
                return;
            }
        }
    }

}
