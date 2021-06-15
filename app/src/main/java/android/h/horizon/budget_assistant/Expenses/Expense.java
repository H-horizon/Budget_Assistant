package android.h.horizon.budget_assistant.Expenses;

import java.util.Date;

public class Expense {
    protected static String EXPENSE_TITLE;
    protected String mDescription;
    protected float mAmount;
    protected Date mDate;

    public Expense(String descriptionArg, float amountArg) {
        mDescription = descriptionArg;
        mAmount = amountArg;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float amount) {
        this.mAmount = amount;
    }
}
