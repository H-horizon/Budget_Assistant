package android.h.horizon.budget_assistant.recycler_view;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TransactionListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TransactionListFragment("Transaction");
    }
//    public static Intent newIntent(Context packageContext, boolean isExpense) {
//        Intent i = new Intent(packageContext, TransactionListActivity.class);
//        i.putExtra(TAG, isExpense);
//        return i;
//    }
}
