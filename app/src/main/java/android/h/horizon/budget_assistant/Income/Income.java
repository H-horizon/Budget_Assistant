package android.h.horizon.budget_assistant.Income;

import java.util.Date;

public class Income {
    protected static String Income_TITLE;
    protected String mDescription;
    protected float mAmount;
    protected Date mDate;

    public Income(String descriptionArg, float amountArg) {
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
