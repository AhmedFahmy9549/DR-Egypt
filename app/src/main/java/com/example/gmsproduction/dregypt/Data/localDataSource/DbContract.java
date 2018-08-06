package com.example.gmsproduction.dregypt.Data.localDataSource;

import android.provider.BaseColumns;

/**
 * Created by Hima on 8/5/2018.
 */

public class DbContract {
    private DbContract() {}
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "MyTry";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
