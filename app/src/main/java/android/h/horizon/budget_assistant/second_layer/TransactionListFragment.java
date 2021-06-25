package android.h.horizon.budget_assistant.second_layer;

import android.app.Activity;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.h.horizon.budget_assistant.transaction.TransactionContainer;
import android.h.horizon.budget_assistant.third_layer.TransactionPagerActivity;
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

/**
 * Represents the class that create a RecyclerView for a list of transactions
 */
public class TransactionListFragment extends Fragment {
    private static final String ARG_TRANSACTION_TITLE = "transaction_title";
    private static final int REQUEST_CODE_TITLE = 0;
    private static final String TAG = "TransactionListFragment";
    private RecyclerView mTransactionRecyclerView;
    private TransactionAdapter mAdapter;
    private String mTransactionTitle;

    /**
     * Passes arguments from TransactionListActivity to this class
     *
     * @param transactionTitle is the argument to be passed
     * @return an instance of this class
     */
    public static TransactionListFragment newInstance(String transactionTitle) {
        Log.d(TAG, "createFragment() called");
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRANSACTION_TITLE, transactionTitle);
        TransactionListFragment fragment = new TransactionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle savedInstanceState called)");
        setArgumentTo_mTransactionTitle();//Retrieves argument
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container,\n" +
                "                             Bundle savedInstanceState) called");
        View view = setRecyclerView(inflater, container);
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "onCreateOptionsMenu(Menu menu, MenuInflater inflater called)");
        inflater.inflate(R.menu.fragment_transaction_list, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item) called");
        switch (item.getItemId()) {
            //Creates a new Transaction
            case R.id.menu_item_new_transaction:
                addTransactionUsingToolbar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            mTransactionTitle = TransactionPagerActivity.returnArgumentTitle(data);//Gets argument from
            // third layer
        }
    }

    private void setArgumentTo_mTransactionTitle() {
        mTransactionTitle = (String) getArguments().getSerializable(ARG_TRANSACTION_TITLE);
    }

    private View setRecyclerView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.transaction_recycler_view, container, false);
        mTransactionRecyclerView = (RecyclerView) view
                .findViewById(R.id.transactions_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updateUI() called");
        TransactionContainer transactionContainer = TransactionContainer.get(getActivity());
        //Update the following line to get list based on title[Delete afterwards]
        List<Transaction> transactions = transactionContainer.getTransactions(mTransactionTitle);
        if (mAdapter == null) {
            mAdapter = new TransactionAdapter(transactions);
            mTransactionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTransactions(transactions);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void addTransactionUsingToolbar() {
        Transaction transaction = new Transaction(UUID.randomUUID());
        transaction.setTitle(mTransactionTitle);
        TransactionContainer.get(getActivity()).addTransaction(transaction);
        Intent intent = TransactionPagerActivity
                .newIntent(getActivity(), transaction.getId(), mTransactionTitle);
        startActivityForResult(intent, REQUEST_CODE_TITLE);
    }

    /**
     * Represents the ViewHolder for the RecyclerView
     */
    private class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mDescriptionTextView;
        private TextView mAmountTextView;
        private TextView mDateTextView;
        private Transaction mTransaction;

        /**
         * Creates the RecyclerView
         *
         * @param itemView is the current view
         */
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

        /**
         * Sets the required data of a transaction on the UI
         *
         * @param transaction
         */
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
            Intent intent = TransactionPagerActivity.newIntent(getActivity(), mTransaction.getId(),
                    mTransactionTitle);
            startActivityForResult(intent, REQUEST_CODE_TITLE);
        }
    }

    /**
     * Represents the adapter for the RecyclerView
     */
    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {
        private List<Transaction> mTransactions;

        /**
         * Creates an adapter for the RecyclerView
         *
         * @param transactions is a list of transactions to display
         */
        public TransactionAdapter(List<Transaction> transactions) {
            Log.d(TAG, "TransactionAdapter(List<Transaction> transactions) called");
            mTransactions = transactions;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "TransactionHolder onCreateViewHolder(ViewGroup parent, " +
                    "int viewType) called");
            View view = setViewToListFormat(parent);
            return new TransactionHolder(view);
        }

        @Override
        public void onBindViewHolder(TransactionListFragment.TransactionHolder holder, int position) {
            Log.d(TAG, "onBindViewHolder(TransactionListFragment.TransactionHolder holder, " +
                    "int position) called");
            Transaction transaction = mTransactions.get(position);
            if (!transaction.getNew().equals(Transaction.NEW)) {
                holder.bindTransaction(transaction);
            }
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

        private View setViewToListFormat(ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_transaction, parent, false);
            return view;
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
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        updateUI();
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
