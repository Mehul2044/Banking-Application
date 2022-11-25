package Query_Feedback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Query_main extends query_fdb_help {
    public Query_main(Statement stmt) {
        super(stmt);
    }

    @Override
    void list_all() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name:");
        String name = sc.nextLine();
        System.out.print("Enter your Email Id:");
        String email = sc.nextLine();
        try {
            ResultSet rst = getStmt().executeQuery("select * from Query where Name = '" + name + "' and Email_ID = '" + email + "';");
            int count = 0;
            while (rst.next()) {
                System.out.printf("Query ID:%-10d Problem:%-100s Status:%-20s Date Submitted:%-10s\n",
                        rst.getInt(1), rst.getString(5), rst.getString(4), rst.getDate(6));
                count++;
            }
            if (count == 0) {
                System.out.println("You have no queries!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    void list_specific() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Query ID:");
        String id = sc.nextLine();
        try {
            int count = 0;
            ResultSet rs = getStmt().executeQuery("select Status, Problem, date from Query where `Query ID` = '" + id + "';");
            while (rs.next()) {
                System.out.println("Problem:" + rs.getString(2) + "\nStatus:" + rs.getString(1) + "\nDate Submitted:" + rs.getDate(3));
                count++;
            }
            if (count == 0) {
                System.out.println("Query ID doesn't match!");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void submit(QF_Main obj) {
        try {
            getStmt().execute("insert into Query (Name, Email_ID, Problem, date) values ('" + obj.getName() + "', '" + obj.getEmail_id() + "', " +
                    "'" + obj.getText() + "', '" + obj.get_time() + "');");
            System.out.println("Query submitted successfully.");
            ResultSet rst = getStmt().executeQuery("select `Query ID` from Query where `Query ID` = (select max(`Query ID`) from Query)");
            rst.next();
            System.out.println("Your Query ID generated is:" + rst.getInt(1) + ". Please take a note of it.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void main() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    Enter 1 to view Query by Query ID
                    Enter 2 to view all Queries submitted by you
                    Enter 3 to submit a new Query
                    Enter anything else to exit""");
            String key = sc.nextLine();
            if (Objects.equals(key, "1")) {
                list_specific();
            } else if (Objects.equals(key, "2")) {
                list_all();
            } else if (Objects.equals(key, "3")) {
                QF_Main obj = take_input();
                submit(obj);
            } else {
                break;
            }
        }
    }
}
