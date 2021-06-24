package android.h.horizon.budget_assistant.dashboard;


import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;


import androidx.fragment.app.Fragment;

public class ExpensesActivity extends SingleFragmentActivity {
    private static final String TAG = "ExpensesActivity";

    @Override
    protected Fragment createFragment() {
        return new ExpensesFragment();
    }
}
