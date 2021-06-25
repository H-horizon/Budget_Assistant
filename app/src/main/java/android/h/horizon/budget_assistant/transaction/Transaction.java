package android.h.horizon.budget_assistant.transaction;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    public static final String NEW = "NEW";
    protected String mTitle;
    protected String mDescription;
    protected double mAmount;
    protected Date mDate;
    protected UUID mId;
    protected String mNew;

    public Transaction(String descriptionArg, double amountArg) {
        mDescription = descriptionArg;
        mAmount = amountArg;
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Transaction(UUID id) {
        mId = id;
        mDate = new Date();
        mNew = NEW;
    }

    public String getNew() {
        return mNew;
    }

    public void setNew(String aNew) {
        mNew = aNew;
    }

    public UUID getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }
}
