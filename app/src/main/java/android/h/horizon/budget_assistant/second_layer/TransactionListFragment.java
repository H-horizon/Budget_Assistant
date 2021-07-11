package android.h.horizon.budget_assistant.second_layer;

import android.app.Activity;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.h.horizon.budget_assistant.transaction.TransactionContainer;
import android.h.horizon.budget_assistant.third_layer.TransactionPagerActivity;
import android.h.horizon.budget_assistant.transaction.TransactionDate;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Represents the class that create a RecyclerView for a list of transactions
 */
public class TransactionListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_TRANSACTION_TITLE = "transaction_title";
    private static final int REQUEST_CODE_TITLE = 0;
    private static final String TAG = "TransactionListFragment";
    private RecyclerView mTransactionRecyclerView;
    private TransactionAdapter mAdapter;
    private String mTransactionTitle;
    private static final int ALL_TIME = 0;
    private static final int TODAY = 1;
    private static final int THIS_WEEK = 2;
    private static final int THIS_MONTH = 3;
    private static final int THIS_YEAR = 4;
    private int mPosition = ALL_TIME;//default value

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
        MenuItem item = menu.findItem(R.id.time_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_time_array, R.layout.spinner_items_list_main);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected() called");
        mPosition = position;
        updateUI();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item) called");
        switch (item.getItemId()) {
            //Creates a new Transaction
            case R.id.menu_item_new_transaction:
                addTransactionUsingToolbar();
                return true;
            case R.id.time_spinner:
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
        Log.d(TAG, "setArgumentTo_mTransactionTitle() called");
        mTransactionTitle = (String) getArguments().getSerializable(ARG_TRANSACTION_TITLE);
    }

    private View setRecyclerView(LayoutInflater inflater, ViewGroup container) {
        Log.d(TAG, "setRecyclerView(LayoutInflater inflater, ViewGroup container) called");
        View view = inflater.inflate(R.layout.transaction_recycler_view, container, false);
        mTransactionRecyclerView = (RecyclerView) view
                .findViewById(R.id.transactions_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void updateUI() {
        Log.d(TAG, "updateUI() called");
        TransactionContainer transactionContainer = TransactionContainer.get(getActivity());
        List<Transaction> transactions = transactionContainer.getTransactions(mTransactionTitle);
        List<Transaction> filteredTransactions = new ArrayList<>();
        filteredTransactions = getFilteredTransactionList(transactions, filteredTransactions);
        setUI(filteredTransactions);
    }

    private List<Transaction> getFilteredTransactionList(List<Transaction> transactions,
                                                         List<Transaction> filteredTransactions) {
        Log.d(TAG, "updateUI() called");

        switch (mPosition) {
            case ALL_TIME:
                Log.d(TAG, "updateUI(): ALL_TIME");
                return transactions;
            case TODAY:
                setFilteredTransactionListToday(transactions, filteredTransactions);
                break;
            case THIS_WEEK:
                setFilteredTransactionListThisWeek(transactions, filteredTransactions);
                break;
            case THIS_MONTH:
                setFilteredTransactionListThisMonth(transactions, filteredTransactions);
                break;
            case THIS_YEAR:
                setFilteredTransactionListThisYear(transactions, filteredTransactions);
                break;
            default:
                Log.d(TAG, "updateUI(): UNKNOWN CONSTANT");
        }
        return filteredTransactions;
    }

    private void setFilteredTransactionListToday(List<Transaction> transactions,
                                                 List<Transaction> filteredTransactions) {
        Log.d(TAG, "setFilteredTransactionListToday() called");
        for (Transaction transaction : transactions) {
            Date date = new Date();
            if (TransactionDate.isToday(date, transaction.getDate())) {
                filteredTransactions.add(transaction);
            }
        }
    }

    private void setFilteredTransactionListThisWeek(List<Transaction> transactions,
                                                    List<Transaction> filteredTransactions) {
        Log.d(TAG, "setFilteredTransactionListThisWeek() called");
        for (Transaction transaction : transactions) {
            Date date = new Date();
            if (TransactionDate.isWeekSame(date, transaction.getDate())) {
                filteredTransactions.add(transaction);
            }
        }
    }

    private void setFilteredTransactionListThisMonth(List<Transaction> transactions,
                                                     List<Transaction> filteredTransactions) {
        Log.d(TAG, "setFilteredTransactionListThisMonth() called");
        for (Transaction transaction : transactions) {
            Date date = new Date();
            if (TransactionDate.isMonthSame(date, transaction.getDate())) {
                filteredTransactions.add(transaction);
            }
        }
    }

    private void setFilteredTransactionListThisYear(List<Transaction> transactions,
                                                    List<Transaction> filteredTransactions) {
        Log.d(TAG, "setFilteredTransactionListThisYear() called");
        for (Transaction transaction : transactions) {
            Date date = new Date();
            if (TransactionDate.isYearSame(date, transaction.getDate())) {
                filteredTransactions.add(transaction);
            }
        }
    }

    private void setUI(List<Transaction> filteredTransactions) {
        Log.d(TAG, "setUI() called");
        if (mAdapter == null) {
            mAdapter = new TransactionAdapter(filteredTransactions);
            mTransactionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTransactions(filteredTransactions);
            mAdapter.notifyDataSetChanged();
        }
    }


    private void addTransactionUsingToolbar() {
        Log.d(TAG, "addTransactionUsingToolbar() called");
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
        private static final String TAG = "TransactionHolder";

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
        private static final String TAG = "TransactionAdapter";

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
            Log.d(TAG, "setViewToListFormat(ViewGroup parent) called");
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
