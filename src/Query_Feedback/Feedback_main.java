package Query_Feedback;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Feedback_main extends query_fdb_help {

    public Feedback_main(Statement stmt) {
        super(stmt);
    }

    public static String input_rating() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter your rating(1 for poor, 2 for average, 3 for good):");
            try {
                String key = sc.nextLine();
                if (Objects.equals(key, "1")) {
                    return "POOR";
                } else if (Objects.equals(key, "2")) {
                    return "AVERAGE";
                } else if (Objects.equals(key, "3")) {
                    return "GOOD";
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Please enter value according to instruction.");
            }
        }
    }

    @Override
    public void submit(QF_Main obj) {
        String rating = input_rating();
        try {
            getStmt().execute("insert into Feedback values ('" + obj.getName() + "', '" + obj.getEmail_id() + "', '" +
                    "" + obj.getText() + "',  '" + rating + "',  '" + obj.get_time() + "');");
            System.out.println("Feedback submitted successfully. Thanks for giving us your valuable feedback.");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void list_all() {
        try {
            ResultSet rs = getStmt().executeQuery("select * from Feedback;");
            int count = 0;
            while (rs.next()) {
                System.out.printf("Name:%-30s Email ID:%-30s Text:%-100s Rating:%-10s Date Submitted:%-10s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                count++;
            }
            if (count == 0) {
                System.out.println("No feedback available.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void list_specific() {
        String rating = input_rating();
        try {
            ResultSet rst = getStmt().executeQuery("select * from Feedback where rating = '" + rating + "';");
            int count = 0;
            while (rst.next()) {
                System.out.printf("Name:%-30s Email ID:%-30s Text:%-100s Date Submitted:%-10s\n",
                        rst.getString(1), rst.getString(2), rst.getString(3), rst.getDate(5));
                count++;
            }
            if (count == 0) {
                System.out.println("No feedback with " + rating + " rating found!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

