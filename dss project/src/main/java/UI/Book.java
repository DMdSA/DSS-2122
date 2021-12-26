package UI;


import BusinessLayer.Client.Client;
import DataBase.ClientDAO;

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

    // (object o, int(1-clients, 2-workers, etc)
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
        List<Client> list = client_map.values().stream().toList();
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
