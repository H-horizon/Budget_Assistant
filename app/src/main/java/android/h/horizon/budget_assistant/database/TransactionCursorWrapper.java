package android.h.horizon.budget_assistant.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.util.Log;

import java.sql.Date;
import java.util.UUID;

/**
 * Represents the cursor wrapper for the Transaction Db
 */
public class TransactionCursorWrapper extends CursorWrapper {

    private static final String TAG = "CursorWrapper";

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TransactionCursorWrapper(Cursor cursor) {
        super(cursor);
        Log.d(TAG, "TransactionCursorWrapper(Cursor cursor) called");
    }

    /**
     * @return a Transaction record from the database
     */
    public Transaction getTransaction() {
        Log.d(TAG, "getTransaction() called");
        String uuidString = getString(getColumnIndex(TransactionDbSchema.Columns.UUID));
        String title = getString(getColumnIndex(TransactionDbSchema.Columns.TITLE));
        long date = getLong(getColumnIndex(TransactionDbSchema.Columns.DATE));
        String description = getString(getColumnIndex(TransactionDbSchema.Columns.DESCRIPTION));
        double amount = getDouble(getColumnIndex(TransactionDbSchema.Columns.AMOUNT));
        String newValue = getString(getColumnIndex(TransactionDbSchema.Columns.NEW));

        Transaction transaction = new Transaction(UUID.fromString(uuidString));
        transaction.setTitle(title);
        transaction.setDate(new Date(date));
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setNew(newValue);
        return transaction;
    }
}
