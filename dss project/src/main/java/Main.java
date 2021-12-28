import BusinessLayer.Client.Client;
import BusinessLayer.Equipments.Budget;
import BusinessLayer.Equipments.BudgetStatus;
import BusinessLayer.Equipments.Equipment;
import BusinessLayer.Equipments.ExpressRepair;
import BusinessLayer.ServicesFacade;
import BusinessLayer.Workers.Counter;
import BusinessLayer.Workers.Hierarchy;
import BusinessLayer.Workers.Manager;
import BusinessLayer.Workers.Technician;
import BusinessLayer.WorkersFacade;
import DataBase.ClientDAO;
import DataBase.ExpressServicesDAO;
import DataBase.ProcessingCenterDAO;
import DataBase.WorkersDAO;
import UI.ShopUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException, ClassNotFoundException {


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

        //workersdatabase.WriteObjectToFile();
        //WorkersDAO workersbase2 = WorkersDAO.loadFileToObject();

        System.out.println("Added " + workersdatabase.get(Hierarchy.COUNTER).size() + " counter workers");
        System.out.println("Added " + workersdatabase.get(Hierarchy.TECHNICIAN).size() + " techs");
        System.out.println("Added " + workersdatabase.get(Hierarchy.MANAGER).size() + " managers");


        Client client1 = new Client("diogo araujo", "999000999", "diogo@gmail.com");
        Client client2 = new Client("diogo rebelo", "000999000", "rebelo@gmail.com");
        Client client3 = new Client("joel araujo", "122122122", "joel@gmail.com");
        Client client4 = new Client("barbara", "211211211", "barbara@gmail.com");
        Client client5 = new Client("suricata", "133133133", "suricata@gmail.com");
        Client client6 = new Client("cliente6", "19993123", "cliente6@gmail.com");
        Client client7 = new Client("cliente7", "441312334", "cliente7@gmail.com");

        ClientDAO clientsdatabase = new ClientDAO();
        clientsdatabase.add(client1);clientsdatabase.add(client2);clientsdatabase.add(client3);clientsdatabase.add(client4);clientsdatabase.add(client5);
        clientsdatabase.add(client6);clientsdatabase.add(client7);
        System.out.println("Added " + clientsdatabase.get().size() + " clients\n\n");

        //clientsdatabase.WriteObjectToFile();
        //clients.database.loadFileToObject();


        ExpressRepair er1 = new ExpressRepair("Reparar Ecrã"
                , "Trocamos o ecrã do teu Smartphone"
                , Arrays.asList("Samsung", "iPhone", "Huawai", "Xiaomi"), 87.33);

        ExpressRepair er2 = new ExpressRepair("Reparar Bateria"
                , "Reparamos a bateria do teu Smartphone"
                , Arrays.asList("Samsung", "iPhone", "Huawai", "Xiaomi"), 44.49);

        ExpressRepair er3 = new ExpressRepair("Reparar Botão"
                , "Trocamos/Reparamos qualquer botão do teu Smartphone"
                , Arrays.asList("Samsung", "iPhone", "Huawai", "Xiaomi"), 29.99);

        ExpressRepair er4 = new ExpressRepair("Reparar Altifalante"
                , "Reparamos/Aperfeiçoamos o(s) altifalante(s) do teu Smartphone"
                , Arrays.asList("Samsung", "iPhone", "Huawai", "Xiaomi"), 34.59);

        ExpressRepair er5 = new ExpressRepair("Melhoria de desempenho"
                , "Atualizamos e reinstalamos o SO e instalamos discos SSD para melhorar o desempenho do teu Computador"
                , List.of("Qualquer Computador"), 69.88);

        ExpressRepair er6 = new ExpressRepair("Segurança"
                , "Detetamos e/ou removemos os vírus do teu Computador (garantimos que não perdes os teus ficheiros com backup de dados)"
                , List.of("Qualquer Computador"), 49.49);

        ExpressRepair er7 = new ExpressRepair("Descalcificar Máquinas de Café"
                , "Limpamos as tubagens com eliminação de calcário e outras impurezas da tua máquina de café"
                , Arrays.asList("Delta", "Expresso"), 19.99);

        List<ExpressRepair> expresslist = new ArrayList<>();
        expresslist.add(er1);expresslist.add(er2);expresslist.add(er3);expresslist.add(er4);expresslist.add(er5);
        expresslist.add(er6);expresslist.add(er7);
        ExpressServicesDAO expressservicesdao = new ExpressServicesDAO(expresslist);
        System.out.println("Added " + expressservicesdao.size() + "express repair services\n\n");


        Budget budget1 = new Budget("999000999", new Equipment("Forno","999000999","counter1"));
        Budget budget2 = new Budget("999000999", new Equipment("Forno2","999000999","counter2"));
        ProcessingCenterDAO processingCenterDAO = new ProcessingCenterDAO();
        processingCenterDAO.add(budget1);processingCenterDAO.add(budget2);
        System.out.println(processingCenterDAO.size(BudgetStatus.WITHOUT_BUDGET));

        // debug purposes only------
        WorkersFacade wf = new WorkersFacade(clientsdatabase, workersdatabase);
        ServicesFacade sf = new ServicesFacade(processingCenterDAO, expressservicesdao);
        ShopUI shop = new ShopUI(wf, sf);
        // ------------------------
        shop.run();
    }
}
