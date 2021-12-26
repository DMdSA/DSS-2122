package BusinessLayer;

import BusinessLayer.Client.Client;
import BusinessLayer.Services.Service;
import BusinessLayer.Workers.Hierarchy;
import DataBase.ClientDAO;
import DataBase.ProcessingCenterDAO;
import DataBase.WorkersDAO;

import java.util.Map;

public class WorkersFacade implements IWorkers{

    /**
     * Instance Variables
     */
    private ClientDAO clients_dao;
    private WorkersDAO workers_dao;
    private ProcessingCenterDAO processingCenter_dao;


    public WorkersFacade(){
        //todo
    }

    public WorkersFacade(ClientDAO clientdao, WorkersDAO workersdao, ProcessingCenterDAO processingCenterdao){
        this.clients_dao = clientdao;
        this.workers_dao = workersdao;
        this.processingCenter_dao = processingCenterdao;
    }

    
    /**
     * CLIENTS ------------------
     */
    @Override
    public Client getClientByNif(String nif) {
        return clients_dao.getByNif(nif);
    }

    @Override
    public Client getClientByPhone(String phone){
        return clients_dao.getByPhone(phone);
    }

    @Override
    public boolean registerClient(Client c) {

        if((this.clients_dao.getByNif(c.getNIF()) != null)) {
            System.out.println("error: Client already exists");
            return false;
        }
        this.clients_dao.add(c.clone());
        return true;
    }

    @Override
    public boolean hasClient(String nif){
        return this.clients_dao.hasClient(nif);
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
    public boolean hasClients(){
        return this.clients_dao.getSize() > 0;
    }

    @Override
    public ClientDAO getClients(){
        return this.clients_dao.clone();     // clone? todo
    }

    /**
     * COUNTER ------------------
     */

    @Override
    public boolean hasWorkers(){
        return (this.workers_dao.getSize() > 0);
    }

    @Override
    public boolean hasWorkers(Hierarchy h){

        return (this.workers_dao.get(h).size() > 0);
    }

    @Override
    public boolean registerExpressService() {
        return false;
    }

    @Override
    public boolean registerBudgetRequest(Service s, String user) {

        this.processingCenter_dao.add(s.getBudget());
        if(this.workers_dao.get(Hierarchy.COUNTER, user) != null){

            this.workers_dao.update(this.workers_dao.get(Hierarchy.COUNTER, user))
        }

        return true;
    }

    @Override
    public void consultExpressServices() {
    }

    @Override
    public boolean hasExpressServices(){
        return false;
        // TODO TODO TODO
    }

    @Override
    public boolean consultBudgetRequests(){
        return false;
        // TODO TODO TODO
        // um COUNTER_WORKER só deve atualizar de "sem budget" -> aceite / não aceite
        //                                     de "à espera de entrega" -> entregue
    }

    @Override
    public boolean hasBudgetRequests(){

        return false;
        //TODO TODO TODO
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
