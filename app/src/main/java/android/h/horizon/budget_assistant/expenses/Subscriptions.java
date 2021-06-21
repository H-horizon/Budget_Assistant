package android.h.horizon.budget_assistant.expenses;

public class Subscriptions extends Expense {
    public Subscriptions(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        mExpenseTitle = "Subscriptions and Insurance";
    }
}
