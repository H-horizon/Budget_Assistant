package android.h.horizon.budget_assistant.expenses_layer;

import android.content.Context;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.h.horizon.budget_assistant.recycler_view.TransactionListActivity;
import android.h.horizon.budget_assistant.recycler_view.TransactionListFragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public class FoodActivity extends ExpenseActivity {
    private static String TAG = "FoodActivity";
    public static final String EXTRA_TRANSACTION_ID = "transaction_id";

    public static Intent newIntent(Context packageContext, UUID transactionId) {
        Intent intent = new Intent(packageContext, FoodActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionId);
        return intent;
    }
    @Override
    protected void setActivityTitle() {
        setTitle("Food");
    }

    @Override
    protected Fragment setFragment() {
        return new TransactionListFragment("Food");
    }

}
