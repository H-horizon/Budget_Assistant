package android.h.horizon.budget_assistant.expenses;

public class Food extends Expense {
    public Food(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        mExpenseTitle = "Food";
    }

}
