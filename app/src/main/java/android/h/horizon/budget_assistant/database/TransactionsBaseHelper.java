package android.h.horizon.budget_assistant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "transactionsBase.db";
    public TransactionsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TransactionDbSchema.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TransactionDbSchema.Columns.UUID + ", " +
                TransactionDbSchema.Columns.TITLE + ", " +
                TransactionDbSchema.Columns.DATE + ", " +
                TransactionDbSchema.Columns.DESCRIPTION + ", " +
                TransactionDbSchema.Columns.AMOUNT +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
