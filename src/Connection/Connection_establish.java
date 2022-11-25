package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection_establish {

    public static Statement dbms_connect() throws ClassNotFoundException, SQLException {

        // set your username, password and database name here to connect to the local MySQL database
        String user = "root";
        String password = "Root@123";
        String database_name = "banking_database";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database_name, user, password);
        return con.createStatement();
    }
}
