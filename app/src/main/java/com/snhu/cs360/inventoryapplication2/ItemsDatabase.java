package com.snhu.cs360.inventoryapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemsDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "items.db";

    private static ItemsDatabase instance;

    private ItemsDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static ItemsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new ItemsDatabase(context);
        }

        return instance;
    }

    private static final class ItemTable{

        private static final String TABLE = "items";

        private static final String COL_ID = "_id";

        private static final String COL_NAME = "name";

        private static final String COL_QUANTITY = "quantity";

        private static final String COL_UNITS = "units";

        private static final String COL_VALUE = "value";

        private static final String COL_LOCATION = "location";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ItemTable.TABLE + "( " +
                ItemTable.COL_ID + " integer primary key autoincrement, " +
                ItemTable.COL_NAME + ", " +
                ItemTable.COL_QUANTITY + ", " +
                ItemTable.COL_UNITS + ", " +
                ItemTable.COL_VALUE + ", " +
                ItemTable.COL_LOCATION + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ItemTable.TABLE);
        onCreate(db);
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + ItemTable.TABLE;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(0);
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String units = cursor.getString(3);
                int value = cursor.getInt(4);
                String location = cursor.getString(5);
                Item item = new Item(id, name, quantity, units, value, location);
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public Item getItem(long itemId) {
        Item item = null;

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + ItemTable.TABLE + " WHERE " + ItemTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(itemId)});

        if (cursor.moveToFirst()){
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            String units = cursor.getString(3);
            int value = cursor.getInt(4);
            String location = cursor.getString(5);
            item = new Item(itemId, name, quantity, units, value, location);
        }

        return item;
    }

    public long addItem(Item item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemTable.COL_NAME, item.getName());
        values.put(ItemTable.COL_QUANTITY, item.getQuantity());
        values.put(ItemTable.COL_UNITS, item.getUnits());
        values.put(ItemTable.COL_VALUE, item.getValue());
        values.put(ItemTable.COL_LOCATION, item.getLocation());

        long newId = db.insert(ItemTable.TABLE, null, values);

        return newId;
    }

    public boolean editItem(long id, Item item) {
        boolean isEdited = false;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ItemTable.COL_ID, id);
        values.put(ItemTable.COL_NAME, item.getName());
        values.put(ItemTable.COL_QUANTITY, item.getQuantity());
        values.put(ItemTable.COL_UNITS, item.getUnits());
        values.put(ItemTable.COL_VALUE, item.getValue());
        values.put(ItemTable.COL_LOCATION, item.getLocation());

        int result = db.update(ItemTable.TABLE, values, ItemTable.COL_ID + " = " + id, null);

        return result == 1;
    }

    public boolean deleteItem(long id) {
        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(ItemTable.TABLE, ItemTable.COL_ID + " = " + id, null);
        return result == 1;
    }
}