package Loan_FD;

import Colors.ConsoleColors;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class fixed_deposit_main extends fixed_deposit {

    public fixed_deposit_main(Statement stmt, int acc) {
        super(stmt, acc);
    }

    static LFD_Main input() {
        Scanner sc = new Scanner(System.in);
        LFD_Main obj = new LFD_Main();
        while (true) {
            try {
                System.out.print("Enter the amount for FD:");
                obj.setPrincipal_amount(sc.nextDouble());
                break;
            } catch (Exception e) {
                System.out.println("Enter numeric values only.");
                sc.nextLine();
            }
        }
        while (true) {
            try {
                System.out.print("Enter the time you want FD(in years):");
                obj.setYear(sc.nextInt());
                System.out.println("You will get an interest of " + (5 + obj.getYear() / 10.00) + " per year.");
                break;
            } catch (Exception e) {
                System.out.println("Enter integer values only.");
                sc.nextLine();
            }
        }
        return obj;
    }

    public void fd_main() {
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Enter 1 to take new FD\nEnter 2 to check status of your FD\nEnter anything else to exit" + ConsoleColors.RESET);
            Scanner sc = new Scanner(System.in);
            String key = sc.nextLine();
            if (Objects.equals(key, "1")) {
                LFD_Main obj = input();
                obj.setRoi(5 + obj.getYear() / 10.00);
                setObject(obj);
                double a = interest_calculate();
                System.out.println("Your total interest will be:" + a);
                a += obj.getPrincipal_amount();
                System.out.println("Enter 1 to confirm\nEnter anything else to exit");
                if (Objects.equals(sc.nextLine(), "1")) {
                    initialize_new(null, a, obj);
                }
            } else if (Objects.equals(key, "2")) {
                try {
                    ResultSet rst = getStmt().executeQuery("select `fd id`, amount, date_issued, installment_remaining, amount_remaining from fixed_deposits " +
                            "where acc_no = '" + getAcc() + "';");
                    int count = 0;
                    while (rst.next()) {
                        System.out.printf(ConsoleColors.CYAN + "FD ID:%-10d Amount:%-15f Date Issued:%-20s Installments Remaining:%-5d Amount Remaining:%-15f\n"
                                        + ConsoleColors.RESET,
                                rst.getInt(1), rst.getDouble(2), rst.getDate(3), rst.getInt(4),
                                rst.getDouble(5));
                        count++;
                    }
                    if (count == 0) {
                        System.out.println("No active FD in your account.");
                    }
                } catch (Exception e) {
                    System.out.println("Unable to check status...");
                }

            } else {
                break;
            }
        }
    }
}
