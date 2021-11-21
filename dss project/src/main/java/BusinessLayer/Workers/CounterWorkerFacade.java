package BusinessLayer.Workers;
import BusinessLayer.ICounterWorker;

public class CounterWorkerFacade implements ICounterWorker{


    BusinessLayer.Workers.CounterWorker cw;

    @Override
    public boolean login(String user, String password) {



        return true;
    }
}
