package UI;


import BusinessLayer.Client.Client;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Worker;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Book {

    private static final Scanner scanner = new Scanner(System.in);

    public static char readOption(){

        int answer;

        System.out.print("#> Option: ");
        String line = scanner.nextLine();
        char c = line.charAt(0);

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

        int number = clients.getSize();

        int per_page = 6;
        if(number <= per_page){
            per_page = number;
        }
        int original_per_page = per_page;

        int n_pages = (int)Math.ceil((double)number/per_page);

        int index = 0;
        // page 1 -> 0 - 5
        // page 2 -> 6 - 11
        // page 3 -> 12 - 17
        // page 4 -> 18 - 23

        Map<String, Client> client_map = clients.get();
        List<Object> list = Arrays.asList(client_map.values().stream().toList().toArray());
        Show_book(list, number, index, original_per_page, per_page, n_pages);
    }

    /**
     * Book that shows the list of workers from a specific hierarchy/type
     * @param wdao
     * @param h
     */
    public static void Show_Workers(WorkersDAO wdao, Hierarchy h){

        Map<String, Worker> workers = wdao.get(h);

        int number = workers.size();

        int per_page = 6;
        if(number <= per_page){
            per_page = number;
        }
        int original_per_page = per_page;
        int n_pages = (int)Math.ceil((double)number/per_page);
        int index = 0;

        List<Object> list = Arrays.asList(workers.values().stream().toList().toArray());
        Show_book(list, number, index, original_per_page, per_page, n_pages);

    }

    /**
     * Auxiliar function that controls the flow of the book
     * @param list List of objects to be shown
     * @param number Number of elements
     * @param index Starting index of the book (0)
     * @param original_per_page Starting number of elements per page
     * @param per_page Number of per page elements
     * @param n_pages Number of pages of the book
     */
    private static void Show_book(List<Object> list, int number, int index, int original_per_page, int per_page, int n_pages){

        int current_page = 1;

        while(true) {

            int aux_index = index + per_page;
            // print dos objetos
            for (; index < aux_index; index++) {
                System.out.println(list.get(index));
            }
            // user help
            System.out.println("a - previous | d - next | c - leave | Page [" + current_page + "]/[" + n_pages + "]");
            char option = ' ';

            // read next option
            while (option != 'a' && option != 'd' && option != 'c') {
                option = readOption();
            }
            // if "previous",
            if (option == 'a') {

                // Se estiver na última página,
                if(current_page == n_pages) {

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
            // Se a opção for avançar,
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
            else return;
        }
    }

}
