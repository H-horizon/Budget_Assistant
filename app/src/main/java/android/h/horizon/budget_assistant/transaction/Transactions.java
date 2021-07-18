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
        updateTransactionList();
        return getDayRevenue(new Date(), mTransactionList);
    }

    /**
     * Gets the total revenue from all transactions which happened on the same week as the current
     * date
     *
     * @return that week's revenue
     */
    public double getThisWeekRevenue() {
        Log.d(TAG, "getThisWeekRevenue() called");
        updateTransactionList();
        return getWeekRevenue(new Date(), mTransactionList);
    }

    /**
     * Gets the total revenue from all transactions which took place during the same month as the
     * current date
     *
     * @return that month's revenue
     */
    public double getThisMonthRevenue() {
        Log.d(TAG, "getThisMonthRevenue() called");
        updateTransactionList();
        return getMonthRevenue(new Date(), mTransactionList);
    }

    /**
     * Gets the total revenue from all transactions which occurred on the same year as the current
     * date
     *
     * @return that year's revenue
     */
    public double getThisYearRevenue() {
        Log.d(TAG, "getThisYearRevenue() called");
        updateTransactionList();
        return getYearRevenue(new Date(), mTransactionList);
    }

    /**
     * Gets the total expenditure from all transactions which happened on the same day as the
     * current date
     *
     * @return the current date's expenditure
     */
    public double getTodayExpenditure() {
        Log.d(TAG, "getTodayExpenditure() called");
        updateTransactionList();
        return getDayExpenditure(new Date(), mTransactionList);
    }

    /**
     * Gets the total expenditure from all transactions which happened on the same week as the
     * current date
     *
     * @return that week's expenditure
     */
    public double getThisWeekExpenditure() {
        Log.d(TAG, "getThisWeekExpenditure() called");
        updateTransactionList();
        return getWeekExpenditure(new Date(), mTransactionList);
    }

    /**
     * Gets the total expenditure from all transactions which took place during the same month as
     * the current date
     *
     * @return that month's expenditure
     */
    public double getThisMonthExpenditure() {
        Log.d(TAG, "getThisMonthExpenditure() called");
        updateTransactionList();
        return getMonthExpenditure(new Date(), mTransactionList);
    }

    /**
     * Gets the total expenditure from all transactions which occurred on the same year as the current
     * date
     *
     * @return that year's expenditure
     */
    public double getThisYearExpenditure() {
        Log.d(TAG, "getThisYearExpenditure() called");
        updateTransactionList();
        return getYearExpenditure(new Date(), mTransactionList);
    }

    public double getDayRevenue(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, " getDayRevenue() called");
        double dayRevenue = 0;
        for (Transaction transaction : transactionList) {
            if (isIncome(transaction) && TransactionDate.isToday(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, " getDayRevenue(): Increment value");
                dayRevenue += transaction.getAmount();
            }
        }
        return dayRevenue;
    }

    public double getWeekRevenue(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getWeekRevenue() called");
        double weeklyRevenue = 0;
        for (Transaction transaction : transactionList) {
            if (isIncome(transaction) && TransactionDate.isWeekSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getWeekRevenue(): Increment value");
                weeklyRevenue += transaction.getAmount();
            }
        }
        return weeklyRevenue;
    }

    public double getMonthRevenue(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getMonthRevenue() called");
        double monthlyRevenue = 0;
        for (Transaction transaction : transactionList) {
            if (isIncome(transaction) && TransactionDate.isMonthSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getMonthRevenue(): Increment");
                monthlyRevenue += transaction.getAmount();
            }
        }
        return monthlyRevenue;
    }

    public double getYearRevenue(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getYearRevenue() called");
        double yearlyRevenue = 0;
        for (Transaction transaction : transactionList) {
            if (isIncome(transaction) && TransactionDate.isYearSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getYearRevenue(): Increment");
                yearlyRevenue += transaction.getAmount();
            }
        }
        return yearlyRevenue;
    }

    public double getDayExpenditure(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getDayExpenditure() called");
        double todayExpenditure = 0;
        for (Transaction transaction : transactionList) {
            if (isExpense(transaction) && TransactionDate.isToday(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getDayExpenditure(): Increment value");
                todayExpenditure += transaction.getAmount();
            }
        }
        return todayExpenditure;
    }

    public double getWeekExpenditure(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getWeekExpenditure() called");
        double weeklyExpenditure = 0;
        for (Transaction transaction : transactionList) {
            if (isExpense(transaction) && TransactionDate.isWeekSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getWeekExpenditure(): Increment");
                weeklyExpenditure += transaction.getAmount();
            }
        }
        return weeklyExpenditure;
    }

    public double getMonthExpenditure(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getMonthExpenditure() called");
        double monthlyExpenditure = 0;
        for (Transaction transaction : transactionList) {
            if (isExpense(transaction) && TransactionDate.isMonthSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getMonthExpenditure(): Increment");
                monthlyExpenditure += transaction.getAmount();
            }
        }
        return monthlyExpenditure;
    }

    public double getYearExpenditure(Date searchDate, List<Transaction> transactionList) {
        Log.d(TAG, "getYearExpenditure() called");
        double yearlyExpenditure = 0;
        for (Transaction transaction : transactionList) {
            if (isExpense(transaction) && TransactionDate.isYearSame(searchDate,
                    transaction.getDate())) {
                Log.d(TAG, "getYearExpenditure(): Increment");
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

    public boolean isExpense(Transaction transaction) {
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

    public boolean isIncome(Transaction transaction) {
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
