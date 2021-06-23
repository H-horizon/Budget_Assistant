package android.h.horizon.budget_assistant.income_layer;

import android.content.Context;
import android.content.Intent;

import java.util.UUID;

public class SalaryActivity extends IncomeActivity {
    public static final String EXTRA_TRANSACTION_ID =
            "transaction_id";

    public static Intent newIntent(Context packageContext, UUID transactionId) {
        Intent intent = new Intent(packageContext, SalaryActivity.class);
        intent.putExtra(EXTRA_TRANSACTION_ID, transactionId);
        return intent;
    }

    @Override
    protected void setActivityTitle() {
        setTitle("Salary");
    }


}
