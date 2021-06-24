package android.h.horizon.budget_assistant.dashboard;

import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.transactions.TransactionListActivity;
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

public class ExpensesFragment extends Fragment {
    private static final String TAG = "ExpensesFragment";
    private Button mDateButton;
    private Button mFoodButton;
    private Button mEducationButton;
    private Button mHealthButton;
    private Button mLeisureButton;
    private Button mOthersButton;
    private Button mRentButton;
    private Button mSubscriptionButton;
    private Button mTravelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View expenseView = inflater.inflate(R.layout.fragment_expenses, container,
                false);
        Log.d(TAG, "onCreateView(LayoutInflater, Container, Bundle) called");
        setDateButton(expenseView);
        setFoodButton(expenseView);
        setEducationButton(expenseView);
        setTravelButton(expenseView);
        setHealthButton(expenseView);
        setLeisureButton(expenseView);
        setOthersButton(expenseView);
        setRentButton(expenseView);
        setSubscriptionButton(expenseView);
        return expenseView;
    }

    private void setSubscriptionButton(View expenseView) {
        Log.d(TAG, "setSubscriptionButton called");
        mSubscriptionButton = (Button) expenseView.findViewById(R.id.subscription_button);
        mSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscription Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setRentButton(View expenseView) {
        Log.d(TAG, "setRentButton called");
        mRentButton = (Button) expenseView.findViewById(R.id.rent_button);
        mRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Rent Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setOthersButton(View expenseView) {
        Log.d(TAG, "setOthersButton called");
        mOthersButton = (Button) expenseView.findViewById(R.id.other_button);
        mOthersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Others Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setLeisureButton(View expenseView) {
        Log.d(TAG, "setLeisureButton called");
        mLeisureButton = (Button) expenseView.findViewById(R.id.leisure_button);
        mLeisureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Leisure Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setHealthButton(View expenseView) {
        Log.d(TAG, "setHeathButton called");
        mHealthButton = (Button) expenseView.findViewById(R.id.health_button);
        mHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Health Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setTravelButton(View expenseView) {
        Log.d(TAG, "setTravelButton called");
        mTravelButton = (Button) expenseView.findViewById(R.id.travel_button);
        mTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Travel Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setEducationButton(View expenseView) {
        Log.d(TAG, "setEducationButton called");
        mEducationButton = (Button) expenseView.findViewById(R.id.education_button);
        mEducationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Education Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setFoodButton(View expenseView) {
        Log.d(TAG, "setFoodButton called");
        mFoodButton = (Button) expenseView.findViewById(R.id.food_button);
        mFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Food Button clicked");
                Intent i = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setDateButton(View expenseView) {
        Log.d(TAG, "setDateButton called");
        mDateButton = (Button) expenseView.findViewById(R.id.date_button);
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
