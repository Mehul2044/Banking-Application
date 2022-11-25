package Transactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Transaction_InterAccounts {
    public static void doTransaction(Statement stmt, String str) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the password:");
        ResultSet rst = stmt.executeQuery("select password from account where acc_no = '" + str + "';");
        while (rst.next()) {
            if (!Objects.equals(sc.nextLine(), rst.getString(1))) {
                System.out.println("Incorrect Password. Please try again!!");
                return;
            }
        }
        System.out.print("Enter the account number you want to do transaction for:");
        String str2 = sc.nextLine();
        System.out.print("Enter the amount:");
        double amount = sc.nextDouble();
        Withdraw_Deposit obj = new Withdraw_Deposit(stmt, str);
        boolean flag = obj.withdraw(amount, str2);
        if (flag) {
            Withdraw_Deposit obj2 = new Withdraw_Deposit(stmt, str2);
            obj2.deposit(amount, str);
            System.out.println("Transaction done successfully.");
        }else {
            System.out.println("Transaction failed! Check whether your account has sufficient balance.");
        }
    }
}
