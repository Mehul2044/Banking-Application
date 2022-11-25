package Pages;

import Account.Account_Main;
import Connection.Connection_establish;
import Loan_FD.Loan_main;
import Loan_FD.fixed_deposit;
import Query_Feedback.Feedback_main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Admin_page {

    public static void info_account(Statement stmt) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the account number:");
        String str = sc.nextLine();
        Account_Main obj = new Account_Main(stmt);
        obj.display(Integer.parseInt(str));
        ResultSet rst = stmt.executeQuery("select * from loans NATURAL JOIN account where acc_no = '" + str + "';");
        int count = 0;
        while (rst.next()) {
            count++;
        }
        if (count != 0) {
            System.out.println("The customer have active loans. Press 1 to view them\nAnything else to exit");
            if (Objects.equals(sc.nextLine(), "1")) {
                Loan_main.display(stmt, Integer.parseInt(str));
            }
        }
    }

    public static void info_all(Statement stmt) throws SQLException {
        ResultSet rst = stmt.executeQuery("select acc_no, customer_name,balance from account;");
        while (rst.next()) {
            System.out.printf("Account Number:%-10d  Name:%-30s   Balance = %f\n", rst.getInt(1), rst.getString(2), rst.getDouble(3));
        }
    }

    public static void view_fd(Statement stmt) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to pay monthly installment of Fixed Deposit to accounts\nEnter 2 to view current pending FDs\nEnter anything else to exit");
        String key = sc.nextLine();
        if (Objects.equals(key, "1")) {
            double sum = 0;
            int count = 0;
            ResultSet rs = stmt.executeQuery("select * from fixed_deposits;");
            while (rs.next()) {
                int acc = rs.getInt(2);
                double amount = rs.getDouble(6) / rs.getInt(5);
                sum += amount;
                int id = rs.getInt(1);
                Statement stmt2 = Connection_establish.dbms_connect();
                fixed_deposit obj = new fixed_deposit(stmt2, acc);
                if (!obj.installment_pay(amount, acc, id)) {
                    count++;
                    System.out.println("Total of " + sum + " was paid to " + count + " unique FDs.");
                    return;
                }
                count++;
            }
            if (count == 0) {
                System.out.println("No active FDs in the bank!!");
            } else {
                System.out.println("Total of " + sum + " was paid to " + count + " unique FDs.");
            }
        } else if (Objects.equals(key, "2")) {
            ResultSet rst = stmt.executeQuery("select * from fixed_deposits;");
            int count = 0;
            while (rst.next()) {
                System.out.printf("FD ID:%-10d Account Number:%-10d Amount:%-15f Date Issued:%-20s Installments Remaining:%-5d Amount Remaining:%-15f\n",
                        rst.getInt(1), rst.getInt(2), rst.getDouble(3), rst.getDate(4), rst.getInt(5),
                        rst.getDouble(6));
                count++;
            }
            if (count == 0) {
                System.out.println("No active FDs in the bank!!");
            }
        }
    }

    public static void solve_query(Statement stmt) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter Query ID:");
            try {
                int a = sc.nextInt();
                int count = 0;
                try {
                    ResultSet rst = stmt.executeQuery("select Status from  Query where `Query ID` = '" + a + "';");
                    while (rst.next()) {
                        if (Objects.equals(rst.getString(1), "Resolved")) {
                            System.out.println("Query already resolved.");
                        } else {
                            stmt.execute("update Query set Status = 'Resolved' where `Query ID` = '" + a + "';");
                            System.out.println("Query resolved successfully.");
                            return;
                        }
                        count++;
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                if (count == 0) {
                    System.out.println("No matching query ID!!");
                }
                break;
            } catch (Exception e) {
                System.out.println("Enter numeric values only.");
                sc.nextLine();
            }
        }
    }

    public static void display_resultSet(ResultSet rst, String status) throws SQLException {
        int count = 0;
        while (rst.next()) {
            System.out.printf("Query ID:%-10d Problem:%-100s Date Submitted:%-10s\n",
                    rst.getInt(1), rst.getString(5), rst.getDate(6));
            count++;
        }
        if (count == 0) {
            System.out.println("No " + status + " Query to show!");
        }
    }

    public static void view_admin_queries(Statement stmt) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Press 1 to view Unresolved Queries\nPress 2 to view Resolved Queries\nPress 3 to solve a query\nPress anything else for exit");
            String key = sc.nextLine();
            if (Objects.equals(key, "1")) {
                try {
                    ResultSet rst = stmt.executeQuery("select * from Query where Status = 'Unresolved';");
                    display_resultSet(rst, "Unresolved");
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(key, "2")) {
                try {
                    ResultSet rst = stmt.executeQuery("select * from Query where Status = 'Resolved';");
                    display_resultSet(rst, "Resolved");
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(key, "3")) {
                solve_query(stmt);
            } else {
                break;
            }
        }
    }

    public static boolean page(Statement stmt) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    Enter 1 view information of a account
                    Enter 2 to view information of all accounts
                    Enter 3 to view and pay FD installments
                    Enter 4 to view Queries
                    Enter 5 to view Feedback
                    Enter any other to go back and logout""");
            String key = sc.nextLine();
            if (Objects.equals(key, "1")) {
                info_account(stmt);
            } else if (Objects.equals(key, "2")) {
                info_all(stmt);
            } else if (Objects.equals(key, "3")) {
                view_fd(stmt);
            } else if (Objects.equals(key, "4")) {
                view_admin_queries(stmt);
            } else if (Objects.equals(key, "5")) {
                Feedback_main obj = new Feedback_main(stmt);
                while (true) {
                    System.out.println("Enter 1 to view Feedback sorted by rating\nEnter 2 to view all Feedbacks\nEnter anything else to exit");
                    String key2 = sc.nextLine();
                    if (Objects.equals(key2, "1")) {
                        obj.list_specific();
                    } else if (Objects.equals(key2, "2")) {
                        obj.list_all();
                    } else {
                        break;
                    }
                }
            } else {
                System.out.println("Successfully logged out!!");
                return false;
            }
        }
    }
}
