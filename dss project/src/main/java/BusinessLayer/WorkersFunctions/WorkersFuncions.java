package BusinessLayer.WorkersFunctions;

import BusinessLayer.Workers.CounterWorker;
import DataBase.CounterWorkerDAO;


public class WorkersFuncions implements IWorkersFuncions {

    CounterWorkerDAO counterWorkerDAOClass = new CounterWorkerDAO();


    public boolean autenticate(String username,String password){
        boolean flag = false;
        int retry;
        while(!flag){

            if(counterWorkerDAOClass.counterWorkerDAO.containsKey(username)){

                if(counterWorkerDAOClass.counterWorkerDAO.get(username).getPassword().equals(password)){
                    flag = true;
                    counterWorkerDAOClass.counterWorkerDAO.get(username).setAutenticatedStatus(true);
                }
                else{
                    retry = IWorkersFuncions.wrong_credentials();
                    if(retry == 0){
                        IWorkersFuncions.credentials_error();
                        return false;
                    }
                }
            }
            else{
                retry = IWorkersFuncions.wrong_credentials();
                if(retry == 0){
                    System.out.println("Acess denied");
                    return false;
                }
            }
        }

        return flag;
    }

    public boolean isAutenticated(String username){
        return counterWorkerDAOClass.counterWorkerDAO.get(username).getAutenticatedStatus();
    }

    public boolean isAutenticated(CounterWorker counterWorker){
        return counterWorkerDAOClass.counterWorkerDAO.get(counterWorker.getUsername()).getAutenticatedStatus();
    }

}
