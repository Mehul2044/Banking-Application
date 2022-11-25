package DateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public interface Date_Time {
    default String getDate_Time() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    static String getDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        return String.valueOf(today);
    }

    static int getDateDiff(String dat) {     //Returns the difference between input date and today in no of days

        LocalDate date2 = LocalDate.parse(dat);
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate tod = LocalDate.now();

        return (int) ChronoUnit.DAYS.between(date2, tod);
    }

    static String getDateMonYear(int m) {

        int yr, mon, day;
        yr = m / 365;
        m = m % 365;
        mon = m / 30;
        m = m % 30;
        day = m;

        if (yr > 0) {
            String s = day + " Days, " + mon + " Months, " + yr + " Years";
            return s;
        }

        if (mon > 0) {
            return day + " Days, " + mon + " Months";
        }

        return m + " Days";

    }
}
