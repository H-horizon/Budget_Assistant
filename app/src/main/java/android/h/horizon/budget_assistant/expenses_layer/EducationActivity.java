package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.recycler_view.TransactionListFragment;

import androidx.fragment.app.Fragment;

public class EducationActivity extends ExpenseActivity {
    @Override
    protected void setActivityTitle() {
        setTitle("Education");
    }

    @Override
    protected Fragment setFragment() {
        return new TransactionListFragment("Education");
    }
}
