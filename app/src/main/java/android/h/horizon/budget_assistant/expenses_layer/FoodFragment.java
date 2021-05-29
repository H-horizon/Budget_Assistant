package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FoodFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View expenseView = inflater.inflate(R.layout.fragment_expense, container,
                false);
        return expenseView;
    }
}
