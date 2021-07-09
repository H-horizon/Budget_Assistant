package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.h.horizon.budget_assistant.MainActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.h.horizon.budget_assistant.transaction.TransactionTitles.*;

public class Transactions {
    private static final String TAG = "Transactions";
    private Double mRevenue;
    private Double mExpenditure;
    private Double mSavings;
    private Context mContext;
    private static Transactions sTransactions;


    public static Transactions get(Context context) {
        Log.d(TAG, "Transactions get(Context context) called");
        if (sTransactions == null) {
            sTransactions = new Transactions(context);
        }
        return sTransactions;
    }

    public void updateTransactions(Transaction transaction, Double newAmount) {
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

    private void updateSavings() {
        mSavings = mRevenue - mExpenditure;
    }

    private boolean checkForIncome(Transaction transaction) {
        Log.d(TAG, "checkForIncome(Transaction transaction) called");
        return transaction.getTitle().equals(SALARY) ||
                transaction.getTitle().equals(ALLOWANCE) ||
                transaction.getTitle().equals(OTHER_INCOME);
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
