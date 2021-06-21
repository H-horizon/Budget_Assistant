package android.h.horizon.budget_assistant.recycler_view;

import android.content.Context;
import android.h.horizon.budget_assistant.expenses.Expense;
import android.h.horizon.budget_assistant.incomes.Income;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Transaction {

    private static Transaction sTransaction;
    private List<Expense> mExpenseList;
    private List<Income> mIncomeList;

    public static Transaction get(Context context) {
        if (sTransaction == null) {
            sTransaction = new Transaction(context);
        }
        return sTransaction;
    }

    private Transaction(Context context) {
        mExpenseList = new ArrayList<>();
        mIncomeList = new ArrayList<>();
        setTestData("Education");
        setTestData("Rent");
    }

    private void setTestData(String title) {
        for (int i = 0; i < 2; i++) {
            Expense expense = new Expense("Expense #" + i, (i + 1) * 10.1);
            expense.setExpenseTitle(title);
            mExpenseList.add(expense);
            Income income = new Income("Income #" + i, (i + 1) * 20.2);
            mIncomeList.add(income);
        }
    }


    public List<Expense> getExpenseList() {
        return mExpenseList;
    }

    public List<Expense> getExpenseList(String title) {
        List<Expense> expenses = getExpenseList();
        int i = 0;


        for (Expense expense : expenses) {
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

    public Expense getTransactionFromExpenseList(UUID id) {
        for (Expense expense : mExpenseList) {
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
}

