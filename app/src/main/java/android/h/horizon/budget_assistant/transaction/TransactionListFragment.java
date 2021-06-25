package android.h.horizon.budget_assistant.transaction;

import android.app.Activity;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;


public class TransactionListFragment extends Fragment {
    private static final String ARG_TRANSACTION_TITLE = "transaction_title";
    private static final int REQUEST_CODE_TITLE = 0;
    private static final String TAG = "TransactionListFragment";
    private RecyclerView mTransactionRecyclerView;
    private TransactionAdapter mAdapter;
    private String mTransactionTitle;

    public static TransactionListFragment newInstance(String transactionTitle) {
        Log.d(TAG, "createFragment() called");
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRANSACTION_TITLE, transactionTitle);
        TransactionListFragment fragment = new TransactionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container,\n" +
                "                             Bundle savedInstanceState) called");
        View view = inflater.inflate(R.layout.transaction_recycler_view, container, false);
        mTransactionRecyclerView = (RecyclerView) view
                .findViewById(R.id.transactions_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updateUI() called");
        TransactionContainer transactionContainer = TransactionContainer.get(getActivity());
        List<Transaction> transactions = transactionContainer.getTransactions();
        if (mAdapter == null) {
            mAdapter = new TransactionAdapter(transactions);
            mTransactionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTransactions(transactions);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mDescriptionTextView;
        private TextView mAmountTextView;
        private TextView mDateTextView;
        private Transaction mTransaction;

        public TransactionHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "TransactionHolder(View itemView) called");
            itemView.setOnClickListener(this);
            mDescriptionTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_description);
            mAmountTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_amount);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_transaction_date);

        }

        public void bindTransaction(Transaction transaction) {
            Log.d(TAG, "bindTransaction(Transaction transaction) called");
            mTransaction = transaction;
            mDescriptionTextView.setText(mTransaction.getDescription());
            mDateTextView.setText(mTransaction.getDate().toString());
            mAmountTextView.setText(Double.toString(mTransaction.getAmount()));
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "A transaction category has been clicked");
            Intent intent = TransactionPagerActivity.newIntent(getActivity(), mTransaction.getId(), mTransactionTitle);
            startActivityForResult(intent, REQUEST_CODE_TITLE);
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {
        private List<Transaction> mTransactions;

        public TransactionAdapter(List<Transaction> transactions) {
            Log.d(TAG, "TransactionAdapter(List<Transaction> transactions) called");
            mTransactions = transactions;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "TransactionHolder onCreateViewHolder(ViewGroup parent, " +
                    "int viewType) called");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_transaction, parent, false);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionListFragment.TransactionHolder holder, int position)
        {
            Log.d(TAG, "onBindViewHolder(TransactionListFragment.TransactionHolder holder, " +
                    "int position) called");
            Transaction transaction = mTransactions.get(position);
            holder.bindTransaction(transaction);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount() called");
            return mTransactions.size();
        }

        public void setTransactions(List<Transaction> transactions) {
            Log.d(TAG, "setTransactions(List<Transaction> transactions) called");
            mTransactions = transactions;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater called)");
        inflater.inflate(R.menu.fragment_transaction_list, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle savedInstanceState called)");
        mTransactionTitle = (String) getArguments().getSerializable(ARG_TRANSACTION_TITLE);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item) called");
        switch (item.getItemId()) {
            case R.id.menu_item_new_transaction:
                Transaction transaction = new Transaction(UUID.randomUUID());
                transaction.setTitle(mTransactionTitle);
                TransactionContainer.get(getActivity()).addTransaction(transaction);
                Intent intent = TransactionPagerActivity
                        .newIntent(getActivity(), transaction.getId(), mTransactionTitle);
                startActivityForResult(intent, REQUEST_CODE_TITLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data) called");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_TITLE) {
            if (data == null) {
                return;
            }
            mTransactionTitle = TransactionPagerActivity.decodeTitle(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
