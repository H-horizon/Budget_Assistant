package android.h.horizon.budget_assistant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Represents the base helper of the database
 */
public class TransactionsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "transactionsBase.db";
    private static final String TAG = "BaseHelper";

    /**
     * Create the base helper for the database
     *
     * @param context gets the state of the application
     */
    public TransactionsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        Log.d(TAG, "TransactionsBaseHelper(Context context) called");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate(SQLiteDatabase db) called");
        db.execSQL("create table " + TransactionDbSchema.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TransactionDbSchema.Columns.UUID + ", " +
                TransactionDbSchema.Columns.TITLE + ", " +
                TransactionDbSchema.Columns.DATE + ", " +
                TransactionDbSchema.Columns.DESCRIPTION + ", " +
                TransactionDbSchema.Columns.AMOUNT + ", " +
                TransactionDbSchema.Columns.NEW +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) called");
    }

}
