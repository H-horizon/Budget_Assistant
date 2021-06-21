package android.h.horizon.budget_assistant.expenses;

public class Health extends Expense {
    public Health(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        mExpenseTitle = "Health";
    }
}
