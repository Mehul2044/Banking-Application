package Login;

import java.sql.SQLException;


interface login{
    void input();
    boolean checkAuthentication() throws SQLException;
}
