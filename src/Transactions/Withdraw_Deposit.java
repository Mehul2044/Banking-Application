package Transactions;

import Account.Account_Main;
import DateTime.Date_Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Withdraw_Deposit extends Account_Main implements Date_Time {

    private double amount;
    private String whichPerson;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getWhichPerson() {
        return whichPerson;
    }

    public void setWhichPerson(String whichPerson) {
        this.whichPerson = whichPerson;
    }

    public Withdraw_Deposit(Statement stmt, String str) {
        super(stmt);
        setAcc_no(Integer.parseInt(str));
    }

    public boolean withdraw(double amount, String whichPerson) throws SQLException {
        setAmount(amount);
        setWhichPerson(whichPerson);
        ResultSet rst = getStmt().executeQuery("select balance from account where acc_no = " + getAcc_no() + ";");
        double balance = 0;
        while (rst.next()) {
            balance = rst.getInt(1);
        }
        if (balance < getAmount()) {
            return false;
        }
        getStmt().execute("update account set balance = balance - '" + getAmount() + "' where acc_no =" + getAcc_no() + ";");
        getStmt().execute("insert into transactions " +
                "select account.acc_no, '-" + getAmount() + "','" + getDate_Time() + "', '" + getWhichPerson() + "' from account where account.acc_no = '" + getAcc_no() + "' LIMIT 1;");
        return true;
    }

    public boolean deposit(double amount, String whichPerson) {
        setAmount(amount);
        setWhichPerson(whichPerson);
        try {
            getStmt().execute("update account set balance = balance + '" + getAmount() + "' where acc_no =" + getAcc_no() + ";");
            getStmt().execute("insert into transactions " +
                    "select account.acc_no, '+" + getAmount() + "','" + getDate_Time() + "', '" + getWhichPerson() + "' from account where account.acc_no = '" + getAcc_no() + "' LIMIT 1;");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }


}
