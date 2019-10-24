package com.imastudio.notessqlite.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NOTE = "note";

    public static final class NotaColumns implements BaseColumns {

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";

    }

}
