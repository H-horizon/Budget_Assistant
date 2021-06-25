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

/**
 * This class is a singleton used to manipulate data of Type Transaction
 */
public class TransactionContainer {

    private static TransactionContainer sTransactionContainer;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Gets a singleton of TransactionContainer
     *
     * @param context represents the current state of the application
     * @return a singleton representing this class
     */
    public static TransactionContainer get(Context context) {
        if (sTransactionContainer == null) {
            sTransactionContainer = new TransactionContainer(context);
        }
        return sTransactionContainer;
    }

    /**
     * Traverse the all records in the database and stores them in an ArrayList
     *
     * @return a list of all transactions stored in the database
     */
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

    /**
     * Searches a transaction record based on the primary key
     *
     * @param id is the unique identifier of a record in the database
     * @return a Transaction object
     */
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

    /**
     * Searches for transactions record based on their title
     *
     * @param title is the filter
     * @return a list of transactions having the required title
     */
    public List<Transaction> getTransactions(String title) {
        List<Transaction> initialTransactionList = getTransactions();
        List<Transaction> transactionList = new ArrayList<>();
        for (Transaction transaction : initialTransactionList) {
            if (transaction.getTitle().equals(title)) {
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }

    /**
     * Adds a Transaction object to the database
     *
     * @param transaction is the object to be added
     */
    public void addTransaction(Transaction transaction) {
        ContentValues values = getContentValues(transaction);
        mDatabase.insert(TransactionDbSchema.NAME, null, values);
    }

    /**
     * Stores changes to a record into the database based on the primary key
     *
     * @param transaction is the updated object
     */
    public void updateTransaction(Transaction transaction) {
        String uuidString = transaction.getId().toString();
        ContentValues values = getContentValues(transaction);
        mDatabase.update(TransactionDbSchema.NAME, values,
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{uuidString});
    }

    /**
     * Deletes a transaction record from the database based on the primary key
     *
     * @param transaction is the object to deleted
     */
    public void deleteTransaction(Transaction transaction) {
        String uuidString = transaction.getId().toString();
        mDatabase.delete(TransactionDbSchema.NAME,
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{uuidString});
    }

    private TransactionContainer(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TransactionsBaseHelper(mContext)
                .getWritableDatabase();
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
}
