package co.davo.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Davo on 6/20/2017.
 */

public final class HabitContract {
    // Blank constructor to keep class from becoming instantiated.
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_DATE = "date";
    }
}