package android.h.horizon.budget_assistant.Expenses;

public class Rent extends Expense {
    public Rent(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        EXPENSE_TITLE = "Rent";
    }
}
