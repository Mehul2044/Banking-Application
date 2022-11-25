package Account;

import Login.User_login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

abstract class Account implements Account_Interface {
    private String password;
    private String name;
    private double balance;
    private Statement stmt;
    private int acc_no;

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

public class Account_Main extends Account {
    public void create() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name:");
        setName(sc.nextLine());
        System.out.print("Enter the password:");
        setPassword(sc.nextLine());
        System.out.print("Enter the initial amount to be deposited:");
        while (true) {
            while (true) {
                try {
                    setBalance(sc.nextDouble());
                    break;
                } catch (Exception e) {
                    System.out.print("Enter numeric values only. Try again:");
                    sc.nextLine();
                }
            }
            if (getBalance() < 2000) {
                System.out.print("Initial deposit can only be greater than 2000. Enter the new amount:");
            } else {
                break;
            }
        }
        try {
            String sql = "insert into account(customer_name, password, balance) values ('" + getName() + "', '" + getPassword() + "', '" + getBalance() + "');";
            getStmt().execute(sql);
            System.out.println("Account successfully added.");
            ResultSet st = getStmt().executeQuery("select acc_no from account where acc_no = (select max(acc_no) from account);");
            System.out.print("The account number is:");
            while (st.next()) {
                System.out.println(st.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("ERROR!! Could not create an account.");
        }
    }

    public boolean delete(int acc_no) throws SQLException {
        Scanner sc = new Scanner(System.in);
        setAcc_no(acc_no);
        User_login obj = new User_login(getStmt(), String.valueOf(acc_no));
        if (obj.checkAuthentication()) {
            System.out.println("Enter 1 to confirm deletion\nEnter anything else to exit");
            if (Objects.equals(sc.nextLine(), "1")) {
                try {
                    getStmt().execute("delete from account where acc_no = '" + getAcc_no() + "';");
                    System.out.println("Account deleted successfully.");
                    return false;
                } catch (SQLException e) {
                    System.out.println(e);
                    return true;
                }
            }
        }
        return true;
    }

    public void display(int acc) throws SQLException {
        ResultSet rst = getStmt().executeQuery("select acc_no, customer_name, balance from account where acc_no = '" + acc + "';");
        while (rst.next()) {
            System.out.printf("Account number:%d\nName:%s\nBalance:%.2f\n", rst.getInt(1), rst.getString(2), rst.getDouble(3));
        }
    }

    public Account_Main(Statement stmt) {
        setStmt(stmt);
    }
}
