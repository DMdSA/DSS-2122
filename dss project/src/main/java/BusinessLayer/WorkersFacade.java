package BusinessLayer;

import BusinessLayer.Client.Client;
import DataBase.ClientDAO;
import DataBase.ProcessingCenterDAO;
import DataBase.WorkersDAO;

public class WorkersFacade implements IWorkers{

    private ClientDAO clients_dao;
    private WorkersDAO workers_dao;
    private ProcessingCenterDAO processingCenter_dao;

    public WorkersFacade(){
    }


    @Override
    public Client getClientByNif(String nif) {
        return clients_dao.getByNif(nif);
    }

    @Override
    public Client getClientByPhone(String phone){
        return clients_dao.getByPhone(phone);
    }


    @Override
    public boolean registerClient() {
        return false;
    }

    @Override
    public boolean updateClient() {
        return false;
    }

    @Override
    public boolean removeClient() {
        return false;
    }

    @Override
    public boolean registerExpressService() {
        return false;
    }

    @Override
    public boolean registerBudgetRequest() {
        return false;
    }

    @Override
    public void consultExpressServices() {

    }

    @Override
    public boolean updateBudgetStatus() {
        return false;
    }

    @Override
    public boolean checkTechAvailability() {
        return false;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public void startSystem() {

    }

    @Override
    public void exitSystem() {

    }
}
