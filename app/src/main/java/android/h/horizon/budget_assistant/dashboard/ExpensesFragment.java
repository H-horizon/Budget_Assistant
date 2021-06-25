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

import static android.h.horizon.budget_assistant.transaction.TransactionNames.*;

public class ExpensesFragment extends Fragment {
    private static final String TAG = "ExpensesFragment";

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

    private void setDateButton(View expenseView) {
        Log.d(TAG, "setDateButton called");
        Button dateButton = (Button) expenseView.findViewById(R.id.date_button);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateButton.setText(dateFormat.format(currentDate));
        dateButton.setEnabled(false);
    }

    private void setFoodButton(View expenseView) {
        Log.d(TAG, "setFoodButton called");
        Button foodButton = (Button) expenseView.findViewById(R.id.food_button);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Food Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), FOOD);
                startActivity(i);
            }
        });
    }

    private void setEducationButton(View expenseView) {
        Log.d(TAG, "setEducationButton called");
        Button educationButton = (Button) expenseView.findViewById(R.id.education_button);
        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Education Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), EDUCATION);
                startActivity(i);
            }
        });
    }

    private void setTravelButton(View expenseView) {
        Log.d(TAG, "setTravelButton called");
        Button travelButton = (Button) expenseView.findViewById(R.id.travel_button);
        travelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Travel Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), TRAVELLING);
                startActivity(i);
            }
        });
    }

    private void setHealthButton(View expenseView) {
        Log.d(TAG, "setHeathButton called");
        Button healthButton = (Button) expenseView.findViewById(R.id.health_button);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Health Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), HEALTH);
                startActivity(i);
            }
        });
    }

    private void setLeisureButton(View expenseView) {
        Log.d(TAG, "setLeisureButton called");
        Button leisureButton = (Button) expenseView.findViewById(R.id.leisure_button);
        leisureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Leisure Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), LEISURE);
                startActivity(i);
            }
        });
    }

    private void setOthersButton(View expenseView) {
        Log.d(TAG, "setOthersButton called");
        Button othersButton = (Button) expenseView.findViewById(R.id.other_button);
        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Others Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), OTHER_EXPENSES);
                startActivity(i);
            }
        });
    }

    private void setRentButton(View expenseView) {
        Log.d(TAG, "setRentButton called");
        Button rentButton = (Button) expenseView.findViewById(R.id.rent_button);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Rent Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), RENT);
                startActivity(i);
            }
        });
    }

    private void setSubscriptionButton(View expenseView) {
        Log.d(TAG, "setSubscriptionButton called");
        Button subscriptionButton = (Button) expenseView.findViewById(R.id.subscription_button);
        subscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Subscription Button clicked");
                Intent i = TransactionListActivity.newIntent(getActivity(), SUBSCRIPTION);
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
