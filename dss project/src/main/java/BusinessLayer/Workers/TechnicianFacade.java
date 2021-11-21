package BusinessLayer.Workers;

import BusinessLayer.ITechnician;

public class TechnicianFacade implements ITechnician {

    BusinessLayer.Workers.CounterWorker cw;

    @Override
    public boolean login(String user, String password) {



        return true;
    }
}
