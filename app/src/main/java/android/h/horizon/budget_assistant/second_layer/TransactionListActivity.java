package android.h.horizon.budget_assistant.second_layer;

import android.content.Context;
import android.content.Intent;
import android.h.horizon.budget_assistant.abstract_classes.SingleFragmentActivity;
import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * Contains the fragment that is used to set up the RecyclerView
 */
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

    /**
     * Passes arguments from the dashboard to the second layer
     *
     * @param packageContext   is the current state of the application
     * @param transactionTitle is the title of the transaction the user has chosen(clicked)
     * @return the mechanism that allows the activity to occur
     */
    public static Intent newIntent(Context packageContext, String transactionTitle) {
        Log.d(TAG, "newIntent(Context packageContext, String transactionTitle) called");
        Intent intent = new Intent(packageContext, TransactionListActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionTitle);
        return intent;
    }
}
