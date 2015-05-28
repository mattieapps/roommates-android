package com.mattieapps.roommates;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.mattieapps.roommates.model.database.Expense;
import com.mattieapps.roommates.model.database.Mate;
import com.mattieapps.roommates.systems.BaseActivity;
import com.mattieapps.roommates.systems.sql_database.DbHelper;
import com.mattieapps.roommates.systems.sql_database.DbOps;

import java.sql.SQLException;

import io.realm.Realm;


public class MainActivity extends BaseActivity {

    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Begin SQL Database Migration
         */

        DbOps dbOps = new DbOps(getApplicationContext());
        try {
            dbOps.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dbOps.getAllMates().size() == 0 && dbOps.getAllExpenses().size() == 0) {
            startActivity();
        }
        
        if (dbOps.getAllMates().size() != 0) {
            int mateCount = dbOps.getAllMates().size();

            while (mateCount >= 1) {
                String[] allMateColumns = {DbHelper.COLUMN_MATE_ID,
                        DbHelper.COLUMN_MATE_NAME, DbHelper.COLUMN_MATE_PRICE,
                        DbHelper.COLUMN_MATE_STATUS, DbHelper.COLUMN_MATE_EMAIL};

                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                final Cursor cursor = database.query(DbHelper.TABLE_MATES, allMateColumns, null, null, null, null, null);
                cursor.moveToNext();

                Realm realm = Realm.getInstance(getApplicationContext());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int autoIncrementId = (int) (realm.where(Mate.class).maximumInt("id")) + 1;

                        Mate mate = realm.createObject(Mate.class);
                        mate.setId(autoIncrementId);
                        mate.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MATE_NAME)));
                        mate.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MATE_PRICE)));
                        mate.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MATE_STATUS)));
                        mate.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MATE_EMAIL)));
                    }
                });
                dbOps.deleteMate(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MATE_ID)));

                mateCount--;
            }

            startActivity();
        }

        if (dbOps.getAllExpenses().size() != 0) {
            int expenseCount = dbOps.getAllExpenses().size();

            while (expenseCount >= 1) {
                String[] allExpensesColumns = {DbHelper.COLUMN_EXPENSE_ID,
                        DbHelper.COLUMN_EXPENSE_NAME, DbHelper.COLUMN_EXPENSE_PRICE,
                        DbHelper.COLUMN_EXPENSE_STATUS, DbHelper.COLUMN_EXPENSE_INVOLVED_PARTIES};

                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                final Cursor cursor = database.query(DbHelper.TABLE_EXPENSES, allExpensesColumns, null, null, null, null, null);
                cursor.moveToNext();

                Realm realm = Realm.getInstance(getApplicationContext());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int autoIncrementId = (int) (realm.where(Expense.class).maximumInt("id")) + 1;

                        Expense expense = realm.createObject(Expense.class);
                        expense.setId(autoIncrementId);
                        expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXPENSE_NAME)));
                        expense.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXPENSE_PRICE)));
                        expense.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXPENSE_STATUS)));
                        expense.setInvolvedParties(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXPENSE_INVOLVED_PARTIES)));
                    }
                });
                dbOps.deleteExpense(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXPENSE_ID)));

                expenseCount--;
            }

            startActivity();
        }

        /*
            End SQL Database Migration
         */
    }

    private void startActivity() {

        isTablet = getResources().getBoolean(R.bool.isTablet);
        Intent intent = getIntent();
        boolean gie = intent.getBooleanExtra("goToExpenses", false);

        //todo move somewhere
        if (gie) {


            if (!isTablet) {
                Intent phoneIntent = new Intent(getApplicationContext(), PhoneMainActivity.class);
                phoneIntent.putExtra("goToExpenses", true);
                startActivity(phoneIntent);
            } else {
                Intent tabletIntent = new Intent(getApplicationContext(), TabletMainActivity.class);
                tabletIntent.putExtra("goToExpenses", true);
                startActivity(tabletIntent);
            }
        } else {
            if (!isTablet) {
                Intent phoneIntent = new Intent(getApplicationContext(), PhoneMainActivity.class);
                phoneIntent.putExtra("goToExpenses", false);
                startActivity(phoneIntent);
            } else {
                Intent tabletIntent = new Intent(getApplicationContext(), TabletMainActivity.class);
                tabletIntent.putExtra("goToExpenses", false);
                startActivity(tabletIntent);
            }
        }
    }
}