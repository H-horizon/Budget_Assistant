package android.h.horizon.budget_assistant.expenses;

public class Rent extends Expense {
    public Rent(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        mExpenseTitle = "Rent";
    }
}
