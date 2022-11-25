package Transactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;


public class transactionHistory {
    public static void delete_transaction_history(Statement stmt, String str) throws SQLException {
        stmt.execute("delete from transactions where acc_no = " + str + ";");

        try {
            System.out.println("Transactions history deleted");
        } catch (Exception e) {
            System.out.println("There is no transaction history to delete");
        }

    }

    public static void display_transactions(Statement stmt, String str) throws SQLException {
        ResultSet rst = stmt.executeQuery("select amount, date, transaction_to from transactions where acc_no = " + str + ";");
        int count = 0;
        System.out.println("List of transactions:");
        while (rst.next()) {
            count++;
            try {
                System.out.printf("%-5d Amount:%-30s" + "Date:" + rst.getDate(2) + "        " +
                        "Time:" + rst.getTime(2) + "        Transaction To/From:%s\n", count, rst.getString(1), rst.getString(3));
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (count == 0) {
            System.out.println("No transaction record found.");
        } else {
            System.out.println("Enter 1 for deletion of history\nEnter any other key to Exit");
            Scanner sc = new Scanner(System.in);
            String number = sc.nextLine();
            if (Objects.equals(number, "1")) {
                delete_transaction_history(stmt, str);
            }
        }
    }
}