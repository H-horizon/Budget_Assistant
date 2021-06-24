package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.content.Intent;
import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TransactionListActivity extends SingleFragmentActivity {
    private static final String EXTRA_TRANSACTION_ID = "transaction_title";
    private String mTransactionTitle;

    @Override
    protected Fragment createFragment() {
        mTransactionTitle = getIntent().getStringExtra(EXTRA_TRANSACTION_ID);
        return TransactionListFragment.newInstance(mTransactionTitle);
    }

    public static Intent newIntent(Context packageContext, String transactionTitle) {
        Intent intent = new Intent(packageContext, TransactionListActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionTitle);
        return intent;
    }
}
