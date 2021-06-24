package android.h.horizon.budget_assistant.transactions;

import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class TransactionListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TransactionListFragment();
    }
}
