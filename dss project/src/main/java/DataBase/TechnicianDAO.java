package DataBase;

import BusinessLayer.Workers.Technician;

import java.util.HashMap;
import java.util.Map;

public class TechnicianDAO extends DAOClass{


    public Map<String, Technician> technicianDAO = new HashMap<>();


    public Technician get(String username){

        return technicianDAO.get(username);
    }


    public boolean insert(Technician technician){

        if(technicianDAO.containsKey(technician.getUsername())){
            return false;
        }
        technicianDAO.put(technician.getUsername(),technician);
        return true;
    }

    public boolean remove(String username){

        if(!technicianDAO.containsKey(username)){
            return false;
        }
        technicianDAO.remove(username);
        return true;
    }

    public boolean update(Technician technician){

        if(!technicianDAO.containsKey(technician.getUsername())){
            return insert(technician);
        }
        else{
            this.remove(technician.getUsername());
            this.insert(technician);
            return true;
        }
    }
}
