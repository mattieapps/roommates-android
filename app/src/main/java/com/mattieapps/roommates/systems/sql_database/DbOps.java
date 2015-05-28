package com.mattieapps.roommates.systems.sql_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewmattie on 2/25/15.
 */
@Deprecated
public class DbOps {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allMateColumns = {DbHelper.COLUMN_MATE_ID,
            DbHelper.COLUMN_MATE_NAME, DbHelper.COLUMN_MATE_PRICE,
            DbHelper.COLUMN_MATE_STATUS, DbHelper.COLUMN_MATE_EMAIL};

    private String[] allExpenseColumns = {DbHelper.COLUMN_EXPENSE_ID,
            DbHelper.COLUMN_EXPENSE_NAME, DbHelper.COLUMN_EXPENSE_PRICE,
            DbHelper.COLUMN_EXPENSE_STATUS, DbHelper.COLUMN_EXPENSE_INVOLVED_PARTIES};

    public DbOps(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Open Database
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close Database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Saves Mate object
     *
     * @param name   Mate name
     * @param price  Mate price
     * @param status Mate status
     * @param email  Mate email
     */
    public void saveMate(String name, String price, String status, String email) {
        //run sql to save obj
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_MATE_NAME, name);
        contentValues.put(DbHelper.COLUMN_MATE_PRICE, price);
        contentValues.put(DbHelper.COLUMN_MATE_STATUS, status);
        contentValues.put(DbHelper.COLUMN_MATE_EMAIL, email);

        long insertId = database.insert(DbHelper.TABLE_MATES, null, contentValues);
        Cursor cursor = database.query(DbHelper.TABLE_MATES, allMateColumns, DbHelper.COLUMN_MATE_ID + " = "
                + insertId, null, null, null, null);
        cursor.moveToFirst();
        Mate newMate = cursorToMate(cursor);
        cursor.close();
    }

    /**
     * Delete Mate
     *
     * @param id Mate object
     */
    public void deleteMate(int id) {
        //run sql to delete obj
        database.delete(DbHelper.TABLE_MATES, DbHelper.COLUMN_MATE_ID + " = " + id, null);
    }

    /**
     * Edit Mate object using ID
     *
     * @param name   Mate name
     * @param price  Mate price
     * @param status Mate status
     * @param email  Mate email
     * @param id     Mate id
     */
    public void editMate(String name, String price, String status, String email, int id) {
        //run sql to edit obj
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_MATE_NAME, name);
        contentValues.put(DbHelper.COLUMN_MATE_PRICE, price);
        contentValues.put(DbHelper.COLUMN_MATE_STATUS, status);
        contentValues.put(DbHelper.COLUMN_MATE_EMAIL, email);

        database.update(DbHelper.TABLE_MATES, contentValues, DbHelper.COLUMN_MATE_ID + " = " + id, null);
    }

    /**
     * Return all Mates in Db
     *
     * @return matesList returned
     */
    public List<Mate> getAllMates() {
        List<Mate> matesList = new ArrayList<Mate>();

        Cursor cursor = database.query(DbHelper.TABLE_MATES, allMateColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Mate mate = cursorToMate(cursor);
            matesList.add(mate);
            cursor.moveToNext();
        }

        cursor.close();

        return matesList;
    }

    /**
     * Get all mates from db
     *
     * @return mCursor
     */
    public Cursor fetchAllMates() {
        Cursor mCursor = database.query(DbHelper.TABLE_MATES, allMateColumns,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Custom cursor constructor
     *
     * @param cursor Cursor object
     * @return Mate object
     */
    private Mate cursorToMate(Cursor cursor) {
        Mate mate = new Mate();
        mate.setId(cursor.getLong(0));
        mate.setName(cursor.getString(1));
        mate.setPrice(cursor.getString(2));
        mate.setStatus(cursor.getString(3));
        mate.setEmail(cursor.getString(4));

        return mate;
    }

    //Expense Ops

    /**
     * Saves Expense Object
     *
     * @param name            Expense name
     * @param price           Expense price
     * @param status          Expense status(paid,unpaid,settled)
     * @param involvedParties Involved parties in string format
     */
    public void saveExpense(String name, String price, String status, String involvedParties) {
        //run sql to save obj
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_EXPENSE_NAME, name);
        contentValues.put(DbHelper.COLUMN_EXPENSE_PRICE, price);
        contentValues.put(DbHelper.COLUMN_EXPENSE_STATUS, status);
        contentValues.put(DbHelper.COLUMN_EXPENSE_INVOLVED_PARTIES, involvedParties);

        long insertId = database.insert(DbHelper.TABLE_EXPENSES, null, contentValues);
        Cursor cursor = database.query(DbHelper.TABLE_EXPENSES, allExpenseColumns, DbHelper.COLUMN_EXPENSE_ID + " = "
                + insertId, null, null, null, null);
        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();
    }

    /**
     * Delete Expense
     *
     * @param id Expense id
     */
    public void deleteExpense(int id) {
        //run sql to delete obj
        database.delete(DbHelper.TABLE_EXPENSES, DbHelper.COLUMN_EXPENSE_ID + " = " + id, null);
    }

    /**
     * Edit Expense using ID
     *
     * @param name            Expense name
     * @param price           Expense price
     * @param status          Expense status(paid,unpaid,settled)
     * @param involvedParties Involved parties in string format
     * @param id              ID of Expense object
     */
    public void editExpense(String name, String price, String status, String involvedParties, int id) {
        //run sql to edit obj
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COLUMN_EXPENSE_NAME, name);
        contentValues.put(DbHelper.COLUMN_EXPENSE_PRICE, price);
        contentValues.put(DbHelper.COLUMN_EXPENSE_STATUS, status);
        contentValues.put(DbHelper.COLUMN_EXPENSE_INVOLVED_PARTIES, involvedParties);

        database.update(DbHelper.TABLE_EXPENSES, contentValues, DbHelper.COLUMN_EXPENSE_ID + " = " + id, null);
    }

    /**
     * Return all expenses in db
     *
     * @return expenseList
     */
    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<Expense>();

        Cursor cursor = database.query(DbHelper.TABLE_EXPENSES, allExpenseColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Expense expense = cursorToExpense(cursor);
            expenseList.add(expense);
            cursor.moveToNext();
        }

        cursor.close();

        return expenseList;
    }

    /**
     * Return all expenses to our adapter
     *
     * @return mCursor
     */
    public Cursor fetchAllExpenses() {
        Cursor mCursor = database.query(DbHelper.TABLE_EXPENSES, allExpenseColumns,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Custom cursor constructor
     *
     * @param cursor Cursor object
     * @return Expense object
     */
    private Expense cursorToExpense(Cursor cursor) {
        Expense expense = new Expense();
        expense.setId(cursor.getLong(0));
        expense.setName(cursor.getString(1));
        expense.setPrice(cursor.getString(2));
        expense.setStatus(cursor.getString(3));
        expense.setInvolvedParties(cursor.getString(4));

        return expense;
    }
}