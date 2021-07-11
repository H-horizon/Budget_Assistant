package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.h.horizon.budget_assistant.transaction.TransactionTitles.*;

/**
 * This class is a singleton, used to manipulate total revenue, expenditure and savings
 */
public class Transactions {
    private static final String TAG = "Transactions";
    private Double mRevenue;
    private Double mExpenditure;
    private Double mSavings;
    private Context mContext;
    private static Transactions sTransactions;
    private List<Transaction> mTransactionList;
    TransactionContainer mTransactionContainer;


    /**
     * Gets a singleton of Transactions
     *
     * @param context represents the current state of the application
     * @return a singleton representing this class
     */
    public static Transactions get(Context context) {
        Log.d(TAG, "Transactions get(Context context) called");
        if (sTransactions == null) {
            sTransactions = new Transactions(context);
        }
        return sTransactions;
    }

    /**
     * Update all Transactions' variables
     *
     * @param transaction is the transaction object before saving
     * @param newAmount   is the new amount value
     */
    public void updateTransactions(Transaction transaction, Double initialAmount, Double newAmount) {
        Log.d(TAG, "updateTransactions(Transaction transaction, Double newAmount) called");
        if (isIncome(transaction)) {
            Log.d(TAG, "updateTransactions(): add revenue");
            mRevenue -= initialAmount;
            mRevenue += newAmount;
        } else if (isExpense(transaction)) {
            Log.d(TAG, "updateTransactions(): add expenditure");
            mExpenditure -= initialAmount;
            mExpenditure += newAmount;
        } else {
            Log.d(TAG, "updateTransactions(): Database contains Invalid data");
        }
        updateSavings();
    }

