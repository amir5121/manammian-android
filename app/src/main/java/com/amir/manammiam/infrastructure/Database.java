package com.amir.manammiam.infrastructure;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vansuita.sqliteparser.SqlParser;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "manammiam.db";
    private static final int DATABASE_VER = 4;
    private static final String T_USER = "user";
    public static final String SELECT_USERS = "SELECT * FROM " + T_USER;
    private static final String C_NAME = "name";
    private static final String C_USERNAME = "username";
    private static final String C_GENDER = "gender";
    private static final String C_MAIL = "email";
    private static final String C_IS_LOGGED_IN = "loggedIn";
    private static final String C_PERMISSION = "permission";
    private static final String C_TOKEN = "token";
    private static final String C_IS_DRIVER = "is_driver";

    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SqlParser.
                        create(T_USER).
                        str(C_NAME).
                        str(C_USERNAME).
                        bool(C_GENDER).
                        str(C_MAIL).
                        num(C_PERMISSION).
                        str(C_TOKEN).
                        num(C_IS_DRIVER).
                        build());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF EXISTS " + T_USER);
        Log.w(getClass().getSimpleName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }

    public User getUser() {
        Cursor cursor = db.rawQuery(SELECT_USERS, null);
        User user;
        if (cursor.getCount() == 1) {
            user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(C_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(C_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(C_GENDER)) == User.MALE_INT,
                    cursor.getString(cursor.getColumnIndexOrThrow(C_MAIL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(C_PERMISSION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(C_TOKEN)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(C_IS_DRIVER)) == 1
                    );
        } else if (cursor.getCount() > 1) {
            Log.e(getClass().getSimpleName(), "more than one user in user table. MUST NOT HAPPEN");
            //todo: handle this situation better
            user = new User(null, null, User.MALE, null, User.BLOCKED, null, false);

        } else {
            //todo: return null
//            user =  new User(null, null, User.MALE, null, User.BLOCKED, null, false);
            user = null;

        }
        cursor.close();
        return user;
    }
}
