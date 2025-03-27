package bank.management.system;

import javax.swing.plaf.nimbus.State;
import java.sql.*;


public class Conn {

    Connection connection;
    Statement statement;

    public Conn() {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankSystem", "root", "CodingCulprit123#");
            statement = connection.createStatement();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
