package android.h.horizon.budget_assistant.Expenses;

public class Subscriptions extends Expense {
    public Subscriptions(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        EXPENSE_TITLE = "Subscriptions and Insurance";
    }
}
