package com.snhu.cs360.inventoryapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "logins.db";

    private static LoginDatabase instance;

    private LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static LoginDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new LoginDatabase(context);
        }

        return instance;
    }

    private static final class LoginTable{

        private static final String TABLE = "logins";

        private static final String COL_ID = "_id";

        private static final String COL_USERNAME = "username";

        private static final String COL_PASSWORD = "password";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LoginDatabase.LoginTable.TABLE + "( " +
                //FIXME: Possible bug with id incrementing to infinity
                LoginDatabase.LoginTable.COL_ID + " integer primary key autoincrement, " +
                LoginDatabase.LoginTable.COL_USERNAME + ", " +
                LoginDatabase.LoginTable.COL_PASSWORD + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + LoginDatabase.LoginTable.TABLE);
        onCreate(db);
    }

    /**
     * Tests for correct username and password combination
     * @param username User input username
     * @param password user input password
     *
     * @return boolean value reflecting validity. True is valid username and password combination
     */
    public Boolean validateLogin(String username, String password) {
        boolean valid = false;

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + LoginDatabase.LoginTable.TABLE;

        //Traverse the db
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                //Find the stored username and password at each ID
                String dbUsername = cursor.getString(1);
                String dbPassword = cursor.getString(2);

                //Compare to the user given username and password
                if (username.equals(dbUsername)) {
                    if (password.equals(dbPassword)) {
                        //update valid
                        valid = true;
                        break;
                    }
                }
            } while (cursor.moveToNext());
        }
        return valid;
    }

    public long addLogin(Login login) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginDatabase.LoginTable.COL_USERNAME, login.getUsername());
        values.put(LoginDatabase.LoginTable.COL_PASSWORD, login.getPassword());

        long newId = db.insert(LoginDatabase.LoginTable.TABLE, null, values);

        return newId;
    }
}
