package DataBase;

import BusinessLayer.Workers.CounterWorker;

import java.util.HashMap;
import java.util.Map;

public class CounterWorkerDAO extends DAOClass{


    public Map<String, CounterWorker> counterWorkerDAO = new HashMap<>();



    public CounterWorker get(String username){

        return counterWorkerDAO.get(username);
    }


    public boolean insert(CounterWorker counterWorker){

        if(counterWorkerDAO.containsKey(counterWorker.getUsername())){
            return false;
        }
        counterWorkerDAO.put(counterWorker.getUsername(),counterWorker);
        return true;
    }

    public boolean remove(String username){

        if(!counterWorkerDAO.containsKey(username)){
            return false;
        }
        counterWorkerDAO.remove(username);
        return true;
    }

    public boolean update(CounterWorker counterWorker){

        if(!counterWorkerDAO.containsKey(counterWorker.getUsername())){
            return insert(counterWorker);
        }
        else{
            this.remove(counterWorker.getUsername());
            this.insert(counterWorker);
            return true;
        }
    }
}
