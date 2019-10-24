package com.imastudio.notessqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbnota";
    public static int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_TABLE = String.format("CREATE TABLE %s"
                                                      + "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        " %s TEXT NOT NULL," +
                                                        " %s TEXT NOT NULL," +
                                                        " %s TEXT NOT NULL)",
                                                      DatabaseContract.TABLE_NOTE,
                                                      DatabaseContract.NotaColumns._ID,
                                                      DatabaseContract.NotaColumns.TITLE,
                                                      DatabaseContract.NotaColumns.DESCRIPTION,
                                                      DatabaseContract.NotaColumns.DATE);

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NOTE);
        onCreate(db);
    }
}
