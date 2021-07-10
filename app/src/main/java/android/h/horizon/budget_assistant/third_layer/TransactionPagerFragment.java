package android.h.horizon.budget_assistant.third_layer;

import android.app.Activity;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.dialog.DatePickerFragment;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.h.horizon.budget_assistant.transaction.TransactionContainer;
import android.h.horizon.budget_assistant.transaction.Transactions;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.h.horizon.budget_assistant.transaction.Transaction.NEW;

/**
 * Represents the fragment that operates the ViewPager
 */
public class TransactionPagerFragment extends Fragment {
    private static final String TAG = "TransactionPageFragment";
    private static final String ARG_TRANSACTION_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    public static final String NOT_NEW = "NOT";
    private static final int REQUEST_DATE = 0;
    public static final String INVALID_AMOUNT_INPUTTED = "Cannot save transaction\n" +
            "Amount field contains invalid value";
    public static final String DESCRIPTION_FIELD_IS_EMPTY = "Cannot save transaction\n" +
            "Description field is empty";
    private static final int SHORT_DELAY = 1500;
    public static final String DATABASE_CORRUPTED = "Database has been corrupted\n" +
            "Cannot save transaction";
    private Transaction mTransaction;
    private String mTempDescription;
    private double mTempAmount;
    private Button mDateButton;
    private boolean mAmountChange = false;
    private boolean mDescriptionChange = false;

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
                if (hasTransactionBeenSaved()) {
                    getActivity().finish();
                }
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
                // This one too
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Description changed");
                mTempDescription = s.toString();
                mDescriptionChange = true;
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
                try {
                    mTempAmount = Double.parseDouble(s.toString());
                } catch (NumberFormatException e) {
                    mTempAmount = -1; // Dummy value because condition in saveTransaction checks for positive value
                }
                mAmountChange = true;
                Log.d(TAG, "Amount changed");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
    }

    private boolean hasTransactionBeenSaved() {
        boolean areConditionsMet;
        Log.d(TAG, "hasTransactionBeenSaved() called");
        double initialAmount = mTransaction.getAmount();
        areConditionsMet = areConditionsMet();
        if (areConditionsMet) {
            Log.d(TAG, "hasTransactionBeenSaved(): Conditions met");
            saveTransaction(initialAmount);
        }
        return areConditionsMet;
    }

    private boolean areConditionsMet() {
        Log.d(TAG, "areConditionsMet() called");
        boolean areConditionsMet;
        if (mTransaction.getNew().equals(NEW)) {
            Log.d(TAG, "areConditionsMet(): New");
            areConditionsMet = areConditionsMetNewRecord();
        } else if (mTransaction.getNew().equals(NOT_NEW)) {
            Log.d(TAG, "areConditionsMet(): Existing");
            areConditionsMet = areConditionsMetExistingRecord();
        } else {
            Log.d(TAG, "areConditionsMet(): Invalid");
            areConditionsMet = areConditionsMetInvalidRecord();
        }
        return areConditionsMet;
    }

    private void saveTransaction(double initialAmount) {
        Log.d(TAG, "saveTransaction(double initialAmount) called");
        mTransaction.setNew(NOT_NEW);
        TransactionContainer.get(getActivity()).updateTransaction(mTransaction);// saving
        Transactions.get(getActivity()).updateTransactions(mTransaction, initialAmount,
                mTempAmount);//Update Dashboard
    }

    private boolean areConditionsMetNewRecord() {
        Log.d(TAG, "areConditionsMetNewRecord() called");
        boolean areConditionsMet;
        boolean isDescriptionFull;
        boolean isAmountFull;
        isDescriptionFull = isDescriptionFull();
        isAmountFull = isAmountFull(isDescriptionFull);// Needs parameter to differentiate between
        // Toasts
        areConditionsMet = isAmountFull && isDescriptionFull;
        return areConditionsMet;
    }

    private boolean areConditionsMetExistingRecord() {
        Log.d(TAG, "areConditionsMetExistingRecord() called");
        boolean areConditionsMet;
        boolean isDescriptionFull;
        boolean isAmountFull;
        isDescriptionFull = true;
        isAmountFull = true;
        if (mDescriptionChange) {
            isDescriptionFull = isDescriptionFull();
            Log.d(TAG, "areConditionsMetExistingRecord(): Description Changed");
        }
        if (mAmountChange) {
            isAmountFull = isAmountFull(isDescriptionFull);
            Log.d(TAG, "areConditionsMetExistingRecord(): Amount Changed");
        }
        areConditionsMet = isAmountFull && isDescriptionFull;
        return areConditionsMet;
    }

    private boolean areConditionsMetInvalidRecord() {
        Log.d(TAG, "areConditionsMetInvalidRecord() called");
        Log.d(TAG, "Database contains invalid data in NEW field");
        Toast.makeText(getActivity(), DATABASE_CORRUPTED, Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    private boolean isDescriptionFull() {
        boolean isDescriptionFull;
        Log.d(TAG, "isDescriptionFull() called");
        if (mTempDescription != null && !mTempDescription.isEmpty()) {
            Log.d(TAG, "isDescriptionFull(): Description Field full");
            mTransaction.setDescription(mTempDescription);
            isDescriptionFull = true;
        } else {
            Log.d(TAG, "isDescriptionFull(): Description Field empty");
            isDescriptionFull = false;
            DisplayToastForEmptyDescriptionField();
        }
        return isDescriptionFull;
    }

    private boolean isAmountFull(boolean isDescriptionFull) {
        Log.d(TAG, "isAmountFull(boolean isDescriptionFull) called");
        boolean isAmountFull;
        if (mTempAmount > 0) {
            Log.d(TAG, "isAmountFull(): Amount value valid");
            mTransaction.setAmount(mTempAmount);
            isAmountFull = true;
        } else {
            Log.d(TAG, "isAmountFull(): Amount value invalid");
            isAmountFull = false;
            DisplayToastForInvalidAmount(isDescriptionFull);
        }
        return isAmountFull;
    }

    private void DisplayToastForEmptyDescriptionField() {
        Log.d(TAG, "DisplayToastForEmptyDescriptionField() called");
        Toast.makeText(getActivity(), DESCRIPTION_FIELD_IS_EMPTY, Toast.LENGTH_SHORT)
                .show();
    }

    private void DisplayToastForInvalidAmount(boolean isDescriptionFull) {
        Log.d(TAG, "DisplayToastForInvalidAmount(boolean isDescriptionFull) called");
        if (isDescriptionFull) {
            outputToastInvalidAmount();
        } else {// 2 consecutive Toasts require handler
            outputToastInvalidAmountWithHandler();
        }
    }

    private void outputToastInvalidAmount() {
        Log.d(TAG, "outputToastInvalidAmount() called");
        Toast.makeText(getActivity(), INVALID_AMOUNT_INPUTTED, Toast.LENGTH_SHORT)
                .show();
    }

    private void outputToastInvalidAmountWithHandler() {
        Log.d(TAG, "outputToastInvalidAmountWithHandler() called");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                outputToastInvalidAmount();
            }
        }, SHORT_DELAY);
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
