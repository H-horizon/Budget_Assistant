package android.h.horizon.budget_assistant.dashboard;

import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.transaction.TransactionListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.h.horizon.budget_assistant.transaction.TransactionNames.ALLOWANCE;
import static android.h.horizon.budget_assistant.transaction.TransactionNames.OTHER_INCOME;
import static android.h.horizon.budget_assistant.transaction.TransactionNames.SALARY;

public class IncomesFragment extends Fragment {
    private static final String TAG = "IncomesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View incomeView = inflater.inflate(R.layout.fragment_incomes, container,
                false);
        Log.d(TAG, "onCreateView(LayoutInflater, Container, Bundle) called");
        setDateButton(incomeView);
        setSalaryButton(incomeView);
        setAllowanceButton(incomeView);
        setOthersButton(incomeView);
        return incomeView;
    }

    private void setDateButton(View incomeView) {
        Log.d(TAG, "setDateButton called");
        Button dateButton = (Button) incomeView.findViewById(R.id.date_button);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateButton.setText(dateFormat.format(currentDate));
        dateButton.setEnabled(false);
    }

    private void setSalaryButton(View incomeView) {
        Log.d(TAG, "setSalaryButton called");
        Button salaryButton = (Button) incomeView.findViewById(R.id.salary_button);
        salaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Salary Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), SALARY);
                startActivity(i);
            }
        });
    }

    private void setAllowanceButton(View incomeView) {
        Log.d(TAG, "setAllowanceButton called");
        Button allowanceButton = (Button) incomeView.findViewById(R.id.allowance_button);
        allowanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Allowance Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), ALLOWANCE);
                startActivity(i);
                //Implement RecyclerView
            }
        });
    }

    private void setOthersButton(View incomeView) {
        Log.d(TAG, "setOthersButton called");
        Button othersButton = (Button) incomeView.findViewById(R.id.others_button);
        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Others Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), OTHER_INCOME);
                startActivity(i);
            }
        });
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
        Log.d(TAG, "onDestroy() called");
    }
}
