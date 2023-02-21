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

    public List<Login> getLogins(){
        List<Login> logins = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + LoginDatabase.LoginTable.TABLE;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                Login login = new Login(id, username, password);
                logins.add(login);
            } while (cursor.moveToNext());
        }

        return logins;
    }

    public Login getLogin(long loginId) {
        Login login = null;

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + LoginDatabase.LoginTable.TABLE + " WHERE " + LoginDatabase.LoginTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(loginId)});

        if (cursor.moveToFirst()){
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            login = new Login(loginId, username, password);
        }

        return login;
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
