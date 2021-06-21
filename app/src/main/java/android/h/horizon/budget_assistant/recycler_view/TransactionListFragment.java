package android.h.horizon.budget_assistant.recycler_view;

import android.h.horizon.budget_assistant.expenses.Expense;
import android.h.horizon.budget_assistant.incomes.Income;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.h.horizon.budget_assistant.R;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionListFragment extends Fragment {

    private RecyclerView mTransactionRecyclerView;
    private ExpensesAdapter mExpensesAdapter;
    private IncomesAdapter mIncomesAdapter;
    private String mTitle;

    public TransactionListFragment(String title) {
        mTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_recycler_view, container, false);//usually the layout file is empty
        mTransactionRecyclerView = (RecyclerView) view
                .findViewById(R.id.transactions_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private class ExpensesHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;

        public ExpensesHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    private class ExpensesAdapter extends RecyclerView.Adapter<ExpensesHolder> {

        private List<Expense> mExpenseList;

        public ExpensesAdapter(List<Expense> expenses) {
            mExpenseList = expenses;
        }

        @Override
        public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ExpensesHolder(view);
        }

        @Override
        public void onBindViewHolder(ExpensesHolder holder, int position) {
            Expense expense = mExpenseList.get(position);
            holder.mTitleTextView.setText(expense.getDescription());
        }

        @Override
        public int getItemCount() {
            return mExpenseList.size();
        }
    }

    private class IncomesHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;

        public IncomesHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    private class IncomesAdapter extends RecyclerView.Adapter<IncomesHolder> {

        private List<Income> mIncomeList;

        public IncomesAdapter(List<Income> incomes) {
            mIncomeList = incomes;
        }

        @Override
        public IncomesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new IncomesHolder(view);
        }

        @Override
        public void onBindViewHolder(IncomesHolder holder, int position) {
            Income income = mIncomeList.get(position);
            holder.mTitleTextView.setText(income.getDescription());
        }

        @Override
        public int getItemCount() {
            return mIncomeList.size();
        }
    }

    private void updateUI() {
        Transaction transaction = Transaction.get(getActivity());
        List<Expense> expenses = transaction.getExpenseList(mTitle);
        if (expenses.isEmpty()) {
            return;
        }
        mExpensesAdapter = new ExpensesAdapter(expenses);
        mTransactionRecyclerView.setAdapter(mExpensesAdapter);
    }

}
