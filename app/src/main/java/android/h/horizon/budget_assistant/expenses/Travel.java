package android.h.horizon.budget_assistant.Expenses;

public class Travel extends Expense{
    public Travel(String descriptionArg, float amountArg) {
        super(descriptionArg, amountArg);
        EXPENSE_TITLE = "Travelling";
    }
}
