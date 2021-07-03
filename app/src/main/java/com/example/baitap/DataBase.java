package com.example.baitap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactManager";
    private static final int DATABASE_VER = 1;
    private static final String TABLE_NAME = "contact";

    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String NUM = "num";

    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = String.format
                ("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, NAME, NUM);
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_table);

        onCreate(db);
    }

    //insert dữ liệu vào bảng
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUM, contact.getPhoneNumber());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //tra ve mot du lieu theo tham so id
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(id) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return contact;
    }

    //ham doc tat ca du lieu
    public List<Contact> getAll() {
        List<Contact>  contactList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Contact student = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            contactList.add(student);
            cursor.moveToNext();
        }
        return contactList;
    }

    //ham update data
    public void update(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getName());
        values.put(NUM, contact.getPhoneNumber());


        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    public void delete(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(contact.getId()) });
        db.close();
    }


}
