package android.h.horizon.budget_assistant.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.h.horizon.budget_assistant.recycler_view.Transaction;

import java.sql.Date;
import java.util.UUID;

public class TransactionCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TransactionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Transaction getTransaction() {
        String uuidString = getString(getColumnIndex(TransactionDbSchema.Columns.UUID));
        String title = getString(getColumnIndex(TransactionDbSchema.Columns.TITLE));
        long date = getLong(getColumnIndex(TransactionDbSchema.Columns.DATE));
        String description = getString(getColumnIndex(TransactionDbSchema.Columns.DESCRIPTION));
        double amount = getDouble(getColumnIndex(TransactionDbSchema.Columns.AMOUNT));

        Transaction transaction = new Transaction(UUID.fromString(uuidString));
        transaction.setTitle(title);
        transaction.setDate(new Date(date));
        transaction.setDescription(description);
        transaction.setAmount(amount);
        return transaction;
    }
}
