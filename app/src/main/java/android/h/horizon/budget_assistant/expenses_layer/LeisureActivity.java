package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.recycler_view.TransactionListFragment;

import androidx.fragment.app.Fragment;

public class LeisureActivity extends ExpenseActivity {
    @Override
    protected void setActivityTitle() {
        setTitle("Leisure and Entertainment");
    }

    @Override
    protected Fragment setFragment() {
        return new TransactionListFragment("Leisure");
    }
}
