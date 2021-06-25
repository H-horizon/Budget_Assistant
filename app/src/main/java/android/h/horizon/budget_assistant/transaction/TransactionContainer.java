package android.h.horizon.budget_assistant.transaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.h.horizon.budget_assistant.database.TransactionCursorWrapper;
import android.h.horizon.budget_assistant.database.TransactionDbSchema;
import android.h.horizon.budget_assistant.database.TransactionsBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionContainer {

    private static TransactionContainer sTransactionList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TransactionContainer get(Context context) {
        if (sTransactionList == null) {
            sTransactionList = new TransactionContainer(context);
        }
        return sTransactionList;
    }

    private TransactionContainer(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TransactionsBaseHelper(mContext)
                .getWritableDatabase();
    }

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionCursorWrapper cursor = queryTransactions(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transactions.add(cursor.getTransaction());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return transactions;
    }

    public Transaction getTransaction(UUID id) {
        TransactionCursorWrapper cursor = queryTransactions(
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTransaction();
        } finally {
            cursor.close();
        }
    }

    public void addTransaction(Transaction transaction) {
        ContentValues values = getContentValues(transaction);
        mDatabase.insert(TransactionDbSchema.NAME, null, values);
    }

    private static ContentValues getContentValues(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TransactionDbSchema.Columns.UUID, transaction.getId().toString());
        values.put(TransactionDbSchema.Columns.TITLE, transaction.getTitle());
        values.put(TransactionDbSchema.Columns.DATE, transaction.getDate().getTime());
        values.put(TransactionDbSchema.Columns.DESCRIPTION, transaction.getDescription());
        values.put(TransactionDbSchema.Columns.AMOUNT, transaction.getAmount());
        values.put(TransactionDbSchema.Columns.NEW, transaction.getNew());
        return values;
    }

    public void updateTransaction(Transaction transaction) {
        String uuidString = transaction.getId().toString();
        ContentValues values = getContentValues(transaction);
        mDatabase.update(TransactionDbSchema.NAME, values,
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteTransaction(Transaction transaction) {
        String uuidString = transaction.getId().toString();
        mDatabase.delete(TransactionDbSchema.NAME,
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{uuidString});
    }

    private TransactionCursorWrapper queryTransactions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TransactionDbSchema.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TransactionCursorWrapper(cursor);
    }
}
