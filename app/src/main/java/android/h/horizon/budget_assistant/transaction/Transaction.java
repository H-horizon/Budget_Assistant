package android.h.horizon.budget_assistant.transaction;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a class of attributes that can be used to represent the required data
 */
public class Transaction {
    public static final String NEW = "NEW";
    protected String mTitle;
    protected String mDescription;
    protected double mAmount;
    protected Date mDate;
    protected UUID mId;
    protected String mNew;

    public Transaction(UUID id) {
        mId = id;
        mDate = new Date();
        mNew = NEW;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getNew() {
        return mNew;
    }

    public void setNew(String aNew) {
        mNew = aNew;
    }
}
