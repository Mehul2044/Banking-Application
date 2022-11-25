package Query_Feedback;

import java.sql.Statement;
import java.util.Scanner;

abstract class query_fdb_help implements Query_Fdb {
    private final Statement stmt;

    public Statement getStmt() {
        return stmt;
    }

    public query_fdb_help(Statement stmt) {
        this.stmt = stmt;
    }

    @Override
    public QF_Main take_input() {
        Scanner sc = new Scanner(System.in);
        QF_Main obj = new QF_Main();
        System.out.print("Enter your name:");
        obj.setName(sc.nextLine());
        System.out.print("Enter your Email ID:");
        obj.setEmail_id(sc.nextLine());
        System.out.print("Enter Text here to submit:");
        obj.setText(sc.nextLine());
        return obj;
    }

    abstract void list_all();

    abstract void list_specific();
}
