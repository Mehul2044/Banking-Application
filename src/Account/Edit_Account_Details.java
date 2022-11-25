package Account;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Edit_Account_Details extends Account_Main {

    public Edit_Account_Details(Statement stmt, String str) {
        super(stmt);
        setAcc_no(Integer.parseInt(str));
    }

    public void change_name() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your new name:");
        setName(sc.nextLine());
        getStmt().execute("update account set customer_name = '" + getName() + "' where acc_no = '" + getAcc_no() + "';");
        System.out.println("Name changed successfully.");
    }

    public void change_pass() throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the new password:");
            String pass = sc.nextLine();
            System.out.print("Reconfirm the password:");
            if (Objects.equals(sc.nextLine(), pass)) {
                getStmt().execute("update account set password = '" + pass + "' where acc_no = '" + getAcc_no() + "'");
                System.out.println("Password updated successfully.");
                break;
            } else {
                System.out.println("Passwords didn't match. Try again.");
            }
        }
    }
}
