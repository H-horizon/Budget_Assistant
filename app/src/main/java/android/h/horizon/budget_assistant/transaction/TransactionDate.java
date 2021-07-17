package android.h.horizon.budget_assistant.transaction;

import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class consists of method that can be used to retrieve information for sorting
 * on transactions' dates
 */
public class TransactionDate {
    private static final String TAG = "TransactionDate";

    /**
     * checks whether or not both parameters are on the same day
     *
     * @param searchValue is the first date
     * @param targetValue is the second date
     * @return true if consider is satisfied
     */
    public static boolean isToday(Date searchValue, Date targetValue) {
        Log.d(TAG, "isToday(Date searchValue, Date targetValue) called");
        try {
            if (isWeekSame(searchValue, targetValue)) {
                return getDay(searchValue) == getDay(targetValue);
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    /**
     * checks whether or not both parameters are on the same week
     *
     * @param searchValue is the first date
     * @param targetValue is the second date
     * @return true if consider is satisfied
     */
    public static boolean isWeekSame(Date searchValue, Date targetValue) {
        Log.d(TAG, "isWeekSame(Date searchValue, Date targetValue) called");
        if (isMonthSame(searchValue, targetValue)) {
            return getWeek(searchValue) == getWeek(targetValue);
        }
        return false;
    }

    /**
     * checks whether or not both parameters are on the same month
     *
     * @param searchValue is the first date
     * @param targetValue is the second date
     * @return true if consider is satisfied
     */
    public static boolean isMonthSame(Date searchValue, Date targetValue) {
        Log.d(TAG, "isMonthSame(Date searchValue, Date targetValue) called");
        if (isYearSame(searchValue, targetValue)) {
            return getMonth(searchValue) == getMonth(targetValue);
        }
        return false;
    }

    /**
     * checks whether or not both parameters are on the same year
     *
     * @param searchValue is the first date
     * @param targetValue is the second date
     * @return true if consider is satisfied
     */
    public static boolean isYearSame(Date searchValue, Date targetValue) {
        Log.d(TAG, "isYearSame(Date searchValue, Date targetValue) called");
        return getYear(searchValue) == getYear(targetValue);
    }

    private static int getWeek(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private static int getDay(Date date) {
        Log.d(TAG, "getDate(Date date) called");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private static int getMonth(Date date) {
        Log.d(TAG, "getMonth(Date date) called");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.getMonthValue();// returns value from 0 to 11
        }
        Log.d(TAG, "getMonth(Date date): Outdated OS");
        return -1;
    }

    private static int getYear(Date date) {
        Log.d(TAG, "getYear(Date date) called");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
