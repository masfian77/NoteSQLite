package com.imastudio.notessqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.imastudio.notessqlite.Note;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.imastudio.notessqlite.database.DatabaseContract.NotaColumns.DATE;
import static com.imastudio.notessqlite.database.DatabaseContract.NotaColumns.DESCRIPTION;
import static com.imastudio.notessqlite.database.DatabaseContract.NotaColumns.TITLE;
import static com.imastudio.notessqlite.database.DatabaseContract.TABLE_NOTE;

public class NotaHelper {

    private static DatabaseHelper databaseHelper;
    private static NotaHelper notaHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static String DATABASE_TABLE = TABLE_NOTE;

    public NotaHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static NotaHelper getInstance(Context context){
        if (notaHelper == null){
            synchronized (SQLiteDatabase.class){
                if (notaHelper == null){
                    notaHelper = new NotaHelper(context);
                }
            }
        }
        return notaHelper;
    }

    public void open() throws SQLException{
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();;
        if (sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
    }

    public ArrayList<Note> getAllNota(){

        ArrayList<Note> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                _ID + " ASC",
                                                null);
        cursor.moveToFirst();

        Note nota;
        if (cursor.getCount() > 0){
            do {
                nota = new Note();
                nota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                nota.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                nota.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                nota.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(nota);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public long insertNota(Note nota){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, nota.getTitle());
        contentValues.put(DESCRIPTION, nota.getDescription());
        contentValues.put(DATE, nota.getDate());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public int updateNota(Note nota){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, nota.getTitle());
        contentValues.put(DESCRIPTION, nota.getDescription());
        contentValues.put(DATE, nota.getDate());
        return  sqLiteDatabase.update(DATABASE_TABLE, contentValues, _ID + "= '" + nota.getId() + "'", null);
    }

    public int deleteNota(int id){
        return sqLiteDatabase.delete(TABLE_NOTE, _ID + " = '" + id + "'", null);
    }
}