    /**
     * Gets the total revenue from all transactions which happened on the same day as the current
     * date
     *
     * @return the current date's revenue
     */
    public double getTodayRevenue() {
        Log.d(TAG, "getTodayRevenue() called");
        double todayRevenue = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isIncome(transaction) && TransactionDate.isToday(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getTodayRevenue(): Increment value");
                todayRevenue += transaction.getAmount();
            }
        }
        return todayRevenue;
    }

    /**
     * Gets the total revenue from all transactions which happened on the same week as the current
     * date
     *
     * @return that week's revenue
     */
    public double getWeeklyRevenue() {
        Log.d(TAG, "getWeeklyRevenue() called");
        double weeklyRevenue = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isIncome(transaction) && TransactionDate.isWeekSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getWeeklyRevenue(): Increment value");
                weeklyRevenue += transaction.getAmount();
            }
        }
        return weeklyRevenue;
    }

    /**
     * Gets the total revenue from all transactions which took place during the same month as the
     * current date
     *
     * @return that month's revenue
     */
    public double getMonthlyRevenue() {
        Log.d(TAG, "getMonthlyRevenue() called");
        double monthlyRevenue = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isIncome(transaction) && TransactionDate.isMonthSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getMonthlyRevenue(): Increment");
                monthlyRevenue += transaction.getAmount();
            }
        }
        return monthlyRevenue;
    }

    /**
     * Gets the total revenue from all transactions which occurred on the same year as the current
     * date
     *
     * @return that year's revenue
     */
    public double getYearlyRevenue() {
        Log.d(TAG, "getYearlyRevenue() called");
        double yearlyRevenue = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isIncome(transaction) && TransactionDate.isYearSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getYearlyRevenue(): Increment");
                yearlyRevenue += transaction.getAmount();
            }
        }
        return yearlyRevenue;
    }

    /**
     * Gets the total expenditure from all transactions which happened on the same day as the
     * current date
     *
     * @return the current date's expenditure
     */
    public double getTodayExpenditure() {
        Log.d(TAG, "getTodayExpenditure() called");
        double todayExpenditure = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isExpense(transaction) && TransactionDate.isToday(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getTodayExpenditure(): Increment value");
                todayExpenditure += transaction.getAmount();
            }
        }
        return todayExpenditure;
    }

    /**
     * Gets the total expenditure from all transactions which happened on the same week as the
     * current date
     *
     * @return that week's expenditure
     */
    public double getWeeklyExpenditure() {
        Log.d(TAG, "getWeeklyExpenditure() called");
        double weeklyExpenditure = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isExpense(transaction) && TransactionDate.isWeekSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getWeeklyExpenditure(): Increment");
                weeklyExpenditure += transaction.getAmount();
            }
        }
        return weeklyExpenditure;
    }

    /**
     * Gets the total expenditure from all transactions which took place during the same month as
     * the current date
     *
     * @return that month's expenditure
     */
    public double getMonthlyExpenditure() {
        Log.d(TAG, "getMonthlyExpenditure() called");
        double monthlyExpenditure = 0;
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isExpense(transaction) && TransactionDate.isMonthSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getMonthlyExpenditure(): Increment");
                monthlyExpenditure += transaction.getAmount();
            }
        }
        return monthlyExpenditure;
    }

    /**
     * Gets the total expenditure from all transactions which occurred on the same year as the current
     * date
     *
     * @return that year's expenditure
     */
    public double getYearlyExpenditure() {
        Log.d(TAG, "getYearlyExpenditure() called");
        double yearlyExpenditure = 0;
        updateTransactionList();
        Date currentDate = new Date();
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isExpense(transaction) && TransactionDate.isYearSame(currentDate,
                    transaction.getDate())) {
                Log.d(TAG, "getYearlyExpenditure(): Increment");
                yearlyExpenditure += transaction.getAmount();
            }
        }
        return yearlyExpenditure;
    }


    private Transactions(Context context) {
        mRevenue = 0.0;
        mExpenditure = 0.0;
        Log.d(TAG, "Transactions(Context context) called");
        mContext = context;
        mTransactionContainer = TransactionContainer.get(context);
        updateTransactionList();
        for (Transaction transaction : mTransactionList) {
            if (isExpense(transaction)) {
                Log.d(TAG, "Transactions(): Increment mExpenditure");
                mExpenditure += transaction.getAmount();
            } else if (isIncome(transaction)) {
                Log.d(TAG, "Transactions(): Increment mRevenue");
                mRevenue += transaction.getAmount();
            } else {
                Log.d(TAG, "Transactions(): Database contains Invalid data");
            }
        }
        updateSavings();
    }

    private void updateTransactionList() {
        Log.d(TAG, "updateTransactionList() called");
        mTransactionList = mTransactionContainer.getTransactions();
    }

    private boolean isExpense(Transaction transaction) {
        Log.d(TAG, "isExpense(Transaction transaction) called");
        return transaction.getTitle().equals(FOOD) ||
                transaction.getTitle().equals(TRAVELLING) ||
                transaction.getTitle().equals(RENT) ||
                transaction.getTitle().equals(LEISURE) ||
                transaction.getTitle().equals(EDUCATION) ||
                transaction.getTitle().equals(HEALTH) ||
                transaction.getTitle().equals(SUBSCRIPTION) ||
                transaction.getTitle().equals(OTHER_EXPENSES);
    }

    private boolean isIncome(Transaction transaction) {
        Log.d(TAG, "isIncome(Transaction transaction) called");
        return transaction.getTitle().equals(SALARY) ||
                transaction.getTitle().equals(ALLOWANCE) ||
                transaction.getTitle().equals(OTHER_INCOME);
    }

    private void updateSavings() {
        Log.d(TAG, "updateSavings() called");
        mSavings = mRevenue - mExpenditure;
    }

    public Double getRevenue() {
        return mRevenue;
    }

    public void setRevenue(Double revenue) {
        mRevenue = revenue;
    }

    public Double getExpenditure() {
        return mExpenditure;
    }

    public void setExpenditure(Double expenditure) {
        mExpenditure = expenditure;
    }

    public Double getSavings() {
        return mSavings;
    }

    public void setSavings(Double savings) {
        mSavings = savings;
    }
}
