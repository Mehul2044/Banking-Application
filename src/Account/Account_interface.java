package Account;

import java.sql.SQLException;
import java.sql.Statement;

interface Account_Interface {
    public int getAcc_no();

    public Statement getStmt();

    public String getPassword();

    public String getName();

    public double getBalance();

    public void create();

    public boolean delete(int a) throws SQLException;

    public void display(int b) throws SQLException;
}