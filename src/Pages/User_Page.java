package Pages;

import Account.Account_Main;
import Account.Edit_Account_Details;
import Loan_FD.Loan_main;
import Loan_FD.fixed_deposit_main;
import Transactions.Transaction_InterAccounts;
import Transactions.Withdraw_Deposit;
import Transactions.transactionHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class User_Page {
    public static void print() {
        System.out.println("""
                Enter 1 to view account details
                Enter 2 to edit account details
                Enter 3 to withdraw/deposit
                Enter 4 to send money
                Enter 5 to delete account
                Enter 6 to view and Opt for Loan
                Enter 7 to view and Opt for FD
                Enter anything else to exit""");
    }

    public static void display_account_info(Statement stmt, String str) throws SQLException {
        Account_Main obj = new Account_Main(stmt);
        obj.display(Integer.parseInt(str));
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to show transactions\nEnter any other to exit");
        String key = sc.nextLine();
        if (Objects.equals(key, "1")) {
            transactionHistory.display_transactions(stmt, str);
        }
    }

    public static boolean page(Statement stmt, String str) throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            print();
            String key = sc.nextLine();
            if (Objects.equals(key, "1")) {
                display_account_info(stmt, str);
            } else if (Objects.equals(key, "2")) {
                Edit_Account_Details obj = new Edit_Account_Details(stmt, str);
                System.out.println("Enter 1 to change name\nEnter 2 to change password");
                String key2 = sc.nextLine();
                if (Objects.equals(key2, "1")) {
                    obj.change_name();
                } else if (Objects.equals(key2, "2")) {
                    obj.change_pass();
                } else {
                    System.out.println("Enter the correct key value!!");
                }
            } else if (Objects.equals(key, "3")) {
                Withdraw_Deposit obj = new Withdraw_Deposit(stmt, str);
                System.out.println("Enter 1 to deposit\nEnter 2 to withdraw\nEnter anything else to cancel");
                String key2 = sc.nextLine();
                if (Objects.equals(key2, "1")) {
                    System.out.print("Enter amount to be deposited:");
                    double amount;
                    try {
                        amount = sc.nextDouble();
                    } catch (Exception e) {
                        System.out.println("Failed! Enter numeric values!");
                        sc.nextLine();
                        continue;
                    }
                    sc.nextLine();
                    if (obj.deposit(amount, "SELF")) {
                        System.out.println("Amount deposited successfully");
                    } else {
                        System.out.println("Deposit failed.");
                    }
                } else if (Objects.equals(key2, "2")) {
                    ResultSet rst = stmt.executeQuery("select password, balance from account where acc_no =" + str + ";");
                    String pass = null;
                    double balance = 0;
                    while (rst.next()) {
                        pass = rst.getString(1);
                        balance = rst.getDouble(2);
                    }
                    System.out.print("Enter password:");
                    if (Objects.equals(sc.nextLine(), pass)) {
                        System.out.print("Enter the amount to be withdrawn:");
                        double amount;
                        try {
                            amount = sc.nextDouble();
                        } catch (Exception e) {
                            System.out.println("Failed! Enter numeric values!");
                            sc.nextLine();
                            continue;
                        }
                        sc.nextLine();
                        if (obj.withdraw(amount, "SELF")) {
                            System.out.println("Amount successfully withdrawn. Remaining balance is:" + (balance - amount));
                        } else {
                            System.out.println("Insufficient balance. Try taking a loan...");
                        }

                    } else {
                        System.out.println("Incorrect password. Withdrawal failed.");
                    }
                }
            } else if (Objects.equals(key, "4")) {
                Transaction_InterAccounts.doTransaction(stmt, str);
                ResultSet rst = stmt.executeQuery("select balance from account where acc_no = '" + str + "';");
                while (rst.next()) {
                    System.out.println("Remaining balance is:" + rst.getDouble(1));
                }
            } else if (Objects.equals(key, "5")) {
                Account_Main obj1 = new Account_Main(stmt);
                return obj1.delete(Integer.parseInt(str));
            } else if (Objects.equals(key, "6")) {
                Loan_main obj = new Loan_main(stmt, Integer.parseInt(str));
                obj.loan_main();
            } else if (Objects.equals(key, "7")) {
                fixed_deposit_main obj = new fixed_deposit_main(stmt, Integer.parseInt(str));
                obj.fd_main();
            } else {
                System.out.println("Logout successful!");
                return false;
            }
        }
    }
}
