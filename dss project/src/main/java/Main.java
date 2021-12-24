import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.Equipment;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Manager;
import BusinessLayer.Workers.Technician;
import DataBase.ClientDAO;
import DataBase.WorkersDAO;

public class Main {


    public static void main(String[] args){


        Counter counter1 = new Counter("counter1", "counter1pass", "diogo", "111000111000", "918883276");
        Counter counter2 = new Counter("counter2", "counter2pass", "jele", "000111000111", "911111111");
        Counter counter3 = new Counter("counter3", "counter3pass", "rebelo", "222000222000", "922222222");
        Counter counter4 = new Counter("counter4", "counter4pass", "mateus", "000222000222", "913333333");
        Counter counter5 = new Counter("counter5", "counter5pass", "argonaut", "333000333", "914444444");

        Technician technician1 = new Technician("tech1", "tech1pass", "joao", "444000444", "915555555");
        Technician technician2 = new Technician("tech2", "tech2pass", "rui", "000444000", "916666666");
        Technician technician3 = new Technician("tech3", "tech3pass", "renato", "555000555", "917777777");
        Technician technician4 = new Technician("tech4", "tech4pass", "pedro", "000555000", "918888888");
        Technician technician5 = new Technician("tech5", "tech5pass", "alexandre", "666000666", "919999999");

        Manager manager1 = new Manager("manager1", "manager1pass", "henrique", "000666000", "919191919");
        Manager manager2 = new Manager("manager2", "manager2pass", "beatriz", "777000777", "91344214");
        Manager manager3 = new Manager("manager3", "manager3pass", "ana", "000777000", "912121212");
        Manager manager4 = new Manager("manager4", "manager4pass", "filipa", "888000888", "913131313");
        Manager manager5 = new Manager("manager5", "manager5pass", "maria", "000888000", "914141414");


        WorkersDAO workersdatabase = new WorkersDAO();
        workersdatabase.add(counter1);workersdatabase.add(counter2);workersdatabase.add(counter3);workersdatabase.add(counter4);workersdatabase.add(counter5);
        workersdatabase.add(technician1);workersdatabase.add(technician2);workersdatabase.add(technician3);workersdatabase.add(technician4);workersdatabase.add(technician5);
        workersdatabase.add(manager1);workersdatabase.add(manager2);workersdatabase.add(manager3);workersdatabase.add(manager4);workersdatabase.add(manager5);

        System.out.println("Added " + workersdatabase.get(Hierarchy.COUNTER).size() + " counter workers");
        System.out.println("Added " + workersdatabase.get(Hierarchy.TECHNICIAN).size() + " techs");
        System.out.println("Added " + workersdatabase.get(Hierarchy.MANAGER).size() + " managers");


        Client client1 = new Client("diogo araujo", "999000999", "diogo@gmail.com");
        Client client2 = new Client("diogo rebelo", "000999000", "rebelo@gmail.com");
        Client client3 = new Client("joel araujo", "122122122", "joel@gmail.com");
        Client client4 = new Client("barbara", "211211211", "barbara@gmail.com");
        Client client5 = new Client("suricata", "133133133", "suricata@gmail.com");

        ClientDAO clientsdatabase = new ClientDAO();
        clientsdatabase.add(client1);clientsdatabase.add(client2);clientsdatabase.add(client3);clientsdatabase.add(client4);clientsdatabase.add(client5);
        System.out.println("Added " + clientsdatabase.get().size() + " clients");



    }
}
