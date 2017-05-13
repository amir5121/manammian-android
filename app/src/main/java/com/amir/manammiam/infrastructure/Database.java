package com.amir.manammiam.infrastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amir.manammiam.services.Account;
import com.vansuita.sqliteparser.SqlParser;

public class Database extends SQLiteOpenHelper {
    private static final String TAG = "Database";
    private static final int DATABASE_VER = 4;
    private static final String DATABASE_NAME = "manammiam.db";


    //USER table
    private static final String T_USER = "user";
    private static final String C_NAME = "name";
    private static final String C_USERNAME = "username";
    private static final String C_GENDER = "gender";
    private static final String C_MAIL = "email";
    private static final String C_PERMISSION = "permission";
    private static final String C_TOKEN = "token";
    private static final String C_IS_DRIVER = "is_driver";

    private static final String QUERY_SELECT_USERS = "SELECT * FROM " + T_USER;
    private static final String QUERY_DROP_USER_TABLE = "Drop TABLE IF EXISTS " + T_USER;


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
        db.execSQL(QUERY_DROP_USER_TABLE);
        Log.w(getClass().getSimpleName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }

    public User getUser() {
        Cursor cursor = db.rawQuery(QUERY_SELECT_USERS, null);
        User user = null;

        Log.e(TAG, "getUser: users count must be one " + cursor.getCount());

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
            Log.e(TAG, "getUser: Using the database");

        } else if (cursor.getCount() > 1) {
            Log.e(getClass().getSimpleName(), "more than one user in user table. MUST NOT HAPPEN");
            //todo: handle this situation better
//            user = null;

        }

        cursor.close();
        return user;
    }

    public void updateUser(Account.ProfileResponse userInfo) {

        Log.e(TAG, "updateUser: " + userInfo.getUsername());
        ContentValues cv =
                SqlParser.content()
                        .add(C_USERNAME, userInfo.getUsername())
                        .add(C_NAME, userInfo.getName())
                        .add(C_PERMISSION, userInfo.getPermission())
                        .add(C_GENDER, userInfo.getGender())
                        .add(C_IS_DRIVER, userInfo.isDriver())
                        .add(C_MAIL, userInfo.getMail()).get();

        db.update(T_USER, cv, null, null);

    }

    public void invalidate() {
        db.execSQL(SqlParser.delete(T_USER).build());
        Log.e(TAG, "invalidate: ");

    }

    public void insertUserWithOnlyToken(String token) {

        Log.e(TAG, "insertUserWithOnlyToken: " + SqlParser.insert(T_USER).col(C_TOKEN, token).build());
        //other columns are null at this time...
        Cursor cursor = db.rawQuery(
                SqlParser.insert(T_USER).col(C_TOKEN, token).build(),
                null);



        cursor.close();


    }
}
