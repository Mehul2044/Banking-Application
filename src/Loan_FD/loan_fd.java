package Loan_FD;

import DateTime.Date_Time;

import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.pow;

interface Loan_fd_help {
    double interest_calculate();

    boolean installment_pay(double amount, int acc, int id);

    void initialize_new(String type, double amount_left, LFD_Main obj);
}

abstract class loan_fd implements Loan_fd_help {
    private Statement stmt;

    private int acc;

    private LFD_Main object;

    public LFD_Main getObject() {
        return object;
    }

    public void setObject(LFD_Main object) {
        this.object = object;
    }

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public loan_fd(Statement stmt, int acc) {
        this.stmt = stmt;
        this.acc = acc;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }


    @Override
    public double interest_calculate() {
        double a = object.getPrincipal_amount() * pow((1 + (object.getRoi() / 100.00)), object.getYear());
        return a - object.getPrincipal_amount();
    }


    public void loan_approve(double ROI, String type) {
        LFD_Main obj = Loan_helper.loan_input(ROI);
        setObject(obj);
        double a = interest_calculate();
        System.out.println("Your interest will be:" + a);
        double amount_left = obj.getPrincipal_amount() + a;
        System.out.println("You have to pay an amount of " + amount_left / (obj.getYear() * 12) + " monthly.");
        System.out.println("Enter 1 to confirm\nEnter anything else to go back");
        Scanner sc = new Scanner(System.in);
        if (Objects.equals(sc.nextLine(), "1")) {
            initialize_new(type, amount_left, obj);
        }
    }
}

class LFD_Main implements Date_Time {
    private double principal_amount;
    private final String dateIssued = getDate_Time();
    private double roi;
    private String type_of_loan;
    private int year;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType_of_loan() {
        return type_of_loan;
    }

    public void setType_of_loan(String type_of_loan) {
        this.type_of_loan = type_of_loan;
    }

    public double getPrincipal_amount() {
        return principal_amount;
    }

    public void setPrincipal_amount(double principal_amount) {
        this.principal_amount = principal_amount;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getDateIssued() {
        return dateIssued;
    }

}


