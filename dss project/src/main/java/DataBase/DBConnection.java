package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "localhost";
    private static final String DBASE = "gestaocentroreparacao";
    private static final String USERNAME = "dss_sb";
    private static final String PASSWORD = "dss2021";
    private boolean connected = false;

    private static Connection connection;

    /**
     * Create a connection to database
     */
    public static void createConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + URL + "/" + DBASE, USERNAME, PASSWORD);
                                        // jdbc:subprotocol:subname

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     */
    public static Connection getConnection(){
        return connection;
    }

}
