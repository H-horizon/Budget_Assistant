package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.content.Intent;
import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class TransactionListActivity extends SingleFragmentActivity {
    private static final String EXTRA_TRANSACTION_ID = "transaction_title";
    public static final String LIST = " List";
    private static final String TAG = "TransactionListActivity";
    private String mTransactionTitle;

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "createFragment() called");
        mTransactionTitle = getIntent().getStringExtra(EXTRA_TRANSACTION_ID);
        setTitle(mTransactionTitle + LIST);
        return TransactionListFragment.newInstance(mTransactionTitle);
    }

    public static Intent newIntent(Context packageContext, String transactionTitle) {
        Log.d(TAG, "newIntent(Context packageContext, String transactionTitle) called");
        Intent intent = new Intent(packageContext, TransactionListActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionTitle);
        return intent;
    }
}
