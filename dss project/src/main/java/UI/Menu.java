package UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    /**
     * Precondition auxiliar interface
     */
    public interface PreCondition{
        boolean validate();
    }

    /**
     * Handler auxiliar interface
     */
    public interface Handler{
        void execute() throws IOException, ClassNotFoundException;
    }

    /**
     * Instance Variables
     */
    private String title;
    private List<String> options;
    private List<PreCondition> availableOptions;
    private List<Handler> handlers;

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Default Constructor
     *
     * Cria um menu default ao qual se podem adicionar opções
     */
    public Menu(){
        this.title = "Title";
        this.options = new ArrayList<>();
        this.availableOptions = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    /**
     * Constructor para menu com título e uma lista de opções
     * @param title
     * @param options
     */
    public Menu(String title, List<String> options){

        this.title = title;
        this.options = options;
        this.availableOptions = new ArrayList<>();
        this.handlers = new ArrayList<>();
        for(String s : this.options){

            availableOptions.add(()->true);
            handlers.add(() -> System.out.println("Opção não implementada"));
        }
    }

    public Menu(List<String> options){
        this.title = "Menu";
        this.options = options;
    }


    public void addOption(String option, PreCondition pc, Handler h){
        this.options.add(option);
        this.availableOptions.add(pc);
        this.handlers.add(h);
    }

    public void setPreCondition(int menuIndex, PreCondition pc){
        // index from 0 - <>
        this.availableOptions.set(menuIndex, pc);
    }

    public void setHandler(int menuIndex, Handler h){
        // index from 0 - <>
        this.handlers.set(menuIndex, h);
    }

    /**
     * SHOW a menu, represented by it's available options and handlers
     * If an option is read as available, it is shown and handled
     * Otherwise, it will not be available for the user to use
     */
    public void show(){

        System.out.println("\\---<>* "+this.title+" *<>---\\");

        for(int i = 1; i < this.options.size(); i++){
            System.out.print("("+i+") -> ");
            System.out.println(this.availableOptions.get(i).validate() ? this.options.get(i) : "------");
        }
        System.out.println("(0) -> " + this.options.get(0));
    }


    /**
     * readOption reads an integer from user, from a specific number of options,
     * previously set by current's menu
     * @return
     */
    public int readOption(){

        int answer;

        System.out.print("#> Option: ");
         try {
            String line = scanner.nextLine();
            answer = Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            answer = -1;
        }

         if(answer < 0 || answer > this.options.size()){
             System.out.println("error: invalid option");
             answer = -1;
         }
         return answer;
    }


    public void run() throws IOException, ClassNotFoundException {

        int option;

        do{
            this.show();
            option = this.readOption();
            if(option > 0 && !this.availableOptions.get(option).validate())
                System.out.println("Unavailable option. Try again.");
            else if(option > 0){

                this.handlers.get(option).execute();
            }
        } while(option != 0);

        System.out.println("\n\n\n\n\n".repeat(10));
    }

}
