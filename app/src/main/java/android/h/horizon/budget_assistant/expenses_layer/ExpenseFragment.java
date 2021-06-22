package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.recycler_view.Transaction;
import android.h.horizon.budget_assistant.recycler_view.TransactionContainer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ExpenseFragment extends Fragment {
    private static final String TAG = "ExpenseFragment";
    Transaction mTransaction;
    private EditText mDescriptionField;
    private EditText mAmountField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID TransactionId = (UUID) getActivity().getIntent()
                .getSerializableExtra(FoodActivity.EXTRA_TRANSACTION_ID);
        mTransaction = TransactionContainer.get(getActivity()).getTransaction(TransactionId);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View expenseView = inflater.inflate(R.layout.fragment_expense, container,
                false);
        Log.d(TAG, "onCreateView(Bundle) called");

        mDescriptionField = (EditText) expenseView.findViewById(R.id.description_field);
        mDescriptionField.setText(mTransaction.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mTransaction.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });

        mAmountField = (EditText) expenseView.findViewById(R.id.amount_field);
        mAmountField.setText(mTransaction.getDescription());
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mTransaction.setAmount(Double.parseDouble(s.toString()) );
            }

            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });

        return expenseView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
//        TransactionContainer.get(getActivity())
//                .updateTransaction(mTransaction);
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
        Log.d(TAG, "onDestroy() called");
    }
}
