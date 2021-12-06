package BusinessLayer.WorkersFunctions;

import java.util.Scanner;

public interface IWorkersFuncions {

    public static Integer wrong_credentials(){

        System.out.println("\tPressiona 1 para reescrever credenciais");
        System.out.println("\tPressiona 0 para sair");
        Scanner wait = new Scanner(System.in);
        return wait.nextInt();
    }

    public static void credentials_error(){
        System.out.println("Acess Denied");
    }
}
