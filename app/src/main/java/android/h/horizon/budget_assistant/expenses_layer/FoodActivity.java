package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.recycler_view.TransactionListFragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FoodActivity extends ExpenseActivity {
    @Override
    protected void setActivityTitle() {
        setTitle("Food");
    }

    @Override
    protected Fragment setFragment() {
        return new TransactionListFragment("Food");
    }
}
