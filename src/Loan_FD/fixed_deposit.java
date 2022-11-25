package Loan_FD;

import Transactions.Withdraw_Deposit;
import DateTime.Date_Time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class fixed_deposit extends loan_fd {

    public fixed_deposit(Statement stmt, int acc) {
        super(stmt, acc);
    }

    @Override
    public boolean installment_pay(double amount, int acc, int fd_id) {
        try {
            Withdraw_Deposit obj = new Withdraw_Deposit(getStmt(), String.valueOf(acc));
            obj.deposit(amount, "FD INSTALLMENT");
            getStmt().execute("update fixed_deposits " +
                    "set amount_remaining = amount_remaining - '" + amount + "'" +
                    "where `fd id` = '" + fd_id + "';");
            getStmt().execute("update fixed_deposits " +
                    "set installment_remaining = fixed_deposits.installment_remaining - 1 " +
                    "where `fd id` = '" + fd_id + "';");
            ResultSet rst = getStmt().executeQuery("select amount_remaining, installment_remaining from fixed_deposits where `fd id` = '" + fd_id + "'");
            while (rst.next()) {
                if (rst.getInt(2) == 0) {
                    System.out.println("FD with ID:" + fd_id + " is fully completed.");
                    getStmt().execute("delete from fixed_deposits where `installment_remaining` = 0;");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public void initialize_new(String type, double amount_left, LFD_Main obj) {
        try {
            Withdraw_Deposit withdraw = new Withdraw_Deposit(getStmt(), String.valueOf(getAcc()));
            if (!withdraw.withdraw(obj.getPrincipal_amount(), "FD AMOUNT")) {
                System.out.println("Insufficient balance for Fixed Deposit!!");
                return;
            }
            getStmt().execute("insert into fixed_deposits (acc_no, amount, date_issued, installment_remaining, amount_remaining) " +
                    "values(" + getAcc() + ", " + obj.getPrincipal_amount() +
                    ", '" + obj.getDate_Time() + "', " + obj.getYear() * 12 + ", '" + amount_left + "');");
            System.out.println("FD amount successfully withdrawn from your bank account.");
        } catch (SQLException e) {
            System.out.println("Unable to initialize a new new fixed deposit..");
        }
    }
}
