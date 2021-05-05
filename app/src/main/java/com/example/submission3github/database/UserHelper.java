package com.example.submission3github.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission3github.model.UserModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission3github.database.DatabaseContract.TABLE_NAME;
import static com.example.submission3github.database.DatabaseContract.UserColumns.AVATAR_URL;
import static com.example.submission3github.database.DatabaseContract.UserColumns.LOGIN;

public class UserHelper {
    private final String DATABASE_TABLE = TABLE_NAME;

    private static DatabaseHelper databaseHelper;
    private static UserHelper INSTANCE;
    private static SQLiteDatabase database;

    public UserHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        database = databaseHelper.getReadableDatabase();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, _ID, " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null, _ID + " = ?", new String[] {id}, null, null, null, null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[] {id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[] {id});
    }

    public ArrayList<UserModel> getAllFavorite() {
        ArrayList<UserModel> arrayList = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, LOGIN + " ASC", null);
        cursor.moveToFirst();

        UserModel userModelResponse;

        if (cursor.getCount() > 0) {
            do {
                userModelResponse = new UserModel();
                userModelResponse.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(LOGIN)));
                userModelResponse.setAvatar_url(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL)));
                arrayList.add(userModelResponse);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } cursor.close();
        return arrayList;
    }
}