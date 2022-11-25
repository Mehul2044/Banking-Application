package Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Admin_login extends User_login {

    public Admin_login(Statement stmt) {
        setStmt(stmt);
    }

    @Override
    public boolean checkAuthentication() throws SQLException {
        input();
        ResultSet rs = getStmt().executeQuery("select password from administrators where s_no = 1;");
        while (rs.next()) {
            if (Objects.equals(getPass(), rs.getString(1))) {
                System.out.println("Successfully logged in!!");
                return true;
            }
        }
        System.out.println("Incorrect password. Login failed. Try again!!");
        return false;
    }
}