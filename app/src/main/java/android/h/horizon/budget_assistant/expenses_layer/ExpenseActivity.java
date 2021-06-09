package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class ExpenseActivity extends AppCompatActivity {
    protected abstract void setActivityTitle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setActivityTitle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.expense_fragment_container);
        if (fragment == null) {
            fragment = new ExpenseFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.expense_fragment_container, fragment)
                    .commit();
        }
    }
}
