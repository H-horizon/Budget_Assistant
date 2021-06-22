package android.h.horizon.budget_assistant.income_layer;

import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.expenses_layer.ExpenseFragment;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public abstract class IncomeActivity extends AppCompatActivity {
    private static final String TAG = "IncomeActivity";
    public static final String EXTRA_TRANSACTION_ID =
            "transaction_id";

    protected abstract void setActivityTitle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_income);
        setActivityTitle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.income_fragment_container);
        if (fragment == null) {
            UUID transactionId = (UUID) getIntent()
                    .getSerializableExtra(EXTRA_TRANSACTION_ID);
            fragment = IncomeFragment.newInstance(transactionId);
            fragmentManager.beginTransaction()
                    .add(R.id.income_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
