package android.h.horizon.budget_assistant.expenses;

public class Travel extends Expense{
    public Travel(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        mExpenseTitle = "Travelling";
    }
}
