package com.mattieapps.roommates.systems.sql_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andrewmattie on 2/25/15.
 */
@Deprecated
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_MATES = "mates";
    public static final String COLUMN_MATE_ID = "_id";
    public static final String COLUMN_MATE_NAME = "name";
    public static final String COLUMN_MATE_PRICE = "price";
    public static final String COLUMN_MATE_STATUS = "status";
    public static final String COLUMN_MATE_EMAIL = "email";

    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_EXPENSE_ID = "_id";
    public static final String COLUMN_EXPENSE_NAME = "name";
    public static final String COLUMN_EXPENSE_PRICE = "price";
    public static final String COLUMN_EXPENSE_STATUS = "status";
    public static final String COLUMN_EXPENSE_INVOLVED_PARTIES = "involvedParties";

    private static final String DB_NAME = "mates_finances.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_MATE_TABLE = "create table " + TABLE_MATES + "(" +
            COLUMN_MATE_ID + " integer primary key autoincrement, "
            + COLUMN_MATE_NAME + " text not null, "
            + COLUMN_MATE_PRICE + " text not null, "
            + COLUMN_MATE_STATUS + " text not null, "
            + COLUMN_MATE_EMAIL + " text not null)";

    private static final String CREATE_EXPENSE_TABLE = "create table " + TABLE_EXPENSES + "(" +
            COLUMN_EXPENSE_ID + " integer primary key autoincrement, "
            + COLUMN_EXPENSE_NAME + " text not null, "
            + COLUMN_EXPENSE_PRICE + " text not null, "
            + COLUMN_EXPENSE_STATUS + " text not null, "
            + COLUMN_EXPENSE_INVOLVED_PARTIES + " text not null)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MATE_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }
}
