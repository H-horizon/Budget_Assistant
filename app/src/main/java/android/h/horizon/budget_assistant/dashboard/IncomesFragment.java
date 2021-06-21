package android.h.horizon.budget_assistant.dashboard;

import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.income_layer.OthersActivity;
import android.h.horizon.budget_assistant.income_layer.AllowanceActivity;
import android.h.horizon.budget_assistant.income_layer.SalaryActivity;
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

public class IncomesFragment extends Fragment {
    private static final String TAG = "IncomesFragment";
    private Button mDateButton;
    private Button mSalaryButton;
    private Button mAllowanceButton;
    private Button mOthersButton;

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

    private void setSalaryButton(View incomeView) {
        Log.d(TAG, "setSalaryButton called");
        mSalaryButton = (Button) incomeView.findViewById(R.id.salary_button);
        mSalaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Salary Button clicked");
                Intent i = new Intent(getActivity(), SalaryActivity.class);
                startActivity(i);
                //Implement RecyclerView
            }
        });
    }

    private void setAllowanceButton(View incomeView) {
        Log.d(TAG, "setAllowanceButton called");
        mAllowanceButton = (Button) incomeView.findViewById(R.id.allowance_button);
        mAllowanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Allowance Button clicked");
                Intent i = new Intent(getActivity(), AllowanceActivity.class);
                startActivity(i);
                //Implement RecyclerView
            }
        });
    }

    private void setOthersButton(View incomeView) {
        Log.d(TAG, "setOthersButton called");
        mOthersButton = (Button) incomeView.findViewById(R.id.others_button);
        mOthersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Others Button clicked");
                Intent i = new Intent(getActivity(), OthersActivity.class);
                startActivity(i);
            }
        });
    }


    private void setDateButton(View incomeView) {
        Log.d(TAG, "setDateButton called");
        mDateButton = (Button) incomeView.findViewById(R.id.date_button);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mDateButton.setText(dateFormat.format(currentDate));
        mDateButton.setEnabled(false);
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
