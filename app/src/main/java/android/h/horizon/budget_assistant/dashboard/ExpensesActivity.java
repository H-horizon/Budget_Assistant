package android.h.horizon.budget_assistant.dashboard;


import android.h.horizon.budget_assistant.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ExpensesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.expenses_fragment_container);
        if (fragment == null) {
            fragment = new ExpensesFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.expenses_fragment_container, fragment)
                    .commit();
        }
    }
}
