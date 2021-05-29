package android.h.horizon.budget_assistant.expenses_layer;

import android.h.horizon.budget_assistant.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setTitle("Food");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.expense_fragment_container);
        if (fragment == null) {
            fragment = new FoodFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.expense_fragment_container, fragment)
                    .commit();
        }
    }
}
