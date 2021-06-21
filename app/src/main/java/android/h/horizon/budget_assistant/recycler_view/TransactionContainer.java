package android.h.horizon.budget_assistant.recycler_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.h.horizon.budget_assistant.database.TransactionsBaseHelper;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionList {

    private static TransactionList sTransactionList;
    
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TransactionList get(Context context) {
        if (sTransactionList == null) {
            sTransactionList = new TransactionList(context);
        }
        return sTransactionList;
    }

    private TransactionList(Context context) {
        mTransactionList = new ArrayList<>();
        mIncomeList = new ArrayList<>();
        mContext = context.getApplicationContext();
        mDatabase = new TransactionsBaseHelper(mContext)
                .getWritableDatabase();
        setTestData("Education");
        setTestData("Rent");
    }

    private void setTestData(String title) {
        for (int i = 0; i < 2; i++) {
            Transaction expense = new Transaction("Expense #" + i, (i + 1) * 10.1);
            expense.setExpenseTitle(title);
            mTransactionList.add(expense);
            Income income = new Income("Income #" + i, (i + 1) * 20.2);
            mIncomeList.add(income);
        }
    }


    public List<Transaction> getTransactionList() {
        return mTransactionList;
    }

    public List<Transaction> getExpenseList(String title) {
        List<Transaction> expenses = getTransactionList();
        int i = 0;


        for (Transaction expense : expenses) {
            if (!(expense.getExpenseTitle().equals(title))) {
                expenses.remove(i);
                continue;
            }
            i++;
        }
        return expenses;
    }

    public List<Income> getIncomeList() {
        return mIncomeList;
    }

    public Transaction getTransactionFromExpenseList(UUID id) {
        for (Transaction expense : mTransactionList) {
            if (expense.getId().equals(id)) {
                return expense;
            }
        }
        return null;
    }

    public Income getTransactionFromIncomeList(UUID id) {
        for (Income income : mIncomeList) {
            if (income.getId().equals(id)) {
                return income;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }
}

