package android.h.horizon.budget_assistant.recycler_view;

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
    private TransactionAdapter mAdapter;
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

    private class TransactionHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;

        public TransactionHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {

        private List<Transaction> mTransactionList;

        public TransactionAdapter(List<Transaction> transactions) {
            mTransactionList = transactions;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout., parent, false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionHolder holder, int position) {
            Transaction expense = mTransactionList.get(position);
            holder.mTitleTextView.setText(expense.getDescription());
        }

        @Override
        public int getItemCount() {
            return mTransactionList.size();
        }

        public void setTransaction(List<Transaction> transactions) {
            mTransactions = transactions;
        }
    }


    private void updateUI() {
        TransactionContainer transaction = TransactionContainer.get(getActivity());
        List<Transaction> transactions = transaction.getTransactionList();
        if (mAdapter == null) {
            mAdapter = new TransactionAdapter(transactions);
            mTransactionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTransaction(transactions);
            mAdapter.notifyDataSetChanged();
        }
    }


}
