package android.h.horizon.budget_assistant.transactions;

import android.h.horizon.budget_assistant.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TransactionListFragment extends Fragment {
    private RecyclerView mTransactionRecyclerView;
    private TransactionAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_recycler_view, container, false);
        mTransactionRecyclerView = (RecyclerView) view
                .findViewById(R.id.transactions_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        TransactionContainer transactionContainer = TransactionContainer.get(getActivity());
        List<Transaction> transactions = transactionContainer.getTransactions();
        mAdapter = new TransactionAdapter(transactions);
        mTransactionRecyclerView.setAdapter(mAdapter);
    }

    private class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mDescriptionTextView;
        private TextView mAmountTextView;
        private TextView mDateTextView;
        private Transaction mTransaction;

        public TransactionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDescriptionTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_description);
            mAmountTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_amount);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_date);

        }

        public void bindTransaction(Transaction transaction) {
            mTransaction = transaction;
            mDescriptionTextView.setText(mTransaction.getDescription());
            mDateTextView.setText(mTransaction.getDate().toString());
            mAmountTextView.setText(Double.toString(mTransaction.getAmount()));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mTransaction.getDescription() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {
        private List<Transaction> mTransactions;
        public TransactionAdapter(List<Transaction> transactions) {
            mTransactions = transactions;
        }

        @Override
        public TransactionHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_transaction, parent, false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionListFragment.TransactionHolder holder, int position) {
            Transaction transaction = mTransactions.get(position);
            holder.bindTransaction(transaction);
        }

        @Override
        public int getItemCount() {
            return mTransactions.size();
        }
    }
}
