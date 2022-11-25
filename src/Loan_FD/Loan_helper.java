package Loan_FD;

import Transactions.Withdraw_Deposit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Loan_helper extends loan_fd {
    public Loan_helper(Statement stmt, int acc) {
        super(stmt, acc);
    }

    public static void print() {
        System.out.println("""
                Press 1 to take Home loan(ROI = 8%)
                Press 2 to take Education Loan(ROI = 6.5%)
                Press 3 to take Personal Loan(ROI 8.5%)
                Press 4 to take Car Loan(ROI 7%)
                Press anything else for exit""");
    }

    public static LFD_Main loan_input(double ROI) {
        Scanner sc = new Scanner(System.in);
        LFD_Main obj = new LFD_Main();
        obj.setRoi(ROI);
        System.out.print("Enter the amount you want to take loan:");
        while (true) {
            try {
                obj.setPrincipal_amount(sc.nextDouble());
                break;
            } catch (Exception e) {
                System.out.println("Enter numeric values only.");
                sc.nextLine();
            }
        }
        System.out.print("Enter the amount of time in years you want to take loan:");
        while (true) {
            try {
                obj.setYear(sc.nextInt());
                break;
            } catch (Exception e) {
                System.out.println("Enter numeric values only.");
                sc.nextLine();
            }
        }
        return obj;
    }

    @Override
    public boolean installment_pay(double amount, int acc, int loan_id) {
        try {
            Withdraw_Deposit obj = new Withdraw_Deposit(getStmt(), String.valueOf(acc));
            if (!obj.withdraw(amount, "BANK(FOR LOAN)")) {
                System.out.println("Insufficient balance. Try later!");
            }
            getStmt().execute("update loans " +
                    "set `amount left` = `amount left` - '" + amount + "'" +
                    "where loan_id = '" + loan_id + "';");
            getStmt().execute("update loans " +
                    "set `installment remaining` = `installment remaining` - 1 " +
                    "where loan_id = '" + loan_id + "';");
            System.out.println("Loan installment paid successfully.");
            ResultSet rst = getStmt().executeQuery("select `amount left`, `installment remaining` from loans where loan_id = '" + loan_id + "'");
            rst.next();
            System.out.println("Remaining amount to be paid:" + rst.getDouble(1) + "\nInstallments remaining:" + rst.getInt(2));
            if (rst.getInt(2) == 0) {
                System.out.println("Loan amount completed. Thank You for paying us timely!!");
                getStmt().execute("delete from loans where `installment remaining` = 0;");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public void initialize_new(String type, double amount_left, LFD_Main obj) {
        try {
            getStmt().execute("insert into loans (acc_no, loan_amount, date_issued, `loan type`, `amount left`, `installment remaining`) " +
                    "values(" + getAcc() + ", " + obj.getPrincipal_amount() +
                    ", '" + obj.getDate_Time() + "', '" + type + "', " + amount_left + ", '" + obj.getYear() * 12 + "');");
            Withdraw_Deposit dep = new Withdraw_Deposit(getStmt(), String.valueOf(getAcc()));
            dep.deposit(obj.getPrincipal_amount(), "LOAN AMOUNT");
            System.out.println("Loan approved successfully. Amount deposited in your bank.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Loan_take() {
        print();
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        if (Objects.equals(key, "1")) {
            loan_approve(8, "HOME");
        } else if (Objects.equals(key, "2")) {
            loan_approve(6.5, "EDUCATION");
        } else if (Objects.equals(key, "3")) {
            loan_approve(8.5, "PERSONAL");
        } else if (Objects.equals(key, "4")) {
            loan_approve(7, "CAR");
        }
    }

}
