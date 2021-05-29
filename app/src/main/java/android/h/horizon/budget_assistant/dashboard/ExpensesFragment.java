package android.h.horizon.budget_assistant.dashboard;

import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.expenses_layer.FoodActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View expenseView = inflater.inflate(R.layout.fragment_expenses, container,
                false);
        setDateButton(expenseView);
        mFoodButton = (Button) expenseView.findViewById(R.id.food_button);
        mFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FoodActivity.class);
                startActivity(i);
            }
        });
        return expenseView;
    }

    private void setDateButton(View expenseView) {
        mDateButton = (Button) expenseView.findViewById(R.id.date_button);
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mDateButton.setText(dateFormat.format(currentDate));
        mDateButton.setEnabled(false);
    }

}
