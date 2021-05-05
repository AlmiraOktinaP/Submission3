package com.example.submission3github.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.submission3github.model.UserModel;

import static android.provider.BaseColumns._ID;
import static com.example.submission3github.database.DatabaseContract.UserColumns.AVATAR_URL;
import static com.example.submission3github.database.DatabaseContract.UserColumns.LOGIN;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user";

    Context context;

    private static String DATABASE_NAME = "dbuserapp";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," + " %s TEXT NOT NULL," + " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.UserColumns._ID,
            DatabaseContract.UserColumns.LOGIN,
            DatabaseContract.UserColumns.AVATAR_URL
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public long addUser(UserModel mUserModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN, mUserModel.getLogin());
        cv.put(AVATAR_URL, mUserModel.getAvatar_url());
        return db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }

    public void deleteUser(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, LOGIN + " = " + "'" + login + "'", null);
        if (result == -1) {
            Toast.makeText(context, "Failed delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor queryByLogin(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, null, LOGIN + " = ?", new String[] {login}, null, null, null, null);
    }
}