package android.h.horizon.budget_assistant.incomes;

import java.util.Date;
import java.util.UUID;

public class Income {
    protected static String Income_TITLE;
    protected String mDescription;
    protected double mAmount;
    protected Date mDate;
    protected static UUID mId;


    public Income(String descriptionArg, double amountArg) {
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
}
