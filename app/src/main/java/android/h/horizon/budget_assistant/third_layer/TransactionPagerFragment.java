package android.h.horizon.budget_assistant.third_layer;

import android.app.Activity;
import android.content.Intent;
import android.h.horizon.budget_assistant.MainActivity;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.dialog.DatePickerFragment;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.h.horizon.budget_assistant.transaction.TransactionContainer;
import android.h.horizon.budget_assistant.transaction.Transactions;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Represents the fragment that operates the ViewPager
 */
public class TransactionPagerFragment extends Fragment {
    private static final String TAG = "TransactionFragment";
    private static final String ARG_TRANSACTION_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    public static final String NOT_NEW = "NOT";
    private static final int REQUEST_DATE = 0;
    private Transaction mTransaction;
    private String tempDescription;
    private double tempAmount;
    private Button mDateButton;

    /**
     * Gets arguments from TransactionPagerActivity when created
     *
     * @param transactionId is the argument that corresponds to the Transaction object that needs
     *                      to be displayed
     * @return a new fragment
     */
    public static TransactionPagerFragment newInstance(UUID transactionId) {
        Log.d(TAG, "newInstance(UUID transactionId) called");
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRANSACTION_ID, transactionId);
        TransactionPagerFragment fragment = new TransactionPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle savedInstanceState) called");
        UUID transactionId = getTransactionIdFromArguments();
        mTransaction = TransactionContainer.get(getActivity()).getTransaction(transactionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_details, container,
                false);
        Log.d(TAG, "onCreateView(Bundle) called");
        setDateButton(view);
        setSaveButton(view);
        setCancelButton(view);
        setDescriptionField(view);
        setAmountField(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(int requestCode, int resultCode, Intent data) called");
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult() not OK");
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Log.d(TAG, "onActivityResult() requested date");
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            mTransaction.setDate(date);
            mDateButton.setText(dateFormat.format(date));
        }
    }

    private UUID getTransactionIdFromArguments() {
        Log.d(TAG, "getTransactionIdFromArguments() called");
        return (UUID) getArguments().getSerializable(ARG_TRANSACTION_ID);
    }

    private void setDateButton(View view) {
        Log.d(TAG, "setDateButton(View view) called");
        mDateButton = (Button) view.findViewById(R.id.date_button);
        mDateButton.setText(mTransaction.getDate().toString());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Date button clicked");
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mTransaction.getDate());
                dialog.setTargetFragment(TransactionPagerFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
    }

    private void setSaveButton(View view) {
        Log.d(TAG, "setSaveButton(View view) called");
        Button saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Save Button clicked");
                saveTransaction();
                getActivity().finish();
            }
        });
    }

    private void setCancelButton(View view) {
        Log.d(TAG, "setCancelButton(View view) called");
        Button cancelButton = (Button) view.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Cancel Button clicked");
                getActivity().finish();
            }
        });
    }

    private void setDescriptionField(View view) {
        Log.d(TAG, "setDescriptionField(View view) called");
        EditText descriptionField = (EditText) view.findViewById(R.id.description_field);
        descriptionField.setText(mTransaction.getDescription());
        descriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                Log.d(TAG, "Description changed");
                tempDescription = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
    }

    private void setAmountField(View view) {
        Log.d(TAG, "setAmountField(View view) called");
        EditText amountField = (EditText) view.findViewById(R.id.amount_field);
        amountField.setText(Double.toString(mTransaction.getAmount()));
        amountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    tempAmount = Double.parseDouble(s.toString());
                }
                Log.d(TAG, "Amount changed");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
    }

    private void saveTransaction() {
        Log.d(TAG, "saveTransaction() called");
        mTransaction.setNew(NOT_NEW);
        if (tempDescription != null && !tempDescription.isEmpty()) {
            Log.d(TAG, "saveTransaction() description not empty");
            mTransaction.setDescription(tempDescription);
        }
        if (tempAmount >= 0) {
            Transactions.get(getActivity()).updateTransactions(mTransaction, tempAmount);
            Log.d(TAG, "saveTransaction() amount not empty");
            mTransaction.setAmount(tempAmount);
        }
        //Handle null inputs here
        TransactionContainer.get(getActivity()).updateTransaction(mTransaction);
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
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteTransactionIfNotSaved();
        Log.d(TAG, "onDestroy() called");
    }

    private void deleteTransactionIfNotSaved() {
        Log.d(TAG, "deleteTransactionIfNotSaved() called");
        if (!(mTransaction.getNew().equals(NOT_NEW))) {
            TransactionContainer.get(getActivity()).deleteTransaction(mTransaction);
        }
    }
}
