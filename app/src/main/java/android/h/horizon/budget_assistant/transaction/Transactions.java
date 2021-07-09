package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
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
    public void updateTransactions(Transaction transaction, Double newAmount) {
        Log.d(TAG, "updateTransactions(Transaction transaction, Double newAmount) called");
        if (checkForIncome(transaction)) {
            mRevenue -= transaction.getAmount();
            mRevenue += newAmount;
        } else if (checkForExpense(transaction)) {
            mExpenditure -= transaction.getAmount();
            mExpenditure += newAmount;
        } else {
            Log.d(TAG, "updateTransactions(): Database contains Invalid data");
        }
        updateSavings();
    }

    private Transactions(Context context) {
        mRevenue = 0.0;
        mExpenditure = 0.0;
        Log.d(TAG, "Transactions(Context context) called");
        mContext = context;
        TransactionContainer transactionContainer = TransactionContainer.get(context);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList = transactionContainer.getTransactions();
        for (Transaction transaction : transactionList) {
            if (checkForExpense(transaction)) {
                mExpenditure += transaction.getAmount();
            } else if (
                    checkForIncome(transaction)) {
                mRevenue += transaction.getAmount();
            } else {
                Log.d(TAG, "Transactions(): Database contains Invalid data");
            }
        }
        updateSavings();
    }

    private boolean checkForExpense(Transaction transaction) {
        Log.d(TAG, "checkForExpense(Transaction transaction) called");
        return transaction.getTitle().equals(FOOD) ||
                transaction.getTitle().equals(TRAVELLING) ||
                transaction.getTitle().equals(RENT) ||
                transaction.getTitle().equals(LEISURE) ||
                transaction.getTitle().equals(EDUCATION) ||
                transaction.getTitle().equals(HEALTH) ||
                transaction.getTitle().equals(SUBSCRIPTION) ||
                transaction.getTitle().equals(OTHER_EXPENSES);
    }

    private boolean checkForIncome(Transaction transaction) {
        Log.d(TAG, "checkForIncome(Transaction transaction) called");
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
