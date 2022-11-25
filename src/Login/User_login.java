package Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class User_login implements login {
    private Statement stmt;
    private String pass;
    private String acc_no;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public User_login(Statement stmt, String acc_no) {
        setStmt(stmt);
        setAcc_no(acc_no);
    }

    User_login() {
    }

    @Override
    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the password:");
        setPass(sc.nextLine());
    }

    @Override
    public boolean checkAuthentication() throws SQLException {
        input();
        ResultSet rs = stmt.executeQuery("select * from account where acc_no = '" + getAcc_no() + "' AND password = '" + getPass() + "'");
        int counter = 0;
        while (rs.next()) {
            counter++;
        }
        if (counter == 0) {
            System.out.println("Login failed.");
            System.out.println("Try again!!");
            return false;
        }
        System.out.println("Details matched. Taking you to the next page...");
        return true;
    }
}
