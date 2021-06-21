package android.h.horizon.budget_assistant.expenses;

import java.util.Date;
import java.util.UUID;

public class Expense {
    protected String mExpenseTitle;
    protected String mDescription;
    protected double mAmount;
    protected Date mDate;
    protected static UUID mId;

    public Expense(String descriptionArg, double amountArg) {
        mDescription = descriptionArg;
        mAmount = amountArg;
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        this.mAmount = amount;
    }

    public void setExpenseTitle(String expenseTitle) {
        mExpenseTitle = expenseTitle;
    }

    public String getExpenseTitle() {
        return mExpenseTitle;
    }
}
