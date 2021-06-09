package android.h.horizon.budget_assistant.dashboard;

import android.content.Intent;
import android.h.horizon.budget_assistant.Expenses.Education;
import android.h.horizon.budget_assistant.Expenses.Health;
import android.h.horizon.budget_assistant.Expenses.Leisure;
import android.h.horizon.budget_assistant.Expenses.Others;
import android.h.horizon.budget_assistant.Expenses.Travel;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.expenses_layer.EducationActivity;
import android.h.horizon.budget_assistant.expenses_layer.FoodActivity;
import android.h.horizon.budget_assistant.expenses_layer.HealthActivity;
import android.h.horizon.budget_assistant.expenses_layer.LeisureActivity;
import android.h.horizon.budget_assistant.expenses_layer.OthersActivity;
import android.h.horizon.budget_assistant.expenses_layer.RentActivity;
import android.h.horizon.budget_assistant.expenses_layer.SubscriptionActivity;
import android.h.horizon.budget_assistant.expenses_layer.TravelActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpensesFragment extends Fragment {
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
        setDateButton(expenseView);
        setFoodButton(expenseView);
        setEducationButton(expenseView);
        setTravelButton(expenseView);
        setHealthButton(expenseView);
        setLeisureButthon(expenseView);
        setOthersButton(expenseView);
        setRentButton(expenseView);
        setSubscriptionButton(expenseView);
        return expenseView;
    }

    private void setSubscriptionButton(View expenseView) {
        mSubscriptionButton = (Button) expenseView.findViewById(R.id.subscription_button);
        mSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(i);
            }
        });
    }

    private void setRentButton(View expenseView) {
        mRentButton = (Button) expenseView.findViewById(R.id.rent_button);
        mRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RentActivity.class);
                startActivity(i);
            }
        });
    }

    private void setOthersButton(View expenseView) {
        mOthersButton = (Button) expenseView.findViewById(R.id.other_button);
        mOthersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OthersActivity.class);
                startActivity(i);
            }
        });
    }

    private void setLeisureButthon(View expenseView) {
        mLeisureButton = (Button) expenseView.findViewById(R.id.leisure_button);
        mLeisureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LeisureActivity.class);
                startActivity(i);
            }
        });
    }

    private void setHealthButton(View expenseView) {
        mHealthButton = (Button) expenseView.findViewById(R.id.health_button);
        mHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), HealthActivity.class);
                startActivity(i);
            }
        });
    }

    private void setTravelButton(View expenseView) {
        mTravelButton = (Button) expenseView.findViewById(R.id.travel_button);
        mTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TravelActivity.class);
                startActivity(i);
            }
        });
    }

    private void setEducationButton(View expenseView) {
        mEducationButton = (Button) expenseView.findViewById(R.id.education_button);
        mEducationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EducationActivity.class);
                startActivity(i);
            }
        });
    }

    private void setFoodButton(View expenseView) {
        mFoodButton = (Button) expenseView.findViewById(R.id.food_button);
        mFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FoodActivity.class);
                startActivity(i);
            }
        });
    }

    private void setDateButton(View expenseView) {
        mDateButton = (Button) expenseView.findViewById(R.id.date_button);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mDateButton.setText(dateFormat.format(currentDate));
        mDateButton.setEnabled(false);
    }

}
