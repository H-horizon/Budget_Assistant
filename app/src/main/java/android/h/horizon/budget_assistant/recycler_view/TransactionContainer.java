package android.h.horizon.budget_assistant.recycler_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.h.horizon.budget_assistant.database.TransactionCursorWrapper;
import android.h.horizon.budget_assistant.database.TransactionDbSchema;
import android.h.horizon.budget_assistant.database.TransactionsBaseHelper;


import java.util.ArrayList;
import java.util.Date;
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

    public  static void setTestData() {
        setOneTestData(10.1, "Education", "Tuition fee", new Date());
        setOneTestData(11.1, "Food", "Lunch", new Date());
        setOneTestData(12.1, "Health", "Medicines", new Date());
        setOneTestData(13.1, "Leisure", "Movies", new Date());
        setOneTestData(14.1, "Others", "Gift", new Date());
        setOneTestData(15.1, "Rent", "Monthly Rent", new Date());
        setOneTestData(16.1, "Subscription", "Netflix", new Date());
        setOneTestData(19.1, "Travelling", "Clementi", new Date());
    }

    private static void setOneTestData(double amount, String title, String description, Date date) {
        Transaction transaction = new Transaction(UUID.randomUUID());
        transaction.setAmount(amount);
        transaction.setTitle(title);
        transaction.setDescription(description);
        transaction.setDate(date);
        sTransactionList.addTransaction(transaction);
    }

    private TransactionContainer(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TransactionsBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addTransaction(Transaction transaction) {
        ContentValues values = getContentValues(transaction);
        mDatabase.insert(TransactionDbSchema.NAME, null, values);
    }

    public List<Transaction> getTransactionList() {
        List<Transaction> transactions = new ArrayList<>();
        TransactionCursorWrapper cursorWrapper = queryCrimes(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                transactions.add(cursorWrapper.getTransaction());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return new ArrayList<>();
    }


    public Transaction getTransaction(UUID id) {
        TransactionCursorWrapper cursor = queryCrimes(
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

    private static ContentValues getContentValues(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(TransactionDbSchema.Columns.UUID, transaction.getId().toString());
        values.put(TransactionDbSchema.Columns.TITLE, transaction.getTitle());
        values.put(TransactionDbSchema.Columns.DATE, transaction.getDate().getTime());
        values.put(TransactionDbSchema.Columns.DESCRIPTION, transaction.getDescription());
        values.put(TransactionDbSchema.Columns.AMOUNT, transaction.getAmount());
        return values;
    }

    public void updateTransaction(Transaction transaction) {
        String uuidString = transaction.getId().toString();
        ContentValues values = getContentValues(transaction);
        mDatabase.update(TransactionDbSchema.NAME, values,
                TransactionDbSchema.Columns.UUID + " = ?",
                new String[]{uuidString});
    }

    private TransactionCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
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

