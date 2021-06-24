package android.h.horizon.budget_assistant.transaction;

import android.content.Context;
import android.content.Intent;
import android.h.horizon.budget_assistant.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class TransactionPagerActivity extends AppCompatActivity {
    private static final String EXTRA_TRANSACTION_TITLE = "transaction_title";
    private ViewPager mViewPager;
    private List<Transaction> mTransactionList;
    private static final String EXTRA_TRANSACTION_ID =
            "transaction_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_pager);
        UUID transactionId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_TRANSACTION_ID);
        String transactionTitle = (String) getIntent()
                .getSerializableExtra(EXTRA_TRANSACTION_TITLE);
        setTitle(transactionTitle);
        mViewPager = (ViewPager) findViewById(R.id.activity_transaction_pager_view_pager);
        mTransactionList = TransactionContainer.get(this).getTransactions();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Transaction transaction = mTransactionList.get(position);
                return TransactionFragment.newInstance(transaction.getId());
            }

            @Override
            public int getCount() {
                return mTransactionList.size();
            }
        });

        for (int i = 0; i < mTransactionList.size(); i++) {
            if (mTransactionList.get(i).getId().equals(transactionId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID transactionId,
                                   String transactionTitle) {
        Intent intent = new Intent(packageContext, TransactionPagerActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionId);
        intent.putExtra(EXTRA_TRANSACTION_TITLE, transactionTitle);
        return intent;
    }


}
