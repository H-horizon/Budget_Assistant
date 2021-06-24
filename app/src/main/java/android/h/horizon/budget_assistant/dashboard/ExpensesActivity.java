package android.h.horizon.budget_assistant.dashboard;


import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ExpensesActivity extends SingleFragmentActivity {
    private static final String TAG = "ExpensesActivity";

    @Override
    protected Fragment createFragment() {
        return new ExpensesFragment();
    }
}
